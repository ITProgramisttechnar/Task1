package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.open();
    }

    public void createUsersTable() {
        String createUsersTableSQL = """
                CREATE TABLE IF NOT EXISTS users(
                    id SERIAL PRIMARY KEY,
                    name TEXT NOT NULL,
                    lastname TEXT NOT NULL,
                    age SERIAL NOT NULL
                );
                """;
        try(Statement statement = connection.createStatement()){
          statement.execute(createUsersTableSQL);
            System.out.println("Table is done");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = """
                DROP TABLE users
                """;
        try(Statement statement = connection.createStatement()){
            statement.execute(dropTableSQL);
            System.out.println("Table is dropped");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        String insertUserSQL = """
                INSERT INTO users (name, lastName, age)
                VALUES (?,?,?);
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUserById = """
                DELETE FROM users WHERE id = ?
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(removeUserById)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            System.out.println("Users with id: " + id + " are removed");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList();
        String selectUsersSQL = """
                SELECT * FROM users
                """;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectUsersSQL)){
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String clearTableSQL = """
                DELETE FROM users 
                """;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(clearTableSQL);
            System.out.println("Table is cleared");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
