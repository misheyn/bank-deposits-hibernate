package models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_code")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "passport_series")
    private int passportSeries;
    @Column(name = "passport_number")
    private int passportNumber;
    @Column(name = "phone_number")
    private long phoneNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ClientAccount> clientAccounts;
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    public Client() {
    }

    public Client(String firstName, String lastName, String patronymic, int passportSeries, int passportNumber,
                  long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.phoneNumber = phoneNumber;
        clientAccounts = new ArrayList<ClientAccount>();
    }

    public void addClientAccount(ClientAccount clientAccount) {
        clientAccount.setClient(this);
        clientAccounts.add(clientAccount);
    }

    public void removeClientAccount(ClientAccount clientAccount) {
        clientAccounts.remove(clientAccount);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(int passportSeries) {
        this.passportSeries = passportSeries;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ClientAccount> getClientAccounts() {
        return clientAccounts;
    }

    public void setClientAccounts(List<ClientAccount> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    @Override
    public String toString() {
        return "Fullname: " + lastName + " " + firstName + " " + patronymic +
                ", Passport data: " + passportSeries + " " + passportNumber +
                ", Phone: " + phoneNumber;
    }
}