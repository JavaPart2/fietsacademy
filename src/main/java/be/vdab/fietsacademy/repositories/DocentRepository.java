package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;

import java.util.Optional;

public interface DocentRepository {
    Optional<Docent> findById(long id);
}
