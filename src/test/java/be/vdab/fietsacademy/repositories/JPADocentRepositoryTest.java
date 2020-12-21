package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.domain.Geslacht;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/insertDocent.sql")
@Import(JPADocentRepository.class)
public class JPADocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JPADocentRepository jpaDocentRepository;
    private static final String DOCENTEN = "docenten";
    private Docent docent;
    private final EntityManager manager;

    public JPADocentRepositoryTest(JPADocentRepository jpaDocentRepository, EntityManager manager) {
        this.jpaDocentRepository = jpaDocentRepository;
        this.manager = manager;
    }

    private long idVanTestMan(){
        return super.jdbcTemplate.queryForObject(
                "select id from docenten where voornaam = 'testM'", long.class);
    }

    private long idVanTestVrouw(){
        return super.jdbcTemplate.queryForObject(
                "select id from docenten where voornaam = 'testV'", long.class);
    }

    @BeforeEach
    void beforeEach(){
        docent = new Docent("test", "test",
                Geslacht.MAN, BigDecimal.TEN, "test@test.be");
    }

    @Test
    void findById(){
        assertThat(jpaDocentRepository.findById(idVanTestMan())
        .get().getVoornaam()).isEqualTo("testM");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(jpaDocentRepository.findById(-1)).isNotPresent();
    }

    @Test
    void man(){
        assertThat(jpaDocentRepository.findById(idVanTestMan())
        .get().getGeslacht()).isEqualTo(Geslacht.MAN);
    }

    @Test
    void vrouw(){
        assertThat(jpaDocentRepository.findById(idVanTestVrouw())
                .get().getGeslacht()).isEqualTo(Geslacht.VROUW);
    }

    @Test
    void create(){
        jpaDocentRepository.create(docent);
        assertThat(docent.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(DOCENTEN, "id = " + docent.getId())).isOne();
    }

    @Test
    void delete(){
        var id = idVanTestMan();
        jpaDocentRepository.delete(id);
        manager.flush();
        assertThat(super.countRowsInTableWhere(DOCENTEN, "id = " + id)).isZero();
    }
}
