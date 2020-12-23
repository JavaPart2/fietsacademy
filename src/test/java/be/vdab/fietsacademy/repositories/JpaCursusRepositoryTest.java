package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Groepscursus;
import be.vdab.fietsacademy.domain.IndividueleCursus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.UUID;

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

    private final EntityManager manager;

    public JpaCursusRepositoryTest(JpaCursusRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private UUID idVanTestGroepsCursus() {
        return super.jdbcTemplate.queryForObject(
                "select bin_to_uuid(id) from groepscursussen where naam='testGroep'", UUID.class);
    }
    private UUID idVanTestIndividueleCursus() {
        return super.jdbcTemplate.queryForObject(
                "select bin_to_uuid(id) from individuelecursussen where naam='testIndividueel'", UUID.class);
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
        assertThat(repository.findById(UUID.randomUUID())).isNotPresent();
    }

    @Test
    void createGroepsCursus(){
        var cursus = new Groepscursus("testGroep2", EEN_DATUM, EEN_DATUM);
        repository.create(cursus);
        manager.flush();
//        assertThat(super.countRowsInTableWhere(CURSUSSEN,
//                "id = '" + cursus.getId() +"'")).isOne();
        assertThat(super.countRowsInTableWhere(GROEPS_CURSUSSEN,
                "id = uuid_to_bin('" + cursus.getId() +"')")).isOne();
    }

    @Test
    void createIndividueleCursus(){
        var cursus = new IndividueleCursus("testIndividueel2", 7);
        repository.create(cursus);
        manager.flush();
//        assertThat(super.countRowsInTableWhere(CURSUSSEN,
//                "id = '" + cursus.getId() +"'")).isOne();
        assertThat(super.countRowsInTableWhere(INDIVIDUELE_CURSUSSEN,
                "id = uuid_to_bin('" + cursus.getId() +"')")).isOne();
    }
}
