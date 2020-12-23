package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "cursussen")
public abstract class Cursus {
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(columnDefinition = "binary(16)")
    private UUID id;
    private String naam;

    public Cursus(String naam) {
        this.naam = naam;
        this.id = UUID.randomUUID();
    }

    protected Cursus() {
    }

    public UUID getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
