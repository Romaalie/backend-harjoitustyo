package com.romaalie.budjetointiharjoitustyo;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.romaalie.budjetointiharjoitustyo.domain.Aliluokka;
import com.romaalie.budjetointiharjoitustyo.domain.AliluokkaRepository;
import com.romaalie.budjetointiharjoitustyo.domain.Kayttaja;
import com.romaalie.budjetointiharjoitustyo.domain.KayttajaRepository;
import com.romaalie.budjetointiharjoitustyo.domain.Menoera;
import com.romaalie.budjetointiharjoitustyo.domain.MenoeraRepository;
import com.romaalie.budjetointiharjoitustyo.domain.Paaluokka;
import com.romaalie.budjetointiharjoitustyo.domain.PaaluokkaRepository;

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
class MenoeraRepoTests {

    @Autowired
    MenoeraRepository menoeraRepository;

    @Autowired
    PaaluokkaRepository paaluokkaRepository;
    private Paaluokka paaluokka;

    @Autowired
    KayttajaRepository kayttajaRepository;
    private Kayttaja maksaja;
    private Kayttaja maksaja2;

    @Autowired
    AliluokkaRepository aliluokkaRepository;
    private Aliluokka aliluokka;
    private Aliluokka aliluokka2;

    @BeforeEach
    public void setUp() {
        //DANGERZONE, tyhjennetään pääluokka, kayttaja ja menoerä tietokantataulut testejä varten.
        // Ehkä vaihdetaan tämä kun siirrytään ulkoiseen tietokantaan.
        paaluokkaRepository.deleteAll();
        kayttajaRepository.deleteAll();
        menoeraRepository.deleteAll();
        //DANGERZONE

        //Luodaan uusi pääluokka ja tallennetaan se tietokantaan.
        paaluokka = new Paaluokka("Lapset");
        paaluokkaRepository.save(paaluokka);

        //Luodaan kaksi käyttäjää ja tallennetaan ne tietokantaan.
        maksaja = new Kayttaja("testikayttaja", "$2y$10$q2AlKdGpIEmqCGYgGyYdYeuPbChJZ0FOGYw7wlBT1GcdcYPIJQyUi", "kayttaja");
        maksaja2 = new Kayttaja("testikayttaja2", "$2y$10$TTntZIC/l1Bw2gIZ1LDtP.ITwpoPgfUuvql0.brvHnEzjFFDQGVJ2", "kayttaja");
        kayttajaRepository.save(maksaja);
        kayttajaRepository.save(maksaja2);

        //Luodaan kaksi uutta aliluokkaa ja tallennetaan ne tietokantaan.
        aliluokka = new Aliluokka("Lelut", paaluokka);
        aliluokkaRepository.save(aliluokka);
        paaluokka.addAliluokka(aliluokka);
        paaluokkaRepository.save(paaluokka);

        aliluokka2 = new Aliluokka("Vaatteet", paaluokka);
        aliluokkaRepository.save(aliluokka2);
        paaluokka.addAliluokka(aliluokka2);
        paaluokkaRepository.save(paaluokka);

    }

    //Testaa tallennusta tietokantaan ja automaattista ID:n luomista sekä hakua tietokannasta.
    @Test
    public void menoeraLuontiTesti() {

        //Luodaan uusi menoerä ja tallennetaan se tietokantaan.
        Menoera menoera = new Menoera(10.00, LocalDate.parse("2024-03-03"), "Uusi nuija nukutukseen", maksaja, paaluokka, aliluokka);
        menoeraRepository.save(menoera);

        //Haetaan menoerä tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Menoera> haettuMenoera = menoeraRepository.findById(menoera.getId());

        assertTrue(haettuMenoera.isPresent());
        assertEquals("Uusi nuija nukutukseen", haettuMenoera.get().getLisatietoja());
        assertEquals("Lelut", haettuMenoera.get().getAliluokka().getNimi());

    }

    //Testaa tiedon päivittämistä tietokantaan.
    @Test
    public void menoeraMuutosTesti() {

        //Luodaan uusi menoerä ja tallennetaan se tietokantaan.
        Menoera menoera = new Menoera(10.00, LocalDate.parse("2024-03-03"), "Uusi nuija nukutukseen", maksaja, paaluokka, aliluokka);
        menoeraRepository.save(menoera);

        //Muutetaan menoerän nimeä ja tallennetaan muutos tietokantaan.
        menoera.setMaksaja(maksaja2);
        menoeraRepository.save(menoera);

        //Haetaan menoerä tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Menoera> haettuMenoera = menoeraRepository.findById(menoera.getId());
        assertEquals("testikayttaja2", haettuMenoera.get().getMaksaja().getNimi());
        assertNotEquals("testikayttaja", haettuMenoera.get().getMaksaja().getNimi());
    }

    //Testaa aliluokan muuttamista menoerässä ja tietojen päivittymistä kaikissa siihen liittyvissä kentissä.
    @Test
    public void menoeraAliluokkaMuutosTesti() {

        //Luodaan uusi menoerä ja tallennetaan se tietokantaan.
        Menoera menoera = new Menoera(10.00, LocalDate.parse("2024-03-03"), "Uusi nuija nukutukseen", maksaja, paaluokka, aliluokka);
        menoeraRepository.save(menoera);

        //Muutetaan menoerän aliluokkaa.
        menoera.setAliluokka(aliluokka2);
        menoeraRepository.save(menoera);

        //Haetaan menoerä tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Menoera> haettuMenoera = menoeraRepository.findById(menoera.getId());

        //Testataan että myös aliluokkaNimi kenttä on päivittynyt.
        assertEquals("Vaatteet", haettuMenoera.get().getAliluokkaNimi());
        assertNotEquals("Lelut", haettuMenoera.get().getAliluokkaNimi());

    }

    //Testaa tiedon poistamista tietokannasta.
    @Test
    public void menoeraPoistoTesti() {

        //Luodaan uusi menoerä ja tallennetaan se tietokantaan.
        Menoera menoera = new Menoera(10.00, LocalDate.parse("2024-03-03"), "Uusi nuija nukutukseen", maksaja, paaluokka, aliluokka);
        menoeraRepository.save(menoera);

        //Poistetaan menoerä tietokannasta.
        menoeraRepository.deleteById(menoera.getId());

        //Haetaan menoerä tietokannasta. Näin varmistetaan, ettei käytetä välimuistin tietoja.
        Optional<Menoera> haettuMenoera = menoeraRepository.findById(menoera.getId());

        //Tarkistetaan, että menoerä on poistettu.
        assertFalse(haettuMenoera.isPresent());
    }

}
