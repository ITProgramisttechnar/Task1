package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;


public class Main {
    private static SessionFactory sessionFactory;
    private static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        try {
            sessionFactory = Util.open();
            userService.createUsersTable();
            userService.saveUser("Tom", "Black", (byte) 21);
            userService.saveUser("Jack", "White", (byte) 15);
            userService.saveUser("John", "Grey", (byte) 54);
            userService.saveUser("Zak", "Blue", (byte) 24);
            userService.getAllUsers();
            userService.removeUserById(1);
            userService.cleanUsersTable();
            userService.dropUsersTable();
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
                sessionFactory.close();
        }
    }
}


