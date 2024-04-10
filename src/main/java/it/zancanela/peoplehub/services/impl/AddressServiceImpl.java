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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        if (Objects.isNull(person.getAdresses()))
            person.setAdresses(new ArrayList<>());

        List<Address> existingAdress = person.getAdresses();
        existingAdress.addAll(repository.saveAll(adresses));
        person.setAdresses(existingAdress);

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
    public void setMainAddress(String id) {
        Address address = this.findById(id);

        if (Objects.isNull(address.getPerson()))
            throw new DataIntegrityViolationException("The address with id [" +
                    address.getId() +
                    "] does not have a linked person");

        List<Address> adresses = this.findAllByPerson(address.getPerson().getId());

        repository.saveAll(adresses.stream().map(
                        a -> {
                            a.setMain(false);
                            if (a.getId().equals(id))
                                a.setMain(true);
                            return a;
                        })
                .toList());
    }
}
