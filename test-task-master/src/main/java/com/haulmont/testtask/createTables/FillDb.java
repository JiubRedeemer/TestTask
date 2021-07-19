package com.haulmont.testtask.createTables;

import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.entities.*;
import com.vaadin.ui.Label;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class FillDb {
    public static void fillDb() throws Exception {
        Client Vlad = new Client("Vladislav", "Golubev", "Dmitrievich", "89053290910", "jiubredeemer@gmail.com", "6315171156");
        Client Dasha = new Client("Darya", "Kovganko", "Sergeevna", "88005553535", "kov_d@gmail.com", "6315123456");

        Credit fast = new Credit("fast", 120000, 0.125f);
        Credit waited = new Credit("waited", 5000000, 0.075f);

        Bank tinkoff = new Bank("Tinkoff");

        Vlad.setBank(tinkoff);
        Dasha.setBank(tinkoff);

        ClientCredit clientCreditV = new ClientCredit(9200,3);
        ClientCredit clientCreditD = new ClientCredit(123456,12);

        clientCreditV.setCredit(waited);
        clientCreditD.setCredit(fast);
        clientCreditV.setClient(Vlad);
        clientCreditD.setClient(Dasha);
        clientCreditV.setStart(LocalDate.now());
        clientCreditD.setStart(LocalDate.now());

        ClientDB clientDB = new ClientDB();
        CreditDB creditDB = new CreditDB();
        BankDB bankDB = new BankDB();
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
