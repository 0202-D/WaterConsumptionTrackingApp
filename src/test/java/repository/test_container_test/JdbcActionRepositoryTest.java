package repository.test_container_test;

import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.user.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.Utils;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class JdbcActionRepositoryTest {
    private final static String CREATE_SCHEMA_IF_NOT_EXISTS_DOMAIN = "CREATE SCHEMA IF NOT EXISTS domain";
    private final static String CREATE_USER_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS domain.users (" +
            "id BIGSERIAL PRIMARY KEY," +
            "user_name VARCHAR(255) NOT NULL," +
            "password VARCHAR(255) NOT NULL," +
            "role VARCHAR(255) NOT NULL" +
            ")";
    private final static String CREATE_ACTION_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS domain.action (" +
            "user_id BIGINT REFERENCES domain.users(id)," +
            "activity VARCHAR(50)," +
            "date_time TIMESTAMPTZ NOT NULL" +
            ")";
    private final static String INSERT = "INSERT INTO domain.users (user_name,password,role) VALUES(?,?,?)";
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        postgreSQLContainer.start();

        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        connection = DriverManager.getConnection(jdbcUrl, username, password);
        try (PreparedStatement statement = connection.prepareStatement(CREATE_SCHEMA_IF_NOT_EXISTS_DOMAIN)) {
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(CREATE_USER_TABLE_QUERY)) {
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ACTION_TABLE_QUERY)) {
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, "testUser");
            statement.setString(2, "testPassword");
            statement.setString(3, String.valueOf(Role.USER));

            statement.executeUpdate();
            statement.executeUpdate();
        }
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testAddAction() throws SQLException {
        JdbcUserRepository jdbcUserRepository = new JdbcUserRepository();
        JdbcActionRepository actionRepository = new JdbcActionRepository(jdbcUserRepository);
        Action action = Utils.getAction();
        Action action2 = Utils.getActionExited();
        actionRepository.addAction(action);
        actionRepository.addAction(action2);
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM domain.action");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        assertEquals(4, count);
    }
}

