package com.romaalie.budjetointiharjoitustyo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.romaalie.budjetointiharjoitustyo.domain.Kayttaja;
import com.romaalie.budjetointiharjoitustyo.domain.KayttajaRepository;

/* ULKOISILLE TIETOKANNOILLE
 * import org.springframework.test.annotation.Rollback;
 *
 * import jakarta.transaction.Transactional;
 */
@DataJpaTest
/* ULKOISILLE TIETOKANNOILLE
 * @Transactional
 * @Rollback(true)
 * @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 */
class KayttajaRepoTests {

    @Autowired
    private KayttajaRepository kayttajaRepository;

    //Testaa tallennusta tietokantaan ja automaattista ID:n luomista sekä hakua tietokannasta.
    @Test
    public void kayttajaLuontiTesti() {

        //Luodaan uusi käyttäjä.
        Kayttaja testiKayttaja = new Kayttaja("testikayttaja", "$2y$10$q2AlKdGpIEmqCGYgGyYdYeuPbChJZ0FOGYw7wlBT1GcdcYPIJQyUi", "kayttaja");
        //Tallennetaan käyttäjä tietokantaan.
        kayttajaRepository.save(testiKayttaja);

        //Haetaan käyttäjä tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Kayttaja> haettuKayttaja = kayttajaRepository.findById(testiKayttaja.getId());

        //Tarkistetaan, että käyttäjä on olemassa.
        assertTrue(haettuKayttaja.isPresent());

    }

    //Testaa tiedon päivittämistä tietokantaan.
    @Test
    public void kayttajaMuutosTesti() {

        //Luodaan uusi käyttäjä ja tallennetaan se tietokantaan.
        Kayttaja testiKayttaja = new Kayttaja("testikayttaja", "$2y$10$q2AlKdGpIEmqCGYgGyYdYeuPbChJZ0FOGYw7wlBT1GcdcYPIJQyUi", "kayttaja");
        kayttajaRepository.save(testiKayttaja);

        //Muutetaan käyttäjän tietoja ja tallennetaan muutokset tietokantaan.
        testiKayttaja.setKayttajaRooli("admin");
        kayttajaRepository.save(testiKayttaja);

        //Haetaan käytäjän tiedot tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Kayttaja> haettuKayttaja = kayttajaRepository.findById(testiKayttaja.getId());

        //Testataan onko tietokantaan päivittynyt käyttäjärooli.
        assertEquals("admin", haettuKayttaja.get().getKayttajaRooli());
        assertNotEquals("kayttaja", haettuKayttaja.get().getKayttajaRooli());

    }

    //Testaa tiedon poistamista tietokannasta.
    @Test
    public void kayttajaPoistoTesti() {
        //Luodaan uusi käyttäjä ja tallennetaan se tietokantaan.
        Kayttaja testiKayttaja = new Kayttaja("testikayttaja", "$2y$10$q2AlKdGpIEmqCGYgGyYdYeuPbChJZ0FOGYw7wlBT1GcdcYPIJQyUi", "kayttaja");
        kayttajaRepository.save(testiKayttaja);

        //Poistetaan käyttäjä.
        kayttajaRepository.delete(testiKayttaja);

        //Haetaan käytäjän tiedot tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Kayttaja> haettuKayttaja = kayttajaRepository.findById(testiKayttaja.getId());

        //Tarkistetaan, että käyttäjä on poistettu.
        assertFalse(haettuKayttaja.isPresent());

    }

}
