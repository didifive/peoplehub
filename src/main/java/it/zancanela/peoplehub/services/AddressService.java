package it.zancanela.peoplehub.services;

import it.zancanela.peoplehub.entities.Address;

import java.util.List;

public interface AddressService {
    void addAdresses(String personId, List<Address> newAdresses);

    default void addAddress(String personId, Address address) {
        this.addAdresses(personId, List.of(address));
    }

    Address findById(String id);

    List<Address> findAllByPerson(String personId);

    Address update(String id, Address address);

    void setMainAddress(String personId, String addressId);

}
