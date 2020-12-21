package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JPADocentRepository implements DocentRepository{
    private final EntityManager manager;

    public JPADocentRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Docent> findById(long id) {
        return Optional.ofNullable(manager.find(Docent.class, id));
    }
}
