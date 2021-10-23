package jm.task.core.hibernate.dao;

import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private static String CREATE_TABLE_users="CREATE TABLE users" +
            "(id BIGINT NOT NULL AUTO_INCREMENT, " +
            " name VARCHAR(50), " +
            " lastname VARCHAR(50), " +
            " age TINYINT, " +
            " PRIMARY KEY (id))";
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session= Util.getSessionFactory().getCurrentSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery(CREATE_TABLE_users).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session= Util.getSessionFactory().getCurrentSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery("DROP TABLE users").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session= Util.getSessionFactory().getCurrentSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(new User(name,lastName,age));
            transaction.commit();
            System.out.printf("User с именем – %s %s добавлен в базу данных\n",name,lastName);
        }

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
