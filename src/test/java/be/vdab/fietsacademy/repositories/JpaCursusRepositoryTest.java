package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Groepscursus;
import be.vdab.fietsacademy.domain.IndividueleCursus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaCursusRepository.class)
@Sql("/insertCursus.sql")
public class JpaCursusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String CURSUSSEN = "cursussen";
    private static final String GROEPS_CURSUSSEN = "groepscursussen";
    private static final String INDIVIDUELE_CURSUSSEN = "individuelecursussen";
    private static final LocalDate EEN_DATUM = LocalDate.of(2019, 1, 1);
    private final JpaCursusRepository repository;

    public JpaCursusRepositoryTest(JpaCursusRepository repository) {
        this.repository = repository;
    }

    private long idVanTestGroepsCursus() {
        return super.jdbcTemplate.queryForObject(
                "select id from cursussen where naam='testGroep'", Long.class);
    }
    private long idVanTestIndividueleCursus() {
        return super.jdbcTemplate.queryForObject(
                "select id from cursussen where naam='testIndividueel'", Long.class);
    }

    @Test
    void findGroepsCursusById(){
        var cursus = repository.findById(idVanTestGroepsCursus()).get();
        assertThat(cursus).isInstanceOf(Groepscursus.class);
        assertThat(cursus.getNaam()).isEqualTo("testGroep");
    }

    @Test
    void findIndividueleCursusById(){
        var cursus = repository.findById(idVanTestIndividueleCursus()).get();
        assertThat(cursus).isInstanceOf(IndividueleCursus.class);
        assertThat(cursus.getNaam()).isEqualTo("testIndividueel");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void createGroepsCursus(){
        var cursus = new Groepscursus("testGroep2", EEN_DATUM, EEN_DATUM);
        repository.create(cursus);
        assertThat(super.countRowsInTableWhere(CURSUSSEN,
                "id = '" + cursus.getId() +"'")).isOne();
        assertThat(super.countRowsInTableWhere(GROEPS_CURSUSSEN,
                "id = '" + cursus.getId() +"'")).isOne();
    }

    @Test
    void createIndividueleCursus(){
        var cursus = new IndividueleCursus("testIndividueel2", 7);
        repository.create(cursus);
        assertThat(super.countRowsInTableWhere(CURSUSSEN,
                "id = '" + cursus.getId() +"'")).isOne();
        assertThat(super.countRowsInTableWhere(INDIVIDUELE_CURSUSSEN,
                "id = '" + cursus.getId() +"'")).isOne();
    }
}
