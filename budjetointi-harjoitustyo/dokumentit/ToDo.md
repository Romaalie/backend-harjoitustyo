# To do lista

## Olennaisia arvostelun kannalta ?

- Soveltava toiminnallisuus?

- Rest testaus postmanilla/sovelluksen sisäisillä testeillä.
    - POST ei toimi. Bodyn muoto pitäisi onnistua määrittämään, mutta virheenetsintä on hankalaa.

- Menoerän lisätiedot kenttää ei hyödynnetä missään (ei edes mahdollisuutta syöttää lisää tai muokkaa sivuilla).
    - Ihan näppärä tosin Rest testauksessa erottamaan helposti entryt toisistaan pysyvässä tietokannassa.

- Lisää uusi menoerän (/lisays) datepickeriin voi asettaa arvoja, jotka jumittaa lomakkeen maksaja kentästä johtuen. Esim. 657-01-17
    - Tämän voisi korjata vaihtamalla muokkaus lomakkeen datepickeriin.

- /getAliluokat/{id} auktorisointi?
    - Tässä pitäisi testata vaikuttaako auktorisointi skriptin toimivuuteen.

## Onko olennaisia arvostelun kannalta ?

- Error handling kun käyttäjä navigoi selaimella olemattomaan paikkaan?
    - Työläs?

- Virheviesti väärälle käyttäjätunnukselle, nykyinen on ruma
    - Tarvitaanko tähän custom login?

- Vanhojen testimetodien ym. siivoaminen koodista pois.