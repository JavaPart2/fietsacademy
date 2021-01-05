package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.queryresults.IdNaamEmail;
import org.apache.catalina.LifecycleState;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DocentRepository {
    Optional<Docent> findById(long id);
    void create(Docent docent);
    void delete(long id);
    List<Docent> findAll();
    List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot);
    List<IdNaamEmail> findAllEmail();
    int algemeneOpslag(BigDecimal percentage);
    Optional<Docent> findByIdWithLock(long id);
}
