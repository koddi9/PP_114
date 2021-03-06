package jm.task.core.hibernate.dao;

import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        String CREATE_TABLE_users="CREATE TABLE IF NOT EXISTS users" +
                "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                " name VARCHAR(50), " +
                " lastname VARCHAR(50), " +
                " age TINYINT, " +
                " PRIMARY KEY (id))";
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
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
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
        try(Session session= Util.getSessionFactory().getCurrentSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.delete(session.get(User.class,id));
//            session.createQuery("delete User where id=:id").setParameter("id",id).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users=null;
        try(Session session= Util.getSessionFactory().getCurrentSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            users= session.createQuery("from User",User.class).list();
            transaction.commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session= Util.getSessionFactory().getCurrentSession()){
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        }
    }
}
