package com.haulmont.testtask.hibernate;

import com.haulmont.testtask.entities.Bank;
import com.haulmont.testtask.entities.Client;

import com.haulmont.testtask.entities.Credit;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().
                    addAnnotatedClass(Client.class).
                    addAnnotatedClass(Credit.class).
                    addAnnotatedClass(Bank.class).
                    buildSessionFactory();
        } catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
