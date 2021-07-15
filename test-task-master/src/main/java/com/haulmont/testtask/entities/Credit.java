package com.haulmont.testtask.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Credit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "idCredit")
    private String idCredit;

    @Column(name = "name")
    private String name;

    @Column(name = "limit")
    private long limit;

    @Column(name = "percent")
    private float percent;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idBank")
    private Bank bank;

    @OneToOne(mappedBy = "credit")
    private ClientCredit clientCredit;

    public Credit() {
    }

    public Credit(String name, long limit, float percent) {
        this.name = name;
        this.limit = limit;
        this.percent = percent;
    }

    public Credit(String name, long limit, float percent, Bank bank) {
        this.name = name;
        this.limit = limit;
        this.percent = percent;
        this.bank = bank;
    }

    public Credit(String name, long limit, float percent, Bank bank, ClientCredit clientCredit) {
        this.name = name;
        this.limit = limit;
        this.percent = percent;
        this.bank = bank;
        this.clientCredit = clientCredit;
    }
}
