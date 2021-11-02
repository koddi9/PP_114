package jm.task.core.hibernate;

import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.service.UserService;
import jm.task.core.hibernate.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan","Ivanov",(byte)50);
        userService.saveUser("Andrey","Mironov",(byte)33);
        userService.saveUser("Denis","Lavrov",(byte)27);
        userService.saveUser("Sergey","Bodrov",(byte)30);

        List<User> users=userService.getAllUsers();
        System.out.println("Users after inserting:");
        users.forEach(e-> System.out.println(e.toString()));

//        userService.cleanUsersTable();
        userService.removeUserById(2);
        users=userService.getAllUsers();
        System.out.println("Users after cleaning:");
        users.forEach(e -> System.out.println(e.toString()));


        userService.dropUsersTable();

    }

    /**
     *  UserHibernateDaoImpl должен реализовывать интерефейс UserDao
     *  В класс Util должна быть добавлена конфигурация для Hibernate ( рядом с JDBC), без использования xml.
     *  Service на этот раз использует реализацию dao через Hibernate
     *  Методы создания и удаления таблицы пользователей в классе UserHibernateDaoImpl должны быть реализованы с помощью SQL.
     */
}
