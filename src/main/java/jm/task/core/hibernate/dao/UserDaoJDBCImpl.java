package jm.task.core.hibernate.dao;

import jm.task.core.hibernate.model.User;
import jm.task.core.hibernate.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static String CREATE_TABLE_users="CREATE TABLE users" +
            "(id BIGINT NOT NULL AUTO_INCREMENT, " +
            " name VARCHAR(50), " +
            " lastname VARCHAR(50), " +
            " age TINYINT, " +
            " PRIMARY KEY (id))";
    private static String INSERT_USER="INSERT INTO users (name,lastname,age) VALUES (?,?,?)";
    private Logger logger = Logger.getLogger(getClass().getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(CREATE_TABLE_users)) {
            prepStatement.executeUpdate();
        }
        catch(SQLException sqlE) {
            logger.log(Level.INFO,sqlE.getStackTrace()[0].toString()+"\n" +
                    "INFO: users TABLE WAS RECREATED");
            dropUsersTable();
            createUsersTable();
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement("DROP TABLE users")) {
            prepStatement.executeUpdate();
        }
        catch(SQLException sqlE) {
            logger.log(Level.INFO,sqlE.getStackTrace()[0].toString()+"\n" +
                    "INFO: users TABLE DOESNT EXIST");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement(INSERT_USER)) {
            prepStatement.setString(1,name);
            prepStatement.setString(2,lastName);
            prepStatement.setString(3,Byte.toString(age));
            prepStatement.executeUpdate();
            System.out.printf("User с именем – %s %s добавлен в базу данных\n",name,lastName);
        }
        catch(SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM users WHERE users.id=id")) {
            prepStatement.executeUpdate();
        }
        catch(SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersArrayList= new ArrayList<>();
        try(Connection connection = Util.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet= prepStatement.executeQuery();
            while(resultSet.next()){
                Long id=resultSet.getLong("id");
                String name=resultSet.getString("name");
                String lastname=resultSet.getString("lastname");
                Byte age=resultSet.getByte("age");
                usersArrayList.add(new User(name,lastname,age));
            }
        }
        catch(SQLException sqlE) {
            sqlE.printStackTrace();
        }
        return usersArrayList;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM users")) {
            prepStatement.executeUpdate();
        }
        catch(SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }
}
