package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Util {

    public static SessionFactory open(){
        try{
            Configuration configuration = new Configuration();
            configuration.configure();
            return configuration.buildSessionFactory();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}









