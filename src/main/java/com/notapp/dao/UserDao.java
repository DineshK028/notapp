package com.notapp.dao;

import com.notapp.entity.User;
import com.notapp.helper.FactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDao {
    private SessionFactory factory;
    
    public UserDao() {
        this.factory = FactoryProvider.getFactory();
    }
    
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        try {
            Session session = factory.openSession();
            Query<User> query = session.createQuery("from User where email=:e and password=:p", User.class);
            query.setParameter("e", email);
            query.setParameter("p", password);
            user = query.uniqueResult();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public User getUserByEmail(String email) {
        User user = null;
        try {
            Session session = factory.openSession();
            Query<User> query = session.createQuery("from User where email=:e", User.class);
            query.setParameter("e", email);
            user = query.uniqueResult();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean saveUser(User user) {
        boolean flag = false;
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}