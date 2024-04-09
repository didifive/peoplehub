package it.zancanela.peoplehub.entities;

import it.zancanela.peoplehub.entities.abstracts.BasicEntity;
import it.zancanela.peoplehub.enums.AddressType;
import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address extends BasicEntity {

    @Column(name = "PUBLIC_PLACE")
    private String publicPlace;
    @Column(name = "NUMBER")
    private Integer number;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP_CODE", length = 8)
    private String zipCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private AddressType addressType;
    @Column(name = "MAIN")
    private boolean main;
    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    Person person;

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
