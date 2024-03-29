package com.romaalie.budjetointiharjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.romaalie.budjetointiharjoitustyo.domain.Aliluokka;
import com.romaalie.budjetointiharjoitustyo.domain.AliluokkaRepository;
import com.romaalie.budjetointiharjoitustyo.domain.KayttajaRepository;
import com.romaalie.budjetointiharjoitustyo.domain.Menoera;
import com.romaalie.budjetointiharjoitustyo.domain.MenoeraRepository;
import com.romaalie.budjetointiharjoitustyo.domain.PaaluokkaRepository;

import jakarta.validation.Valid;

@Controller
public class MenoeraController {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MenoeraController.class);

    @Autowired
    private MenoeraRepository menoeraRepository;

    @Autowired
    private PaaluokkaRepository paaluokkaRepository;

    @Autowired
    private AliluokkaRepository aliluokkaRepository;

    @Autowired
    private KayttajaRepository kayttajaRepository;

    //Yksinkertainen testisivu virheenetsintää varten
    @GetMapping("/testi")
    public String testisivu() {
        return "testi";
    }

    //Pääsivu, jolla listataan kaikki menoerät.
    @RequestMapping("/main")
    public String paasivu(Model model) {
        model.addAttribute("menoerat", menoeraRepository.findAll());
        return "main";
    }

    //Sivu, jolla voi lisätä uuden menoerän lomakkeen avulla.
    @PreAuthorize("hasAuthority('rooli_admin')")
    @RequestMapping("/lisays")
    public String lisaysSivu(Model model) {
        model.addAttribute("menoera", new Menoera());
        model.addAttribute("paaluokat", paaluokkaRepository.findAll());
        model.addAttribute("aliluokat", aliluokkaRepository.findAll());
        model.addAttribute("kayttajat", kayttajaRepository.findAll());
        return "lisays";
    }

    //Aliluokkien dynaamiseen päivittämiseen /lisaa endpointissa.
    @RequestMapping("/getAliluokat/{id}")
    @ResponseBody
    public List<Aliluokka> getAliluokatOfPaaluokka(@PathVariable("id") Long id) {
        List<Aliluokka> aliluokat = aliluokkaRepository.findByPaaluokkaId(id);
        return aliluokat;
    }

    // /lisays sivun lomakkeen lähetys virheen käsittelyllä.
    @PreAuthorize("hasAuthority('rooli_admin')")
    @RequestMapping(value = "/lisaa")
    public String lisaaMenoera(@Valid Menoera menoera, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("paaluokat", paaluokkaRepository.findAll());
            model.addAttribute("aliluokat", aliluokkaRepository.findAll());
            model.addAttribute("kayttajat", kayttajaRepository.findAll());
            return "lisays";
        }
        menoeraRepository.save(menoera);
        return "redirect:/main";
    }

    // /main sivun poista -napin toiminnallisuus.
    @PreAuthorize("hasAuthority('rooli_admin')")
    @RequestMapping("/poista/{id}")
    public String poistaMenoera(@PathVariable("id") Long id) {
        menoeraRepository.deleteById(id);
        return "redirect:../main";
    }

    //Sivu, jolla voi muokata yksittäistä menoerää.
    @PreAuthorize("hasAuthority('rooli_admin')")
    @RequestMapping("/muokkaus/{id}")
    public String muokkausSivu(@PathVariable("id") Long id, Model model) {
        Optional<Menoera> menoeraOptional = menoeraRepository.findById(id);
        if (menoeraOptional.isPresent()) {
            Menoera menoera = menoeraOptional.get();
            logger.info("Received Menoera for editing: {}", menoera);
            model.addAttribute("menoera", menoeraRepository.findById(id));
            model.addAttribute("paaluokat", paaluokkaRepository.findAll());
            model.addAttribute("aliluokat", aliluokkaRepository.findAll());
            model.addAttribute("kayttajat", kayttajaRepository.findAll());
            return "muokkaus";
        } else {
            logger.warn("Menoera with ID {} not found for editing.", id);
            // Handle case where Menoera with given ID is not found
            return "redirect:/main";
        }
    }


    /* 



    //TÄMÄ ON VANHA JA VAIN TESTAUSTA VARTEN
    @RequestMapping("/lisays2")
    public String lisaysSivu2(Model model
    ) {
        model.addAttribute("menoera", new Menoera());
        model.addAttribute("paaluokat", paaluokkaRepository.findAll());
        model.addAttribute("aliluokat", aliluokkaRepository.findAll());
        model.addAttribute("kayttajat", kayttajaRepository.findAll());
        return "lisays2";
    }

    //TÄMÄ ON VANHA JA VAIN TESTAUSTA VARTEN
    @RequestMapping(value = "/lisaa2")
    public String lisaaMenoera2(@Valid Menoera menoera, BindingResult tulos
    ) {
        if (tulos.hasErrors()) {
            return "lisays2";
        }
        menoeraRepository.save(menoera);
        return "redirect:/main";
    }

     */
}
