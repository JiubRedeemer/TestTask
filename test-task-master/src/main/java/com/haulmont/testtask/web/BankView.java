package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.BankDB;
import com.haulmont.testtask.entities.Bank;
import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.Credit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;
import java.util.List;

public class BankView extends VerticalLayout {
    private Grid<Client> gridClients = new Grid<>(Client.class);
    private Grid<Credit> gridCredits = new Grid<>(Credit.class);
    private Bank bank = new Bank();
    private BankDB bankDB = new BankDB();

    public BankView() throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(gridClients, gridCredits);
        gridLayout.setExpandRatio(gridClients, 1);
        gridLayout.setExpandRatio(gridCredits, 1);
        updateList();
        addComponents(layout, gridLayout);
    }


    protected void updateList() throws SQLException {

    }

    private void gridConfigure() throws SQLException {
        if(bankDB.getAllBanks().isEmpty()) bankDB.addBank(new Bank("MyBank"));
        bank = (Bank) bankDB.getAllBanks().get(0);
        gridClients.setWidth("900");
        gridClients.setColumns("name", "surname", "patronymic", "phone", "email", "passport");
        gridCredits.setWidth("900");
        gridCredits.setColumns("name", "limit", "percent");
        List<Client> clients = bank.getClients();
        List<Credit> credits = bank.getCredits();
        if (clients != null)
            gridClients.setItems(clients);
        if (credits != null)
            gridCredits.setItems(credits);
    }
}
