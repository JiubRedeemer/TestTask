package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.ClientDB;
import com.haulmont.testtask.entities.Client;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class ClientEditUI extends VerticalLayout {

    ClientView clientView;

    private ClientDB clientDB = new ClientDB();
    private TextField tfName = new TextField("Name");
    private TextField tfSurname = new TextField("Surname");
    private TextField tfPatronymic = new TextField("Patronymic");
    private TextField tfPhone = new TextField("Phone");
    private TextField tfEmail = new TextField("Email");
    private TextField tfPassport = new TextField("Passport");

    private Button add = new Button("Add");
    private Button delete = new Button("Delete");
    private Button update = new Button("Update");
    private Button cancel = new Button("Cancel");
    private Client client;

    public ClientEditUI(Client client, ClientView clientView) {
        this.client = client;
        setVisible(false);
        setWidthUndefined();
        HorizontalLayout layout = new HorizontalLayout();
        cancel.addClickListener(event -> this.setVisible(false));
        addClickListeners(clientView);
        layout.addComponents(add, delete, update, cancel);
        addComponents(tfName, tfSurname, tfPatronymic, tfPhone, tfEmail, tfPassport, layout);
    }

    public void editConfigure(Client client) {
        setVisible(true);
        if (client == null) {
            clear();
            add.setVisible(true);
            delete.setVisible(false);
            update.setVisible(false);
        } else {
            this.client = client;
            setClient(client);
            add.setVisible(false);
            delete.setVisible(true);
            update.setVisible(true);
        }
        tfName.setPlaceholder("Enter name");
        tfSurname.setPlaceholder("Enter surname");
        tfPatronymic.setPlaceholder("Enter patronymic");
        tfPhone.setPlaceholder("Enter phone");
        tfEmail.setPlaceholder("Enter email");
        tfPassport.setPlaceholder("Enter passport");
    }

    private void clear(){
        tfName.clear();
        tfSurname.clear();
        tfPatronymic.clear();
        tfPhone.clear();
        tfEmail.clear();
        tfPassport.clear();
    }

    private void addClickListeners(ClientView clientView){
        add.addClickListener(event -> {
            try {
                if (fieldCheck()){
                    addClient();
                    clientView.updateGrid();
                    this.setVisible(false);
                    clear();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        update.addClickListener(event -> {
            try {
                updateClient(client);
                clientView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        delete.addClickListener(event -> {
            try {
                clientDB.deleteClient(client);
                clientView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck(){
        if (tfName.isEmpty() || tfSurname.isEmpty() || tfPatronymic.isEmpty() || tfEmail.isEmpty() || tfPhone.isEmpty() || tfPassport.isEmpty()){
            add.setComponentError(new UserError("Не все поля введены"));
            return false;
        } else {
            add.setComponentError(null);
            return true;
        }
    }

    private void setClient(Client client){
        tfName.setValue(client.getName());
        tfSurname.setValue(client.getSurname());
        tfPatronymic.setValue(client.getPatronymic());
        tfPhone.setValue(client.getPhone());
        tfEmail.setValue(client.getEmail());
        tfPassport.setValue(client.getPassport());
    }

    private void addClient() throws SQLException {
        Client client = new Client(tfName.getValue(),
                tfSurname.getValue(),
                tfPatronymic.getValue(),
                tfPhone.getValue(),
                tfEmail.getValue(),
                tfPassport.getValue());
        clientDB.addClient(client);
    }

    private void updateClient(Client client) throws SQLException{
        client.setName(tfName.getValue());
        client.setSurname(tfSurname.getValue());
        client.setPatronymic(tfPatronymic.getValue());
        client.setPhone(tfPhone.getValue());
        client.setEmail(tfEmail.getValue());
        client.setPassport(tfPassport.getValue());
        clientDB.updateClient(client);
    }
}