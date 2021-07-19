package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.ClientCreditDB;
import com.haulmont.testtask.dao.CreditDB;
import com.haulmont.testtask.entities.Credit;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class CreditEditUI extends VerticalLayout {

    CreditView creditView;

    private CreditDB creditDB = new CreditDB();
    private ClientCreditDB clientCreditDB = new ClientCreditDB();
    private TextField tfName = new TextField("Name");
    private TextField tfLimit = new TextField("Limit");
    private TextField tfPercent = new TextField("Percent");


    private Button add = new Button("Add");
    private Button delete = new Button("Delete");
    private Button update = new Button("Update");
    private Button cancel = new Button("Cancel");
    private Credit credit;

    public CreditEditUI(Credit credit, CreditView creditView) {
        this.credit = credit;
        setVisible(false);
        setWidthUndefined();
        HorizontalLayout layout = new HorizontalLayout();
        cancel.addClickListener(event -> this.setVisible(false));
        addClickListeners(creditView);
        layout.addComponents(add, delete, update, cancel);
        addComponents(tfName, tfLimit, tfPercent, layout);
    }

    public void editConfigure(Credit credit) {
        setVisible(true);
        if (credit == null) {
            clear();
            add.setVisible(true);
            delete.setVisible(false);
            update.setVisible(false);
        } else {
            this.credit = credit;
            setCredit(credit);
            add.setVisible(false);
            delete.setVisible(true);
            update.setVisible(true);
        }
        tfName.setPlaceholder("Enter name");
        tfLimit.setPlaceholder("Enter limit");
        tfPercent.setPlaceholder("Enter percent");

    }

    private void clear() {
        tfName.clear();
        tfLimit.clear();
        tfPercent.clear();
    }

    private void addClickListeners(CreditView creditView) {
        add.addClickListener(event -> {
            try {
                if (fieldCheck()) {
                    addCredit();
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        update.addClickListener(event -> {
            try {
                if (fieldCheck()) {
                    updateCredit(credit);
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        delete.addClickListener(event -> {
            try {
                if (credit.getClientCredits().isEmpty()) {
                    creditDB.deleteCredit(credit);
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();
                } else delete.setComponentError(new UserError("Credit has clients"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck() {
        String rgxLimit = "^-?\\d+$";
        String rgxPercent = "^[0-9]*[.,]?[0-9]+$";
        if (tfName.isEmpty() || tfLimit.isEmpty() || tfPercent.isEmpty()) {
            add.setComponentError(new UserError("Не все поля введены"));
            return false;
        } else {
            add.setComponentError(null);
            tfLimit.setComponentError(null);
            tfPercent.setComponentError(null);

            if (!tfLimit.getValue().matches(rgxLimit)) {
                tfLimit.setComponentError(
                        new UserError("Введите максимальную сумму кредита, например: 1000000"));
                return false;
            }
            if (!tfPercent.getValue().matches(rgxPercent)) {
                tfPercent.setComponentError(
                        new UserError("Введите процентную ставку, например: 12.4"));
                return false;
            }


            return true;
        }
    }

    private void setCredit(Credit credit) {
        tfName.setValue(credit.getName());
        tfLimit.setValue(Long.toString(credit.getLimit()));
        tfPercent.setValue(Float.toString(credit.getPercent() * 100));

    }

    private void addCredit() throws SQLException {
        Credit credit = new Credit(tfName.getValue(),
                Long.parseLong(tfLimit.getValue()),
                (Float.parseFloat(tfPercent.getValue())) / 100);

        creditDB.addCredit(credit);
    }

    private void updateCredit(Credit credit) throws SQLException {
        credit.setName(tfName.getValue());
        credit.setLimit(Long.parseLong(tfLimit.getValue()));
        credit.setPercent(Float.parseFloat(tfPercent.getValue()) / 100);

        creditDB.updateCredit(credit);
    }
}

