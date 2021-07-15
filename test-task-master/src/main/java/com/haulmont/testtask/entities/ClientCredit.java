package com.haulmont.testtask.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientCredit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name ="uuid", strategy = "uuid")
    @Column(name = "idClientCredit")
    private String idClientCredit;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "idClient")
   private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCredit")
    private Credit credit;

    @OneToOne(mappedBy = "clientCredit", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payments payments;


    public ClientCredit() {
    }

    public ClientCredit(Client client, Credit credit) {
        this.client = client;
        this.credit = credit;
    }

    public ClientCredit(Client client, Credit credit, Payments payments) {
        this.client = client;
        this.credit = credit;
        this.payments = payments;
    }

    public String getIdClientCredit() {
        return idClientCredit;
    }

    public void setIdClientCredit(String idClientCredit) {
        this.idClientCredit = idClientCredit;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public Payments getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "ClientCredit{" +
                "idClientCredit='" + idClientCredit + '\'' +
                ", client=" + client +
                ", credit=" + credit +
                ", payments=" + payments +
                '}';
    }
}
