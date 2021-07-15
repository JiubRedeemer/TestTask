package com.haulmont.testtask.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Payments {

    @Id
    @Column(name = "idClientCredit")
    private String idClientCredit;
    @Column(name = "date")
    private Date date;
    @Column(name = "sumPayment")
    private long sumPayment;
    @Column(name = "sumPaymentBody")
    private long sumPaymentBody;
    @Column(name = "sumPaymentPercents")
    private long sumPaymentPercents;
    @Column(name = "balanceOwed")
    private long balanceOwed;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idClientCredit")
    private ClientCredit clientCredit;

    public Payments() {
    }


    public Payments(Date date, long sumPayment, long sumPaymentBody, long sumPaymentPercents, long balanceOwed) {
        this.date = date;
        this.sumPayment = sumPayment;
        this.sumPaymentBody = sumPaymentBody;
        this.sumPaymentPercents = sumPaymentPercents;
        this.balanceOwed = balanceOwed;
    }

    public Payments(Date date, long sumPayment, long sumPaymentBody, long sumPaymentPercents, long balanceOwed, ClientCredit clientCredit) {
        this.date = date;
        this.sumPayment = sumPayment;
        this.sumPaymentBody = sumPaymentBody;
        this.sumPaymentPercents = sumPaymentPercents;
        this.balanceOwed = balanceOwed;
        this.clientCredit = clientCredit;
    }

    public String getIdClientCredit() {
        return idClientCredit;
    }

    public void setIdClientCredit(String idClientCredit) {
        this.idClientCredit = idClientCredit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getSumPayment() {
        return sumPayment;
    }

    public void setSumPayment(long sum) {
        this.sumPayment = sum;
    }

    public long getSumPaymentBody() {
        return sumPaymentBody;
    }

    public void setSumPaymentBody(long sumPayBody) {
        this.sumPaymentBody = sumPayBody;
    }

    public long getSumPaymentPercents() {
        return sumPaymentPercents;
    }

    public void setSumPaymentPercents(long sumPayPercents) {
        this.sumPaymentPercents = sumPayPercents;
    }

    public long getBalanceOwed() {
        return balanceOwed;
    }

    public void setBalanceOwed(long balanceOwed) {
        this.balanceOwed = balanceOwed;
    }

    public ClientCredit getClientCredit() {
        return clientCredit;
    }

    public void setClientCredit(ClientCredit clientCredit) {
        this.clientCredit = clientCredit;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "idClientCredit='" + idClientCredit + '\'' +
                ", date=" + date +
                ", sumPayment=" + sumPayment +
                ", sumPaymentBody=" + sumPaymentBody +
                ", sumPaymentPercents=" + sumPaymentPercents +
                ", balanceOwed=" + balanceOwed +
                ", clientCredit=" + clientCredit +
                '}';
    }
}
