package models;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "client_account")
public class ClientAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number")
    private int id;
    @Column(name = "account_opening_date")
    private Date dateOpen;
    @Column(name = "account_closing_date")
    private Date dateClose;
    @Column(name = "invested_amount")
    private long amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_code")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_code")
    private Deposit deposit;

    public ClientAccount() {
    }

    public ClientAccount(Date dateOpen, Date dateClose, long amount) {
        this.dateOpen = dateOpen;
        this.dateClose = dateClose;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public Date getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(Date dateOpen) {
        this.dateOpen = dateOpen;
    }

    public Date getDateClose() {
        return dateClose;
    }

    public void setDateClose(Date dateClose) {
        this.dateClose = dateClose;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "Account number: " + id +
                ", opening date: " + dateFormat.format(dateOpen) + ", closing date: " + dateFormat.format(dateClose) +
                ", invested amount = " + amount;
    }
}
