package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Tom","Grey", (byte) 12);
        userService.saveUser("Bill","Brown", (byte) 30);
        userService.saveUser("Zak","Blue", (byte) 16);
        userService.saveUser("Joe","Black", (byte) 26);
        userService.getAllUsers();
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
