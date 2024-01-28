package io.ylab.petrov;

import io.ylab.petrov.dao.user.InMemoryUserRepositoryImpl;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.in.controller.AuthController;
import io.ylab.petrov.in.controller.MonitoringController;
import io.ylab.petrov.dto.AddReadingRqDto;
import io.ylab.petrov.dto.AuthReqDto;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input;
        String[] nameAndPassword;
        AuthController authController = new AuthController();
        MonitoringController monitoringController = new MonitoringController();
        UserRepository userRepository = new InMemoryUserRepositoryImpl();
     //   User currentUser = new User(1l, "Ivan", "123", Role.ADMIN);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            User currentUser;
            int choice = 0;
            System.out.println("Добро пожаловать!");
            System.out.println("Чтобы зарегестрироваться введите 1");
            System.out.println("Чтобы войти введите 2");
            System.out.println("Чтобы выйти введите 0");
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Не верный ввод");
                scanner.nextLine();
                return;
            }
            switch (choice) {
                case (0):
                    return;
                case (1):
                    System.out.println("Ведите через пробел имя и пароль");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    nameAndPassword = input.split(" ");
                    if (nameAndPassword.length != 2) {
                        System.out.println("Не верный ввод");
                        scanner.nextLine();
                        continue;
                    }
                    User user = User.builder()
                            .userName(nameAndPassword[0])
                            .password(nameAndPassword[1])
                            .role(Role.USER)
                            .build();
                    authController.addUser(user);
                    continue;
                case (2):
                    System.out.println("Ведите через пробел имя и пароль");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    nameAndPassword = input.split(" ");
                    if (nameAndPassword.length != 2) {
                        System.out.println("Не верный ввод");
                        scanner.nextLine();
                        continue;
                    }
                    String userName = nameAndPassword[0];
                    String password = nameAndPassword[1];
                    AuthReqDto dto = AuthReqDto.builder()
                            .userName(userName)
                            .password(password)
                            .build();
                    boolean isAuthenticated = authController.authenticateUser(dto);
                    if (!isAuthenticated) {
                        continue;
                    } else {
                        currentUser = userRepository.getUserByUserName(userName);
                        break;
                    }

                default:
                    System.out.println("Такой операции не существует");
                    return;
            }

            while (true) {
                int choiceTwo = 0;
                System.out.println("Добро пожаловать!");
                System.out.println("Нажмите 1 чтобы внести показания");
                System.out.println("Нажмите 2 для просмотра текущих показаний");
                System.out.println("Нажмите 3 для просмотра показаний за интересующий Вас месяц");
                System.out.println("Чтобы посмотреть историю подачи показаний введите 4");
                System.out.println("Нажмите 0 для выхода");
                try {
                    choiceTwo = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Не верный ввод");
                    scanner.nextLine();
                    continue;
                }
                switch (choiceTwo) {
                    case (0):
                        return;
                    case (1):
                        System.out.println("Введите через пробел id пользователя, показания и id счетчика");
                        scanner.nextLine();
                        input = scanner.nextLine();
                        String[] addRqDto = input.split(" ");
                        if (addRqDto.length != 3) {
                            System.out.println("Не верный ввод");
                            scanner.nextLine();
                            continue;
                        }
                        long userId = Long.parseLong(addRqDto[0]);
                        BigDecimal readout = new BigDecimal(addRqDto[1]);
                        long meterId = Integer.valueOf(addRqDto[2]);
                        AddReadingRqDto dto = AddReadingRqDto.builder()
                                .meterId(meterId)
                                .readout(readout)
                                .userId(userId)
                                .build();
                        monitoringController.addReading(dto);
                        continue;
                    case (2):
                        System.out.println("Введите через пробел id пользователя и id счетчика");
                        scanner.nextLine();
                        input = scanner.nextLine();
                        String[] readingRqDto = input.split(" ");
                        if (readingRqDto.length != 2) {
                            System.out.println("Не верный ввод");
                            scanner.nextLine();
                            continue;
                        }
                        long userIdTwo = Long.parseLong(readingRqDto[0]);
                        long meterTwoId = Integer.valueOf(readingRqDto[1]);
                        if (userIdTwo != currentUser.getId() && currentUser.getRole() != Role.ADMIN) {
                            System.out.println(" Вы не можете просматривать не свои показания");
                            break;
                        }
                        ReadingRqDto dtoTwo = ReadingRqDto.builder()
                                .meterId(meterTwoId)
                                .userId(userIdTwo)
                                .build();
                        if (monitoringController.getCurrentReading(dtoTwo) == null) {
                            System.out.println("Вы пока не подавали показания");
                            continue;
                        }
                        System.out.println(monitoringController.getCurrentReading(dtoTwo));
                        continue;
                    case (3):
                        System.out.println("Введите через пробел id пользователя, id счетчика и месяц");
                        scanner.nextLine();
                        input = scanner.nextLine();
                        String[] getForMonth = input.split(" ");
                        if (getForMonth.length != 3) {
                            System.out.println("Не верный ввод");
                            scanner.nextLine();
                            continue;
                        }
                        userId = Long.parseLong(getForMonth[0]);
                        meterId = Integer.valueOf(getForMonth[1]);
                        Month month = Month.valueOf(getForMonth[2]);
                        if (userId != currentUser.getId() && currentUser.getRole() != Role.ADMIN) {
                            System.out.println(" Вы не можете просматривать не свои показания");
                            break;
                        }
                        ReadingInMonthRq rq = ReadingInMonthRq.builder()
                                .userId(userId)
                                .meterId(meterId)
                                .month(month)
                                .build();
                        Optional<Reading> reading = monitoringController.getReadingForMonth(rq);
                        if (reading.isEmpty()) {
                            System.out.println("Не корректные данные");
                        }
                        System.out.println(reading.get());
                    case (4):
                        if (currentUser.getRole() != Role.ADMIN) {
                            System.out.println("Не достаточно прав");
                            continue;
                        }
                        System.out.println("Введите userId");
                        userId = scanner.nextLong();
                        monitoringController.historyReadingsByUserId(userId);
                        continue;
                    default:
                        System.out.println("Такой операции не существует");
                        return;
                }
            }
        }
    }
}