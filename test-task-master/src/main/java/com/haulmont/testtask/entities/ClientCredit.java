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

    @Column(name = "clientFullName")
    private String clientFullName;

    @Column(name = "creditName")
    private String creditName;

    @Column(name = "creditSum")
    private long creditSum;

    @Column(name = "percent")
    private String percent;

    @Column(name = "time")
    private long time;

   @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
   @JoinColumn(name = "idClient")
   private Client client;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCredit")
    private Credit credit;

    @OneToOne(mappedBy = "clientCredit", orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Payments payments;


    public ClientCredit() {
    }

    public ClientCredit(Client client, Credit credit, long creditSum, long time) throws Exception {
        this.client = client;
        this.credit = credit;
        this.time = time;
        if(creditSum < credit.getLimit())
        this.creditSum = creditSum; else throw new Exception("Credit sum > credit limit");
        clientFullName = client.getName()+" "+client.getSurname()+" "+client.getPatronymic();
        creditName=credit.getName();
    }

    public ClientCredit(Client client, Credit credit, Payments payments) {
        this.client = client;
        this.credit = credit;
        this.payments = payments;
        clientFullName = client.getName()+" "+client.getSurname()+""+client.getPatronymic();
        creditName=credit.getName();
    }


    public String getIdClientCredit() {
        return idClientCredit;
    }

    public void setIdClientCredit(String idClientCredit) {
        this.idClientCredit = idClientCredit;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client; clientFullName = client.getName()+" "+client.getSurname()+""+client.getSurname();
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit; creditName=credit.getName();
    }

    public Payments getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    public long getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(long creditSum) {
        this.creditSum = creditSum;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ClientCredit{" +
                "idClientCredit='" + idClientCredit + '\'' +
                ", clientFullName='" + clientFullName + '\'' +
                ", creditName='" + creditName + '\'' +
                ", creditSum=" + creditSum +
                ", percent='" + percent + '\'' +
                ", time='" + time + '\'' +
                ", client=" + client +
                ", credit=" + credit +
                ", payments=" + payments +
                '}';
    }
}
