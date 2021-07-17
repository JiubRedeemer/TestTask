package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.ClientCredit;
import com.haulmont.testtask.entities.Credit;
import com.haulmont.testtask.hibernate.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientCreditDB implements ClientCreditDAO{
    @Override
    public void addClientCredit(ClientCredit clientCredit) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(clientCredit);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updateClientCredit(ClientCredit clientCredit) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(clientCredit);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public ClientCredit getClientCreditById(String id) throws SQLException {
        Session session = null;
        ClientCredit clientCredit = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clientCredit = session.load(ClientCredit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return clientCredit;
    }

    @Override
    public List getAllClientCredit() throws SQLException {
        Session session = null;
        List clientCredits = new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clientCredits = session.createQuery("SELECT clientCredit FROM ClientCredit clientCredit").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clientCredits;
    }

    @Override
    public void deleteClientCredit(ClientCredit clientCredit) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(clientCredit);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }



}
