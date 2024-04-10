package it.zancanela.peoplehub.services.impl;

import it.zancanela.peoplehub.entities.Address;
import it.zancanela.peoplehub.entities.Person;
import it.zancanela.peoplehub.exceptions.DataIntegrityViolationException;
import it.zancanela.peoplehub.exceptions.EntityNotFoundException;
import it.zancanela.peoplehub.repositories.AddressRepository;
import it.zancanela.peoplehub.services.AddressService;
import it.zancanela.peoplehub.services.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final PersonService personService;

    private final AddressRepository repository;

    public AddressServiceImpl(PersonService personService, AddressRepository repository) {
        this.personService = personService;
        this.repository = repository;
    }

    @Override
    @Transactional
    public void addAdresses(String personId, List<Address> adresses) {
        adresses = adresses.stream().map(address -> {
                    address.setMain(false);
                    return address;
                })
                .toList();

        Person person = personService.findById(personId);

        adresses = adresses.stream().map(address -> {
                    address.setPerson(person);
                    return address;
                })
                .toList();

        person.setAdresses(repository.saveAll(adresses));

        personService.save(person);
    }

    @Override
    public Address findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Address with id [" +
                                id +
                                "] not found"));
    }

    @Override
    public List<Address> findAllByPerson(String personId) {
        Person person = personService.findById(personId);

        return repository.findAllByPerson(person);
    }

    @Override
    @Transactional
    public Address update(String id, Address address) {
        Address existingAddress = findById(id);

        existingAddress.setPublicPlace(address.getPublicPlace());
        existingAddress.setAddressType(address.getAddressType());
        existingAddress.setNumber(address.getNumber());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setZipCode(address.getZipCode());

        return repository.save(existingAddress);
    }

    @Override
    @Transactional
    public void setMainAddress(String personId, String addressId) {
        List<Address> adresses = this.findAllByPerson(personId);

        boolean personHasNoProvidedAddress =
                adresses.stream()
                        .filter(address -> address.getId().equals(addressId))
                        .toList()
                        .isEmpty();
        if (adresses.isEmpty() || personHasNoProvidedAddress)
            throw new DataIntegrityViolationException(
                    "Person with id [" +
                            personId +
                            "] does not have the address with id [" +
                            addressId +
                            "] linked"
            );

        repository.saveAll(adresses.stream().map(
                        address -> {
                            address.setMain(false);
                            if (address.getId().equals(addressId))
                                address.setMain(true);
                            return address;
                        })
                .toList());
    }
}
