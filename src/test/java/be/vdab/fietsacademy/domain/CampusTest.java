package be.vdab.fietsacademy.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
public class CampusTest {
    private Campus campusTest;
    private final EntityManager manager;

    public CampusTest(EntityManager manager) {
        this.manager = manager;
    }

    @Test
    void create(){
        var adres = new Adres("straat X", "huisnr X", "postcode X", "gemeente X");
        var campus = new Campus("naam X", adres);

        manager.persist(campus);
    }

    void read(){
        var campus = manager.find(Campus.class, 1);
    }
}
