package com.haulmont.testtask.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "BANK")
public class Bank {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name ="uuid", strategy = "uuid")
    @Column(name = "idBank")
    private String idBank;
    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Client> clients;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Credit> credits;

    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    public String getIdBank() {
        return idBank;
    }

    public void setIdBank(String idBank) {
        this.idBank = idBank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "idBank='" + idBank + '\'' +
                ", name='" + name + '\'' +
                ", clients=" + clients +
                ", credits=" + credits +
                '}';
    }
}
