package com.romaalie.budjetointiharjoitustyo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.romaalie.budjetointiharjoitustyo.domain.Aliluokka;
import com.romaalie.budjetointiharjoitustyo.domain.AliluokkaRepository;
import com.romaalie.budjetointiharjoitustyo.domain.Paaluokka;
import com.romaalie.budjetointiharjoitustyo.domain.PaaluokkaRepository;

import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Rollback(true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class AliluokkaRepoTests {

    @Autowired
    private AliluokkaRepository aliluokkaRepository;

    @Autowired
    private PaaluokkaRepository paaluokkaRepository;

    private Paaluokka paaluokka;

    @BeforeEach
    public void setUp() {
        //DANGERZONE, tyhjennetään pääluokka ja aliluokka tietokantataulut testejä varten.
        // Ehkä vaihdetaan tämä kun siirrytään ulkoiseen tietokantaan.
        paaluokkaRepository.deleteAll();
        aliluokkaRepository.deleteAll();
        //DANGERZONE
        //Luodaan uusi pääluokka nimeltään Lapset ja tallennetaan se tietokantaan.
        paaluokka = new Paaluokka("Lapset");
        paaluokkaRepository.save(paaluokka);
    }

    //Testaa tallennusta tietokantaan ja automaattista ID:n luomista sekä hakua tietokannasta.
    @Test
    public void aliluokkaTallennusTesti() {
        //Luodaan kaksi uutta aliluokkaa käyttäen valmista pääluokkaa.
        Aliluokka aliluokka = new Aliluokka("Vaatteet", paaluokka);
        Aliluokka aliluokka2 = new Aliluokka("Lelut", paaluokka);

        //Tallennetaan aliluokat tietokantaan.
        aliluokkaRepository.save(aliluokka);
        aliluokkaRepository.save(aliluokka2);

        //Haetaan aliluokat tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Aliluokka> haettuAliluokka = aliluokkaRepository.findById(aliluokka.getId());
        Optional<Aliluokka> haettuAliluokka2 = aliluokkaRepository.findById(aliluokka2.getId());

        //Testataan etteivät aliluokat ole tyhjiä.
        assertNotNull(haettuAliluokka);
        assertNotNull(haettuAliluokka2);

        //Testataan, että aliluokilla on Id arvot, jotka ovat isommat kuin 0.
        assertTrue(haettuAliluokka.isPresent());
        assertTrue(haettuAliluokka2.isPresent());

        //Testataan, että aliluokilla on annetut nimet.
        assertEquals("Vaatteet", haettuAliluokka.get().getNimi());
        assertEquals("Lelut", haettuAliluokka2.get().getNimi());

        //Testataan, että ylemmästä testistä ei mene läpi satunnaiset nimet.
        assertNotEquals("paaluokka", haettuAliluokka.get().getNimi());
        assertNotEquals("Vaatteet", haettuAliluokka2.get().getNimi());
    }

    //Testaa tiedon päivittämistä tietokantaan.
    @Test
    public void aliluokkaMuutosTesti() {
        //Luodaan kaksi uutta aliluokkaa käyttäen valmista pääluokkaa.
        Aliluokka aliluokka = new Aliluokka("Vaatteet", paaluokka);
        Aliluokka aliluokka2 = new Aliluokka("Lelut", paaluokka);

        //Tallennetaan aliluokat tietokantaan.
        aliluokkaRepository.save(aliluokka);
        aliluokkaRepository.save(aliluokka2);

        //Asetetaan aliluokille uudet nimet
        aliluokka.setNimi("Harrastukset");
        aliluokka2.setNimi("Mysteeriarvo");

        //Tallennetaan aliluokat tietokantaan.
        aliluokkaRepository.save(aliluokka);
        aliluokkaRepository.save(aliluokka2);

        //Haetaan aliluokat tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Aliluokka> haettuAliluokka = aliluokkaRepository.findById(aliluokka.getId());
        Optional<Aliluokka> haettuAliluokka2 = aliluokkaRepository.findById(aliluokka2.getId());

        //Testataan, että aliluokilla on uudet päivitetyt nimet.
        assertEquals("Harrastukset", haettuAliluokka.get().getNimi());
        assertEquals("Mysteeriarvo", haettuAliluokka2.get().getNimi());
    }

    //Testaa tiedon poistamista tietokannasta.
    @Test
    public void aliluokkaPoistoTesti() {
        //Luodaan kaksi uutta aliluokkaa käyttäen valmista pääluokkaa.
        Aliluokka aliluokka = new Aliluokka("Vaatteet", paaluokka);
        Aliluokka aliluokka2 = new Aliluokka("Lelut", paaluokka);

        //Tallennetaan aliluokat tietokantaan.
        aliluokkaRepository.save(aliluokka);
        aliluokkaRepository.save(aliluokka2);

        //Poistetaan ensimmäinen aliluokka.
        aliluokkaRepository.delete(aliluokka);

        //Haetaan aliluokat tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Aliluokka> haettuAliluokka = aliluokkaRepository.findById(aliluokka.getId());
        Optional<Aliluokka> haettuAliluokka2 = aliluokkaRepository.findById(aliluokka2.getId());

        //Testataan, että poistettu aliluokka on poistunut, mutta toinen on olemassa.
        assertFalse(haettuAliluokka.isPresent());
        assertTrue(haettuAliluokka2.isPresent());

    }

    @Test
    public void findByPaaluokkaIdTesti() {
        //Luodaan kaksi uutta aliluokkaa käyttäen valmista pääluokkaa.
        Aliluokka aliluokka = new Aliluokka("Vaatteet", paaluokka);
        Aliluokka aliluokka2 = new Aliluokka("Lelut", paaluokka);

        //Tallennetaan aliluokat tietokantaan.
        aliluokkaRepository.save(aliluokka);
        aliluokkaRepository.save(aliluokka2);

        //Haetaan aliluokat tietokannasta pääluokan id:llä. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        List<Aliluokka> haetutAliluokat = aliluokkaRepository.findByPaaluokkaId(paaluokka.getId());

        //Testataan, että paaluokan id:llä löytyy lista kooltaan kaksi.
        assertEquals(2, haetutAliluokat.size());

        //Testataan, että haetuilla luokilla on oikeat nimet.
        assertEquals("Vaatteet", haetutAliluokat.get(0).getNimi());
        assertEquals("Lelut", haetutAliluokat.get(1).getNimi());

        //Testataan, että kaikki nimet eivät mene läpi.
        assertNotEquals("Lelut", haetutAliluokat.get(0).getNimi());
        assertNotEquals("Vaatteet", haetutAliluokat.get(1).getNimi());

    }
;

}
