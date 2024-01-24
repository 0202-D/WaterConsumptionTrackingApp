package io.ylab.petrov.io;

import io.ylab.petrov.io.controller.AuthController;
import io.ylab.petrov.io.dto.AuthReqDto;
import io.ylab.petrov.io.model.user.Role;
import io.ylab.petrov.io.model.user.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input;
        String[] nameAndPassword;
        AuthController authController = new AuthController();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = 0;
            System.out.println("Добро пожаловать!");
            System.out.println("Нажмите 1 чтобы зарегестрироваться , или 2 для входа");
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Не верный ввод");
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case (0):
                    continue;
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
                            .login(nameAndPassword[1])
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
                    if(!isAuthenticated){
                        continue;
                    }
                    else {
                        break;
                    }
                default:
                    System.out.println("Такой операции не существует");
                    continue;
            }
            while (true){
                System.out.println("Добро пожаловать");
            }
        }
    }
}
