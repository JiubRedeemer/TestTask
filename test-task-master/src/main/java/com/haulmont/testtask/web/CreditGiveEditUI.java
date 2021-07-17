package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.ClientCreditDB;
import com.haulmont.testtask.dao.ClientDB;
import com.haulmont.testtask.dao.CreditDB;
import com.haulmont.testtask.dao.PaymentsDB;
import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.ClientCredit;
import com.haulmont.testtask.entities.Credit;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class CreditGiveEditUI extends VerticalLayout {

    private ComboBox<Client> clientComboBox = new ComboBox<>("Client");
    private ComboBox<Credit> creditComboBox = new ComboBox<>("Credit");
    private TextField tfCreditSum = new TextField("Credit sum");
    private TextField tfTimeOfCredit = new TextField("Months");

    private Button add = new Button("Добавить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");
    private Button calculate = new Button("Calculate");
    private Label paymentSum = new Label("Payment sum");
    private Label overpay = new Label("Overpay");

    private ClientCredit clientCredit;
    private CreditGiveView creditGiveView;
    private ClientDB clientDB = new ClientDB();
    private CreditDB creditDB = new CreditDB();
    private ClientCreditDB clientCreditDB = new ClientCreditDB();
    private PaymentsDB paymentsDB = new PaymentsDB();
    private Binder<ClientCredit> binder = new Binder();


    public CreditGiveEditUI(ClientCredit clientCredit, CreditGiveView creditGiveView) throws SQLException {
        this.clientCredit = clientCredit;
        setVisible(false);
        setWidthUndefined();
        updateFromComboBox();
        HorizontalLayout layout = new HorizontalLayout();
        cancel.addClickListener(event -> this.setVisible(false));
        addClickListeners(creditGiveView);
        layout.addComponents(add, delete, update, cancel, calculate);
        addComponents(clientComboBox, creditComboBox, tfCreditSum, tfTimeOfCredit, layout);
    }

    public void editConfigure(ClientCredit clientCredit) {
        setVisible(true);
        try {
            updateFromComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (clientCredit == null) {
            clear();
            update.setVisible(false);
            delete.setVisible(false);
            paymentSum.setVisible(false);
            calculate.setVisible(true);
            add.setVisible(true);
        } else {
            this.clientCredit = clientCredit;
            setClientCredit(clientCredit);
            add.setVisible(false);
            calculate.setVisible(false);
            paymentSum.setVisible(false);
            delete.setVisible(true);
            update.setVisible(true);
            // TODO: CALCULATING

        }
        binder.forField(tfTimeOfCredit).withConverter(new StringToLongConverter("Поле введено неверно"))
                .bind(ClientCredit::getTime,ClientCredit::setTime);
        tfCreditSum.setPlaceholder("Enter credit sum");
        tfTimeOfCredit.setPlaceholder("Enter time");
    }


    private void clear() {
        clientComboBox.clear();
        creditComboBox.clear();
        tfCreditSum.clear();
        tfTimeOfCredit.clear();
        paymentSum.setValue("");
    }

    private void addClickListeners(CreditGiveView creditGiveView) {
        add.addClickListener(event -> {
            if (fieldCheck()) {
                try {
                    clientCreditDB.addClientCredit(getClientCredit());
                    creditGiveView.updateGrid();
                    this.setVisible(false);
                    clear();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        update.addClickListener(event -> {
            try {
                updateClientCredit(clientCredit);
                creditGiveView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        delete.addClickListener(event ->{
            try {
                clientCreditDB.deleteClientCredit(clientCredit);
                creditGiveView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void setClientCredit(ClientCredit clientCredit) {
        clientComboBox.setValue(clientCredit.getClient());
        creditComboBox.setValue(clientCredit.getCredit());
        tfCreditSum.setValue(Long.toString(clientCredit.getCreditSum()));
        tfTimeOfCredit.setValue(Long.toString(clientCredit.getTime()));
    }


    private void updateClientCredit(ClientCredit clientCredit) throws SQLException {
        clientCredit.setClient(clientComboBox.getValue());
        clientCredit.setCredit(creditComboBox.getValue());
        clientCredit.setCreditSum(Long.parseLong(tfCreditSum.getValue()));
        clientCredit.setTime(Long.parseLong(tfTimeOfCredit.getValue()));


        clientCreditDB.updateClientCredit(clientCredit);
    }

    private void updateFromComboBox() throws SQLException{
        clientComboBox.setItems(clientDB.getAllClients());
        clientComboBox.setItemCaptionGenerator(Client::getFIO);
        creditComboBox.setItems(creditDB.getAllCredits());
        creditComboBox.setItemCaptionGenerator(Credit::getName);
        creditComboBox.addValueChangeListener(valueChangeEvent -> {
            if (creditComboBox.getValue() == null)
                binder.forField(tfCreditSum).withConverter(new StringToLongConverter("Поле введено неверно"))
                        .bind(ClientCredit::getCreditSum, ClientCredit::setCreditSum);
            else
                binder.forField(tfCreditSum).withConverter(new StringToLongConverter("Поле введено неверно"))
                        .withValidator(event -> event <= creditComboBox.getValue().getLimit(), "Сумма кредита не должна быть выше " + creditComboBox.getValue().getLimit())
                        .bind(ClientCredit::getCreditSum, ClientCredit::setCreditSum);
        });
    }

    private boolean fieldCheck() {
        if (clientComboBox.isEmpty() || creditComboBox.isEmpty() || tfCreditSum.isEmpty() || tfTimeOfCredit.isEmpty()) {
            add.setComponentError(new UserError("Not all fields are entered!"));
            return false;
        } else {
            add.setComponentError(null);
            return true;
        }
    }

    private ClientCredit getClientCredit(){
        clientCredit.setClient(clientComboBox.getValue());
        clientCredit.setCredit(creditComboBox.getValue());
        clientCredit.setCreditSum(Long.parseLong(tfCreditSum.getValue()));
        clientCredit.setTime(Long.parseLong(tfTimeOfCredit.getValue()));
        return clientCredit;
    }
}

