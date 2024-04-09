package it.zancanela.peoplehub.entities;

import it.zancanela.peoplehub.entities.abstracts.BasicEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "PERSON")
public class Person extends BasicEntity {
    @Column(name = "NAME")
    private String name;
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "person"
            , cascade = {CascadeType.ALL}
            , orphanRemoval = true)
    private List<Address> adresses;

    public Optional<Address> getMainAddress() {
        if (Objects.isNull(getAdresses()))
            return Optional.empty();

        return getAdresses().stream()
                .filter(Address::isMain)
                .findFirst();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Address> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Address> adresses) {
        this.adresses = adresses;
    }
}
