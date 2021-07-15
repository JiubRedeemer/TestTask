package com.haulmont.testtask;

import com.haulmont.testtask.dao.BankDB;
import com.haulmont.testtask.dao.ClientDB;
import com.haulmont.testtask.entities.*;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.sql.SQLException;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        //CreateTables.create();
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        Client Vlad = new Client("Vladislav", "Golubev", "Dmitrievich", "89053290910", "jiubredeemer@gmail.com", "6315171156");
        Client Dasha = new Client("Darya", "Kovganko", "Sergeevna", "88005553535", "kov_d@gmail.com", "6315123456");

        Credit fast = new Credit("fast", 120000, 12.5f);
        Credit waited = new Credit("waited", 5000000, 7.5f);

        Bank tinkoff = new Bank("Tinkoff");

        Vlad.setBank(tinkoff);
        Dasha.setBank(tinkoff);

        Payments paymentsV = new Payments(new Date(System.currentTimeMillis()), 5000, 1200, 3000, 800);
        Payments paymentsD = new Payments(new Date(System.currentTimeMillis()), 6000, 2200, 3000, 800);

        ClientCredit clientCreditV = new ClientCredit(Vlad, fast, paymentsV);
        ClientCredit clientCreditD = new ClientCredit(Dasha, waited, paymentsD);

        layout.addComponent(new Label("asd"));

        ClientDB clientDB = new ClientDB();
        BankDB bankDB = new BankDB();
        try {
            clientDB.addClient(Vlad);
            clientDB.addClient(Dasha);
            bankDB.addBank(tinkoff);
            clientDB.updateClient(Vlad);
            clientDB.updateClient(Dasha);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


//        Connection con;
//        try {
//            con = DriverManager.getConnection("jdbc:hsqldb:file:"
//                            + "database",    // filenames
//                    "sa",                     // username
//                    "");
//            Statement stmt = con.createStatement();
//            ResultSet rs;
//            rs = stmt.executeQuery("SELECT * FROM BANK");
//            while (rs.next()) {
//                String a = rs.getString(1);
//                String b = rs.getString(2);
//                String c = rs.getString(3);
//
//                System.out.printf("%s %s %s",a, b, c);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }


        setContent(layout);

    }
}