package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.ClientCreditDB;
import com.haulmont.testtask.dao.ClientDB;
import com.haulmont.testtask.dao.CreditDB;
import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.ClientCredit;
import com.haulmont.testtask.entities.Credit;
import com.haulmont.testtask.entities.Payments;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditGiveEditUI extends VerticalLayout {

    private ComboBox<Client> clientComboBox = new ComboBox<>("Client");
    private ComboBox<Credit> creditComboBox = new ComboBox<>("Credit");
    private TextField tfCreditSum = new TextField("Credit sum");
    private TextField tfTimeOfCredit = new TextField("Months");
    private DateField dateField = new DateField("Start");

    private Button add = new Button("Добавить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");
    private Button calculate = new Button("Calculate");
    private Label paymentSum = new Label("Payment sum");
    private Label overpay = new Label("Overpay");

    private CreditGiveView creditGiveView;
    private ClientCredit clientCredit;
    public PaymentsView paymentsView;

    private ClientCreditDB clientCreditDB = new ClientCreditDB();
    private ClientDB clientDB = new ClientDB();
    private CreditDB creditDB = new CreditDB();
    private Binder<ClientCredit> binder = new Binder();
    private List<Payments> paymentsListDiff = new ArrayList<>();
    private List<Payments> paymentsListAnn = new ArrayList<>();

    public CreditGiveEditUI(ClientCredit clientCredit, CreditGiveView creditGiveView) throws SQLException {
        this.clientCredit = clientCredit;
        this.creditGiveView = creditGiveView;
        setVisible(false);
        setWidthUndefined();
        HorizontalLayout layout = new HorizontalLayout();
        cancel.addClickListener(event -> this.setVisible(false));
        addClickListeners(creditGiveView);
        updateSelects();
        layout.addComponents(add, update, delete, cancel, calculate);
        addComponents(clientComboBox, creditComboBox, tfCreditSum, tfTimeOfCredit, dateField, layout);
    }

    public void editConfigure(ClientCredit clientCredit) {
        setVisible(true);
        try {
            updateSelects();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (clientCredit == null) {
            clear();
            add.setVisible(true);
            delete.setVisible(false);
            update.setVisible(false);
            calculate.setVisible(false);
            paymentSum.setVisible(false);
            overpay.setVisible(false);
        } else {
            clear();
            this.clientCredit = clientCredit;
            setClientCredit(clientCredit);
            add.setVisible(false);
            delete.setVisible(true);
            update.setVisible(true);
            calculate.setVisible(true);
        }
        tfCreditSum.setPlaceholder("Enter credit sum");
        tfTimeOfCredit.setPlaceholder("Enter credit time");
        clientComboBox.setPlaceholder("Select client");
        creditComboBox.setPlaceholder("Select credit");
    }

    private void setClientCredit(ClientCredit clientCredit) {

//        tfCreditSum.setValue("" + clientCredit.getCreditSum());
//        tfTimeOfCredit.setValue(""+clientCredit.getTime());
//        clientComboBox.setValue(clientCredit.getClient());
//        creditComboBox.setValue(clientCredit.getCredit());

    }

    private void clear() {
        clientComboBox.clear();
        creditComboBox.clear();
        tfTimeOfCredit.clear();
        tfCreditSum.clear();
        dateField.clear();
    }

    private void addClickListeners(CreditGiveView creditGiveView) {
        calculate.addClickListener(clickEvent -> {
            try {
                createDiffPayment(clientCredit);
                createAnnPayment(clientCredit);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        add.addClickListener(clickEvent -> {
            if (filedCheck()) {
                try {
                    addClientCredit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    creditGiveView.updateGrid();
                    this.setVisible(false);
                    clear();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        update.addClickListener(clickEvent -> {
            if (filedCheck()) {
                try {
                    updateClientCredit(clientCredit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    creditGiveView.updateGrid();
                    this.setVisible(false);
                    clear();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });

        delete.addClickListener(clickEvent -> {
            try {
                deleteClientCredit(clientCredit);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                creditGiveView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void addClientCredit() throws Exception {
        ClientCredit clientCredit = new ClientCredit(Long.parseLong(tfCreditSum.getValue()),
                Long.parseLong(tfTimeOfCredit.getValue()));
        clientCredit.setClient(clientComboBox.getValue());
        clientCredit.setCredit(creditComboBox.getValue());
        clientCredit.setStart(dateField.getValue());
        clientComboBox.getValue().getClientCredits().add(clientCredit);
        creditComboBox.getValue().getClientCredits().add(clientCredit);
        clientCreditDB.addClientCredit(clientCredit);
    }

    private void updateClientCredit(ClientCredit clientCredit) throws Exception {

        clientCredit.getClient().getClientCredits().remove(clientCredit);
        clientCredit.getCredit().getClientCredits().remove(clientCredit);
        clientDB.updateClient(clientCredit.getClient());
        creditDB.updateCredit(clientCredit.getCredit());

        clientCredit.setClient(clientComboBox.getValue());
        clientCredit.setCredit(creditComboBox.getValue());
        clientCredit.getClient().getClientCredits().add(clientCredit);
        clientCredit.getCredit().getClientCredits().add(clientCredit);
        clientDB.updateClient(clientCredit.getClient());
        creditDB.updateCredit(clientCredit.getCredit());


        clientCredit.setCreditSum(Long.parseLong(tfCreditSum.getValue()));
        clientCredit.setTime(Long.parseLong(tfTimeOfCredit.getValue()));
        clientCredit.setStart(dateField.getValue());
        clientCreditDB.updateClientCredit(clientCredit);
        Page.getCurrent().reload();
    }

    private void deleteClientCredit(ClientCredit clientCredit) throws SQLException {
        clientCredit.getClient().getClientCredits().remove(clientCredit);
        clientCredit.getCredit().getClientCredits().remove(clientCredit);

        clientDB.updateClient(clientCredit.getClient());
        creditDB.updateCredit(clientCredit.getCredit());
        clientCreditDB.deleteClientCredit(clientCredit);
        creditGiveView.updateGrid();
        this.setVisible(false);
        clear();
        Page.getCurrent().reload();
    }

    private boolean filedCheck() {
        String rgxTimeOfCredit = "^-?\\d+$";
        String rgxCreditSum = "^-?\\d+$";
        if (clientComboBox.isEmpty() || creditComboBox.isEmpty() || tfCreditSum.isEmpty() || tfTimeOfCredit.isEmpty() || dateField.isEmpty()) {
            add.setComponentError(new UserError("Error!"));
            return false;
        } else {
            add.setComponentError(null);
            tfTimeOfCredit.setComponentError(null);
            tfCreditSum.setComponentError(null);

            if (!tfTimeOfCredit.getValue().matches(rgxTimeOfCredit)) {
                tfTimeOfCredit.setComponentError(
                        new UserError("Введите количество месяцев, например: 24"));
                return false;
            }
            if (!tfCreditSum.getValue().matches(rgxCreditSum)) {
                tfCreditSum.setComponentError(
                        new UserError("Введите сумму кредита, например: 100000"));
                return false;
            }
            return true;
        }
    }

    private void updateSelects() throws SQLException {
        clientComboBox.setItems(clientDB.getAllClients());
        clientComboBox.setItemCaptionGenerator(Client::getFIO);
        creditComboBox.setItems(creditDB.getAllCredits());
        creditComboBox.setItemCaptionGenerator(Credit::getName);
    }

    private void createDiffPayment(ClientCredit clientCredit) throws SQLException {
        LocalDate currentDate = clientCredit.getStart();
        long owed = clientCredit.getCreditSum();
        long timePaid = 1L;
        for (int i = 0; i < clientCredit.getTime(); i++) {
            Payments payment = new Payments();
            payment.setDate(currentDate);
            currentDate = currentDate.plusMonths(1);
            payment.setSumPaymentBody(clientCredit.getCreditSum() / clientCredit.getTime());
            payment.setSumPaymentPercents((long) (owed * (clientCredit.getPercent() / 12)));
            owed = clientCredit.getCreditSum() - (payment.getSumPaymentBody() * timePaid);
            timePaid++;
            payment.setSumPayment(payment.getSumPaymentBody() + payment.getSumPaymentPercents());
            payment.setBalanceOwed(owed);
            paymentsListDiff.add(payment);

        }
        paymentsListDiff.get(paymentsListDiff.size() - 1).setSumPayment(paymentsListDiff.get(paymentsListDiff.size() - 1).getSumPayment() + owed);
        paymentsListDiff.get(paymentsListDiff.size() - 1).setBalanceOwed(0);
        paymentsView.setPaymentsListDiff(paymentsListDiff);
    }

    private void createAnnPayment(ClientCredit clientCredit) throws SQLException {
        LocalDate currentDate = clientCredit.getStart();
        long owed = clientCredit.getCreditSum();
        long timePaid = 0L;
        for (int i = 0; i < clientCredit.getTime(); i++) {
            Payments payment = new Payments();
            payment.setDate(currentDate);
            currentDate = currentDate.plusMonths(1);

            payment.setSumPayment((long) (clientCredit.getCreditSum() * ((clientCredit.getPercent() / 12.0f) +
                    ((clientCredit.getPercent() / 12.0f) /
                            ((Math.pow(1 + clientCredit.getPercent() / 12, clientCredit.getTime()) - 1))))));
            payment.setSumPaymentPercents((long) (owed * clientCredit.getPercent() / 12));
            payment.setSumPaymentBody(payment.getSumPayment() - payment.getSumPaymentPercents());
            owed -= payment.getSumPaymentBody();
            payment.setBalanceOwed(owed);


            paymentsListAnn.add(payment);

        }
        paymentsListAnn.get(paymentsListAnn.size() - 1).setSumPayment(paymentsListAnn.get(paymentsListAnn.size() - 1).getSumPayment() + owed);
        paymentsListAnn.get(paymentsListAnn.size() - 1).setBalanceOwed(0);
        paymentsView.setPaymentsListAnn(paymentsListAnn);
    }
}

