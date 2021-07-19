package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.PaymentsDB;
import com.haulmont.testtask.entities.ClientCredit;
import com.haulmont.testtask.entities.Payments;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentsView extends VerticalLayout {
    public Grid<Payments> grid = new Grid<>(Payments.class);
    private PaymentsDB paymentsDB = new PaymentsDB();
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    Button refresh = new Button("Refresh");
    public ClientCredit clientCredit;

    private List<Payments> paymentsListDiff = new ArrayList<>();
    private List<Payments> paymentsListAnn = new ArrayList<>();

    public PaymentsView() throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(grid);
        gridLayout.setExpandRatio(grid, 1);
        radioGroup.setItems("Differentiated payment", "Annuity payment");
        radioGroup.setValue("Differentiated payment");
        layout.addComponents(radioGroup, refresh);
        radioGroup.addValueChangeListener(event -> {
            if(event.getValue() == "Differentiated payment") grid.setItems(paymentsListDiff);
            if(event.getValue() == "Annuity payment") grid.setItems(paymentsListAnn);
        });
        updateList();
        addComponents(layout, gridLayout);
    }


    protected void updateList() throws SQLException {
        grid.setItems(paymentsListDiff);
    }

    public void updateGrid() throws SQLException {
        grid.setItems(paymentsDB.getAllPayments());
    }

    private void gridConfigure() throws SQLException {
        grid.setWidth("900");
        grid.setColumns("date", "sumPayment", "sumPaymentBody", "sumPaymentPercents", "balanceOwed");
        List<Payments> payments = paymentsDB.getAllPayments();
        grid.setItems(payments);
    }

    public List<Payments> getPaymentsListDiff() {
        return paymentsListDiff;
    }

    public void setPaymentsListDiff(List<Payments> paymentsListDiff) {
        this.paymentsListDiff = paymentsListDiff;
    }

    public List<Payments> getPaymentsListAnn() {
        return paymentsListAnn;
    }

    public void setPaymentsListAnn(List<Payments> paymentsListAnn) {
        this.paymentsListAnn = paymentsListAnn;
    }
}
