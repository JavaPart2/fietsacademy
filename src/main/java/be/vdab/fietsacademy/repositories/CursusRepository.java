package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Cursus;

import java.util.Optional;
import java.util.UUID;

public interface CursusRepository {
    Optional<Cursus> findById(UUID id);
    void create(Cursus cursus);
}
