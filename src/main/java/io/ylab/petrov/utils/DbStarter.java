package io.ylab.petrov.utils;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

public class DbStarter {
    public void start() throws LiquibaseException {
        Connection connection1 = DataBaseConnector.getConnection();
        Database dataBase;
        try {
            dataBase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection1));
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        Liquibase liquibase = new Liquibase("db/changelog/changelog.xml"
                ,new ClassLoaderResourceAccessor(),dataBase);
        liquibase.update(new Contexts(), new LabelExpression());
    }
}
