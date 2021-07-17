package com.haulmont.testtask;

import com.haulmont.testtask.createTables.CreateTables;
import com.haulmont.testtask.createTables.FillDb;
import com.haulmont.testtask.web.ClientView;
import com.haulmont.testtask.web.CreditGiveView;
import com.haulmont.testtask.web.CreditView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.*;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        CreateTables.create();
        try {
            FillDb.fillDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        setContent(layout);
        TabSheet tabSheet = new TabSheet();
        TabSheet editTabSheet = new TabSheet();
        TabSheet creditsTabSheet = new TabSheet();
        try {
            editTabSheet.addTab(new ClientView(), "Clients");
            editTabSheet.addTab(new CreditView(), "Credits");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        layout.addComponent(tabSheet);
        tabSheet.addTab(creditsTabSheet, "Credits");
        tabSheet.addTab(editTabSheet, "EditData");
        try {
            creditsTabSheet.addTab(new CreditGiveView(), "Gived credits");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


//        Client Vlad = new Client("Vladislav", "Golubev", "Dmitrievich", "89053290910", "jiubredeemer@gmail.com", "6315171156");
//        Client Dasha = new Client("Darya", "Kovganko", "Sergeevna", "88005553535", "kov_d@gmail.com", "6315123456");
//
//        Credit fast = new Credit("fast", 120000, 12.5f);
//        Credit waited = new Credit("waited", 5000000, 7.5f);
//
//        Bank tinkoff = new Bank("Tinkoff");
//
//        Vlad.setBank(tinkoff);
//        Dasha.setBank(tinkoff);
//
//        Payments paymentsV = new Payments(new Date(System.currentTimeMillis()), 5000, 1200, 3000, 800);
//        Payments paymentsD = new Payments(new Date(System.currentTimeMillis()), 6000, 2200, 3000, 800);
//
//        ClientCredit clientCreditV = new ClientCredit(Vlad, fast);
//        ClientCredit clientCreditD = new ClientCredit(Dasha, waited);
//        clientCreditV.setPayments(paymentsV);
//        clientCreditD.setPayments(paymentsD);
//        paymentsV.setClientCredit(clientCreditV);
//        paymentsD.setClientCredit(clientCreditD);
//
//        layout.addComponent(new Label("asd"));
//
//        ClientDB clientDB = new ClientDB();
//        CreditDB creditDB = new CreditDB();
//        BankDB bankDB = new BankDB();
//        PaymentsDB paymentsDB = new PaymentsDB();
//        ClientCreditDB clientCreditDB = new ClientCreditDB();
//        try {
//            clientDB.addClient(Vlad);
//            clientDB.addClient(Dasha);
//            creditDB.addCredit(fast);
//            creditDB.addCredit(waited);
//            bankDB.addBank(tinkoff);
//            clientDB.updateClient(Vlad);
//            clientDB.updateClient(Dasha);
//            paymentsDB.addPayments(paymentsV);
//            paymentsDB.addPayments(paymentsD);
//            clientCreditDB.addClientCredit(clientCreditV);
//            clientCreditDB.addClientCredit(clientCreditD);
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

//
//        Connection con;
//        try {
//            con = DriverManager.getConnection("jdbc:hsqldb:file:"
//                            + "database",    // filenames
//                    "sa",                     // username
//                  "");
//           Statement stmt = con.createStatement();
//            ResultSet rs;
//            rs = stmt.executeQuery("SELECT * FROM CLIENTCREDIT");
//            while (rs.next()) {
//                String a = rs.getString(1);
//                String b = rs.getString(2);
//                String c = rs.getString(3);
//
//                System.out.printf("%s %s %s \n",a, b, c);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }


        setContent(layout);

    }
}