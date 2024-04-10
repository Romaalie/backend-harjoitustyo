package com.romaalie.budjetointiharjoitustyo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.romaalie.budjetointiharjoitustyo.domain.Paaluokka;
import com.romaalie.budjetointiharjoitustyo.domain.PaaluokkaRepository;

import jakarta.transaction.Transactional;

@DataJpaTest

@Transactional
@Rollback(true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class PaaluokkaRepoTests {

    @Autowired
    PaaluokkaRepository paaluokkaRepository;

    //Testaa tallennusta tietokantaan ja automaattista ID:n luomista sekä hakua tietokannasta.
    @Test
    public void paaluokkaLuontiTesti() {

        //Luodaan uusi pääluokka ja tallennetaan se tietokantaan.
        Paaluokka testiPaaluokka = new Paaluokka("TestiPaaluokka");
        paaluokkaRepository.save(testiPaaluokka);

        //Haetaan Paaluokka tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Paaluokka> haettuPaaluokka = paaluokkaRepository.findById(testiPaaluokka.getId());

        //Testataan, että haettu pääluokka on tietokannassa.
        assertTrue(haettuPaaluokka.isPresent());
    }

    //Testaa tiedon päivittämistä tietokantaan.
    @Test
    public void paaluokkaMuutosTesti() {

        //Luodaan uusi pääluokka ja tallennetaan se tietokantaan.
        Paaluokka testiPaaluokka = new Paaluokka("TestiPaaluokka");
        paaluokkaRepository.save(testiPaaluokka);

        //Muutetaan pääluokan nimeä ja tallennetaan muutos tietokantaan.
        testiPaaluokka.setNimi("UusiTestiPaaluokka");
        paaluokkaRepository.save(testiPaaluokka);

        //Haetaan pääluokka tietokannasta ja tarkistetaan nimi.
        Optional<Paaluokka> haettuPaaluokka = paaluokkaRepository.findById(testiPaaluokka.getId());
        assertEquals("UusiTestiPaaluokka", haettuPaaluokka.get().getNimi());
        assertNotEquals("TestiPaaluokka", haettuPaaluokka.get().getNimi());

    }

    //Testaa tiedon poistamista tietokannasta.
    @Test
    public void paaluokkaPoistoTesti() {

        //Luodaan uusi pääluokka ja tallennetaan se tietokantaan.
        Paaluokka testiPaaluokka = new Paaluokka("TestiPaaluokka");
        paaluokkaRepository.save(testiPaaluokka);

        //Poistetaan luotu pääluokka, haetaan sen id:llä pääluokkaa tietokannasta ja tarkistetaan onko se olemassa.
        paaluokkaRepository.delete(testiPaaluokka);
        Optional<Paaluokka> haettuPaaluokka = paaluokkaRepository.findById(testiPaaluokka.getId());
        assertFalse(haettuPaaluokka.isPresent());

    }

}
