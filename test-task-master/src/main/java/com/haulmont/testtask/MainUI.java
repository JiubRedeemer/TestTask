package com.haulmont.testtask;

import com.haulmont.testtask.createTables.CreateTables;
import com.haulmont.testtask.createTables.FillDb;
import com.haulmont.testtask.web.ClientView;
import com.haulmont.testtask.web.CreditGiveView;
import com.haulmont.testtask.web.CreditView;
import com.haulmont.testtask.web.PaymentsView;
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
       // CreateTables.create();
//        try {
//        try {
//            FillDb.fillDb();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        setContent(layout);
        TabSheet tabSheet = new TabSheet();
        TabSheet editTabSheet = new TabSheet();
        TabSheet creditsTabSheet = new TabSheet();



        try {
            PaymentsView paymentsView = new PaymentsView();
            editTabSheet.addTab(new ClientView(), "Clients");
            editTabSheet.addTab(new CreditView(), "Credits");
            layout.addComponent(tabSheet);
            tabSheet.addTab(creditsTabSheet, "Credits");
            tabSheet.addTab(editTabSheet, "EditData");
            creditsTabSheet.addTab(new CreditGiveView(paymentsView), "Gived credits");
            creditsTabSheet.addTab(paymentsView, "Payments");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        setContent(layout);

    }
}