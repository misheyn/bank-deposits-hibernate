package models;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house_number")
    private Integer houseNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_code")
    private Client client;

    public Address() {
    }

    public Address(String city, String street, Integer houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return ", Address: " + city + ", " + street + ", " + houseNumber;
    }
}
