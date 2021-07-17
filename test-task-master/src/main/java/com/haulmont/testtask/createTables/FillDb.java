package com.haulmont.testtask.createTables;

import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.entities.*;
import com.vaadin.ui.Label;

import java.sql.Date;
import java.sql.SQLException;

public class FillDb {
    public static void fillDb() throws Exception {
        Client Vlad = new Client("Vladislav", "Golubev", "Dmitrievich", "89053290910", "jiubredeemer@gmail.com", "6315171156");
        Client Dasha = new Client("Darya", "Kovganko", "Sergeevna", "88005553535", "kov_d@gmail.com", "6315123456");

        Credit fast = new Credit("fast", 120000, 12.5f);
        Credit waited = new Credit("waited", 5000000, 7.5f);

        Bank tinkoff = new Bank("Tinkoff");

        Vlad.setBank(tinkoff);
        Dasha.setBank(tinkoff);

        Payments paymentsV = new Payments(new Date(System.currentTimeMillis()), 5000, 1200, 3000, 800);
        Payments paymentsD = new Payments(new Date(System.currentTimeMillis()), 6000, 2200, 3000, 800);

        ClientCredit clientCreditV = new ClientCredit(Vlad, fast, 32000, 17);
        ClientCredit clientCreditD = new ClientCredit(Dasha, waited, 2310000, 15);
        clientCreditV.setPayments(paymentsV);
        clientCreditD.setPayments(paymentsD);
        paymentsV.setClientCredit(clientCreditV);
        paymentsD.setClientCredit(clientCreditD);

        ClientDB clientDB = new ClientDB();
        CreditDB creditDB = new CreditDB();
        BankDB bankDB = new BankDB();
        PaymentsDB paymentsDB = new PaymentsDB();
        ClientCreditDB clientCreditDB = new ClientCreditDB();
        try {
            bankDB.addBank(tinkoff);
            clientDB.addClient(Vlad);
            clientDB.addClient(Dasha);
            creditDB.addCredit(fast);
            creditDB.addCredit(waited);
            bankDB.addBank(tinkoff);

            //paymentsDB.addPayments(paymentsV);
            //paymentsDB.addPayments(paymentsD);
            clientCreditDB.addClientCredit(clientCreditV);
            clientCreditDB.addClientCredit(clientCreditD);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
