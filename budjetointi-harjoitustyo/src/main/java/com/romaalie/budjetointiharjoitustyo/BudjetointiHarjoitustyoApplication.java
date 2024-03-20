package com.romaalie.budjetointiharjoitustyo;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.romaalie.budjetointiharjoitustyo.domain.Aliluokka;
import com.romaalie.budjetointiharjoitustyo.domain.AliluokkaRepository;
import com.romaalie.budjetointiharjoitustyo.domain.Kayttaja;
import com.romaalie.budjetointiharjoitustyo.domain.KayttajaRepository;
import com.romaalie.budjetointiharjoitustyo.domain.Menoera;
import com.romaalie.budjetointiharjoitustyo.domain.MenoeraRepository;
import com.romaalie.budjetointiharjoitustyo.domain.Paaluokka;
import com.romaalie.budjetointiharjoitustyo.domain.PaaluokkaRepository;

@SpringBootApplication
public class BudjetointiHarjoitustyoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudjetointiHarjoitustyoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(MenoeraRepository menoeraRepository, KayttajaRepository kayttajaRepository, PaaluokkaRepository paaluokkaRepository, AliluokkaRepository aliluokkaRepository) {
        return (args) -> {

            // Create test users
            Kayttaja tanja = new Kayttaja("Tanja", "$2y$10$R3n540TfIBqXtgjZC8ksKeWSyZl0YhzPXPLTctnD1.B8NriqBhO.2", "kayttaja");
            Kayttaja tero = new Kayttaja("Tero", "$2y$10$GDTmEuglS.PUfitb5Q5IzeaQOf.7zcqVRQpqzmvW21J9DQnbwZlQ2", "kayttaja");

            // Save users to the database
            kayttajaRepository.save(tanja);
            kayttajaRepository.save(tero);

            // Create main classes
            Paaluokka ruoka = new Paaluokka("Ruoka");
            Paaluokka lapset = new Paaluokka("Lapset");
            Paaluokka pakolliset = new Paaluokka("Pakolliset");

            // Save main classes to the database
            paaluokkaRepository.save(ruoka);
            paaluokkaRepository.save(lapset);
            paaluokkaRepository.save(pakolliset);

            // Create sub classes
            Aliluokka arki = new Aliluokka("Arki", ruoka);
            Aliluokka herkut = new Aliluokka("Herkut", ruoka);
            Aliluokka ravintola = new Aliluokka("Ravintola", ruoka);

            Aliluokka vaatteet = new Aliluokka("Vaatteet", lapset);
            Aliluokka lelut = new Aliluokka("Lelut", lapset);
            Aliluokka harrastukset = new Aliluokka("Harrastukset", lapset);

            Aliluokka sahko = new Aliluokka("Sähkö", pakolliset);
            Aliluokka vuokra = new Aliluokka("Vuokra", pakolliset);
            Aliluokka vesi = new Aliluokka("Vesi", pakolliset);

            // Save sub classes to the database
            aliluokkaRepository.save(arki);
            aliluokkaRepository.save(herkut);
            aliluokkaRepository.save(ravintola);

            aliluokkaRepository.save(vaatteet);
            aliluokkaRepository.save(lelut);
            aliluokkaRepository.save(harrastukset);

            aliluokkaRepository.save(sahko);
            aliluokkaRepository.save(vuokra);
            aliluokkaRepository.save(vesi);

            // Create some Menoera demos
            Menoera menoera1 = new Menoera(25.50, LocalDate.now(), "Ruokaostokset", tanja, ruoka, arki);
            Menoera menoera2 = new Menoera(15.75, LocalDate.now().minusDays(2), "Lounas ulkona", tanja, ruoka, ravintola);
            Menoera menoera3 = new Menoera(45.30, LocalDate.now().minusDays(1), "Uudet lelut lapsille", tero, lapset, lelut);

            // Save Menoera demos to the database
            menoeraRepository.save(menoera1);
            menoeraRepository.save(menoera2);
            menoeraRepository.save(menoera3);

            // Print confirmation
            System.out.println("Test data generated successfully!");

        };
    }
}
