package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.ClientCredit;
import com.haulmont.testtask.entities.Payments;

import java.sql.SQLException;
import java.util.List;

public interface PaymentsDAO {
    public void addPayments(Payments payments) throws SQLException;
    public void updatePayments(Payments payments) throws SQLException;
    public Payments getPaymentsById(String id) throws SQLException;
    public List getAllPayments() throws SQLException;
    public void deletePayments(Payments payments) throws SQLException;
}
