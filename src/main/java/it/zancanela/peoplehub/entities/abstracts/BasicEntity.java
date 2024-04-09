package it.zancanela.peoplehub.entities.abstracts;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;

@MappedSuperclass
public abstract class BasicEntity {
    @Id
    @UuidGenerator
    @Column(length = 36)
    private String id;

    protected BasicEntity() {}
    protected BasicEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
