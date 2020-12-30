package be.vdab.fietsacademy.services;

import be.vdab.fietsacademy.domain.Adres;
import be.vdab.fietsacademy.domain.Campus;
import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.domain.Geslacht;
import be.vdab.fietsacademy.exceptions.DocentNietGevondenException;
import be.vdab.fietsacademy.repositories.DocentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultDocentServiceTest {
    private DefaultDocentService service;

    @Mock
    private DocentRepository repository;
    private Docent docent;

    @BeforeEach
    void beforeEach(){
        var campus = new Campus("test",
                new Adres("test", "test", "test", "test"));
        docent = new Docent("test", "test", Geslacht.MAN,
                BigDecimal.valueOf(100), "test@test.be", campus);
        service = new DefaultDocentService(repository);
    }

    @Test
    void opslag(){
        when(repository.findById(1)).thenReturn(Optional.of(docent));
        service.opslag(1, BigDecimal.TEN);
        assertThat(docent.getWedde()).isEqualByComparingTo("110");
        verify(repository).findById(1);
    }

    @Test
    void opslagVoorOnbestaandeDocent(){
        assertThatExceptionOfType(DocentNietGevondenException.class)
                .isThrownBy(() -> service.opslag(-1, BigDecimal.TEN));
        verify(repository).findById(-1);
    }
}
