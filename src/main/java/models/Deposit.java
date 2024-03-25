package models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_code")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "storage_period")
    private int period;
    @Column(name = "rate")
    private double rate;

    @OneToMany(mappedBy = "deposit", fetch = FetchType.EAGER)
    private List<ClientAccount> clientAccounts;

    public Deposit() {
    }

    public Deposit(String name, int period, double rate) {
        this.name = name;
        this.period = period;
        this.rate = rate;
        clientAccounts = new ArrayList<ClientAccount>();
    }

    public void addClientAccount(ClientAccount clientAccount) {
        clientAccount.setDeposit(this);
        clientAccounts.add(clientAccount);
    }

    public void removeClientAccount(ClientAccount clientAccount) {
        clientAccounts.remove(clientAccount);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public List<ClientAccount> getClientAccounts() {
        return clientAccounts;
    }

    public void setClientAccounts(List<ClientAccount> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Storage period (months): " + period +
                ", Rate, %: " + rate;
    }
}
