package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.queryresults.IdNaamEmail;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
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

    @Override
    public void create(Docent docent) {
        manager.persist(docent);
    }

    @Override
    public void delete(long id) {
        findById(id).ifPresent(docent -> manager.remove(docent));
    }

    @Override
    public List<Docent> findAll() {
        return manager.createQuery("select d from Docent d order by d.wedde", Docent.class)
                .getResultList();
    }

    @Override
    public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot) {
        return manager.createNamedQuery("Docent.findByWeddeBetween", Docent.class)
                .setParameter("van", van).setParameter("tot", tot)
                .setHint("javax.persistence.loadgraph",
                        manager.createEntityGraph("Docent.metCampusEnVerantwoordelijkheden"))
                .getResultList();
//        return manager.createQuery(
//                "select d from Docent d where d.wedde between :van and :tot", Docent.class)
//                .setParameter("van", van).setParameter("tot", tot).getResultList();
    }

    @Override
    public List<IdNaamEmail> findAllEmail() {
        return manager.createQuery(
                "select new be.vdab.fietsacademy.queryresults.IdNaamEmail(d.id, d.familienaam, d.emailAdres)" +
                " from Docent d", IdNaamEmail.class).getResultList();
    }

    @Override
    public int algemeneOpslag(BigDecimal percentage) {
        var factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        return manager.createNamedQuery("Docent.algemeneOpslag")
                .setParameter("factor", factor)
                .executeUpdate();
    }

    @Override
    public Optional<Docent> findByIdWithLock(long id) {
        return Optional.ofNullable(manager.find(Docent.class, id, LockModeType.PESSIMISTIC_WRITE));
    }
}
