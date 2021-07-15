package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.Payments;
import com.haulmont.testtask.hibernate.HibernateUtil;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentsDB implements PaymentsDAO {
    @Override
    public void addPayments(Payments payments) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(payments);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updatePayments(Payments payments) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(payments);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Payments getPaymentsById(String id) throws SQLException {
        Session session = null;
        Payments payments = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            payments = (Payments) session.load(Payments.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return payments;
    }

    @Override
    public List getAllPayments() throws SQLException {
        Session session = null;
        List payments = new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            payments = session.createQuery("SELECT payments FROM Payments payments").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return payments;
    }

    @Override
    public void deletePayments(Payments payments) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(payments);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }

    }
}



