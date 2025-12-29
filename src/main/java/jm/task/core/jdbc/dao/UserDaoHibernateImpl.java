package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;


    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            String sql = """
                    CREATE TABLE IF NOT EXISTS users(
                        id SERIAL PRIMARY KEY,
                        name TEXT NOT NULL,
                        lastname TEXT NOT NULL,
                        age SERIAL NOT NULL
                    );
                    """;
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Table is created");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()){
           Transaction transaction = session.beginTransaction();
            String dropTableSQL = """
                  DROP TABLE IF EXISTS users
                    """;
            session.createNativeQuery(dropTableSQL, User.class).executeUpdate();
            transaction.commit();
            System.out.println("Table is dropped");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()) {
           Transaction transaction = session.beginTransaction();
           session.persist(new User(name, lastName, age));
           transaction.commit();
           System.out.println("User " + name + " is added ");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()){
           Transaction transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if(user !=null){
                session.remove(user);
                System.out.println("User with id: " + id + " is removed");
            }
            transaction.commit();
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = sessionFactory .openSession()){
           Transaction transaction = session.beginTransaction();
            String getUsers =
                    """
                 SELECT * FROM users  
                    """;
            NativeQuery<User> query = session.createNativeQuery(getUsers,User.class);
            List<User> users = query.getResultList();
            for(User user: users){
                System.out.println(user);
            }
            transaction.commit();
            return users;
            }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()){
           Transaction transaction = session.beginTransaction();
            String clearTableSQL = """
                    DELETE FROM users
                    """;
            session.createNativeQuery(clearTableSQL,User.class).executeUpdate();
            transaction.commit();
            System.out.println("Table is cleared");
        }
    }
}
