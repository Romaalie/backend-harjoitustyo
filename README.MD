# Budjetointi harjoitustyö

Tämä repositorio on Ali Romar:n harjoitustyö Haaga-Helian kurssille K24 Back end -ohjelmointi - SOF003AS3A-3004.

Harjoitustyön tarkoituksena on luoda pienimuotoinen web sovellus hyödyntäen SpringBoot frameworkia sekä Thymeleafia yksinkertaisen käyttöliittymän luomiseen.

Harjoitustyössä hyödynnetään git versionhallintaa: kehitystyö tapahtuu dev haarassa ja toimivat osakokonaisuudet päivitetään main haaraan. Harjoitustyötä työstetään paikallisesti, mutta sitä päivitetään aktiivisesti GitHubiin, josta tulee myös löytymään lopullinen versio. 

## Sovelluksen yksityiskohdat

Sovelluksen tarkoituksena on mahdollistaa kuluanalyysiä ja tulevaa budjetointia yksittäisen kotitalouden kuluista.

### Tietokanta

Sovelluksen käyttöön on suunniteltu alustavasti alla olevan kaavion mukainen tietokanta.

![Tietokantakaavio](/budjetointi-harjoitustyo/dokumentit/tietokantakaavio.JPG)

Kehitystyön alkuvaiheessa tullaan käyttämään h2 ajonaikaista tietokantaa, mutta myöhemmin siirrytään käyttämään PostgreSQL paikallisesti ajettavaa tietokantaa.

### Käyttöliittymä ja käyttäjät

Käyttöliittymän toteutuksessa hyödynnetään Thymeleafia ja MVC arkitehtuuria.

Käyttöliittymästä ei ole piirretty malleja, mutta tarkoituksena on toteuttaa yksinkertainen käyttöliittymä, jonka kautta perus käyttäjä pystyy tarkastelemaan tietokannan tapahtumia sekä järjestelemään niitä näkymään esimerkiksi pääluokkien, aliluokkien tai hinnan mukaan.

Alustavana tarkoituksena on tehdä kotitalouden aikuisille käyttäjät, joilla voi lisätä uusia tapahtumia tietokantaan sekä Admin käyttäjä, joka voi sieltä niitä poistaa. Jos selviää, että ei ole liian työlästä niin tarkoituksena olisi mahdollista peruskäyttäjille lisääminen vain omalla nimellään sekä mahdollisuus poistaa/muokata omalla nimellään olevia tapahtumia.

### Toteutuksesta ja arviointikriteereihin liittyvää

Sovellusta on tarkoitus lähteä kehittämään toiminnallisuus kerrallaan, sisällyttäen validoinnit sekä Junit yksikkötestauksen tähän prosessiin. Viimeisimpiä töitä tässä vaiheessa tulee olemaan REST rajapinnan toteuttaminen rajatulle määrälle toiminnallisuuksia.

Kun sovelluksen runko on hyvin kasassa ja toimiva, lisätään käyttäjät mukaan ja näiden toimivuuden jälkeen sovellus asennetaan ulkoiselle palvelimelle (render.com) h2 tietokantaa ja demodataa hyödyntäen.

Tämän jälkeen on tarkoitus siirtää sovellus toimimaan ulkoisella tietokannalla (PostgreSQL).