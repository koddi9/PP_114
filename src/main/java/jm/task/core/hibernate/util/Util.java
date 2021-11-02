package jm.task.core.hibernate.util;

import jm.task.core.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static String dbURL="jdbc:mysql://localhost:3306/dbfor_113?useSSL=false";
    private static String dbUsername="root";
    private static String dbPassword="localdb";

    public static Connection getConnection(){
        Connection connecteion = null;
        try{
            connecteion= DriverManager.getConnection(dbURL,dbUsername,dbPassword);
        }
        catch(SQLException sqlE){
            sqlE.printStackTrace();
        }
        return connecteion;
    }

    public static SessionFactory getSessionFactory(){
        SessionFactory factory = null;
        try {
            Properties settings = new Properties();
            settings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, dbURL);
            settings.put(Environment.USER, dbUsername);
            settings.put(Environment.PASS, dbPassword);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "none");

            Configuration configuration = new Configuration().setProperties(settings)
                    .addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }
}
