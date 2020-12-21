package be.vdab.fietsacademy.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/insertDocent.sql")
@Import(JPADocentRepository.class)
public class JPADocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JPADocentRepository jpaDocentRepository;

    public JPADocentRepositoryTest(JPADocentRepository jpaDocentRepository) {
        this.jpaDocentRepository = jpaDocentRepository;
    }

    private long idVanTestMan(){
        return super.jdbcTemplate.queryForObject(
                "select id from docenten where voornaam = 'testM'", long.class);
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
}
