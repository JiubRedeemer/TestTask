package com.haulmont.testtask;

import com.haulmont.testtask.createTables.CreateTables;
import com.haulmont.testtask.dao.BankDB;
import com.haulmont.testtask.dao.ClientDB;
import com.haulmont.testtask.dao.CreditDB;
import com.haulmont.testtask.entities.Bank;
import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.Credit;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.hibernate.Session;

import java.sql.*;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        //CreateTables.create();
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        BankDB bankDB = new BankDB();
        ClientDB clientDB = new ClientDB();
        CreditDB creditDB = new CreditDB();
        Credit firstC = new Credit("Fast", 501292, 0.3f);
        Bank tink = new Bank("Tinkoff");
        Client Alex = new Client("Alex", "Golubev", "Dmitrievich", "89020473540", "jiubredeemer@gmail.com", "6315171156");
        Client Max = new Client("Max", "Ivanov", "Dmitrievich", "89012343540", "jiubasdeemer@gmail.com", "6312371156");

        try {
            bankDB.addBank(tink);
            firstC.setBank(tink);
            creditDB.addCredit(firstC);
            Alex.setBank(tink);
            Max.setBank(tink);
            clientDB.addClient(Alex);
            clientDB.addClient(Max);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            BankDB bankDB1 = new BankDB();
            Bank getted = (Bank) bankDB1.getAllBanks().get(0);
            layout.addComponent(new Label(getted.getClients().get(1).getName()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:hsqldb:file:"
                            + "database",    // filenames
                    "sa",                     // username
                    "");
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM BANK");
            while (rs.next()) {
                String a = rs.getString(1);
                String b = rs.getString(2);
                String c = rs.getString(3);

                System.out.printf("%s %s %s",a, b, c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }




        setContent(layout);

    }
}