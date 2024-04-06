package com.romaalie.budjetointiharjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public String paasivu(Model model, Authentication authentication) {
        model.addAttribute("menoerat", menoeraRepository.findAll());

        if (authentication != null && authentication.isAuthenticated()) {
            // Get the logged-in user's data
            String loggedInUserName = authentication.getName();
            model.addAttribute("loggedInUserName", loggedInUserName);
            Object loggedInUser = authentication.getPrincipal();
            model.addAttribute("loggedInUser", loggedInUser);
            Long loggedInUserId = kayttajaRepository.findByNimi(loggedInUserName).getId();
            model.addAttribute("loggedInUserId", loggedInUserId);
        }

        return "main";
    }

    //Sivu, jolla voi lisätä uuden menoerän lomakkeen avulla.
    @PreAuthorize("hasAuthority('ROLE_admin') || hasAuthority('ROLE_kayttaja')")
    @RequestMapping("/lisays")
    public String lisaysSivu(Model model, Authentication authentication) {
        model.addAttribute("menoera", new Menoera());
        model.addAttribute("paaluokat", paaluokkaRepository.findAll());
        model.addAttribute("aliluokat", aliluokkaRepository.findAll());
        model.addAttribute("kayttajat", kayttajaRepository.findAll());
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the logged-in user's data
            String loggedInUserName = authentication.getName();
            model.addAttribute("loggedInUserName", loggedInUserName);
            Object loggedInUser = authentication.getPrincipal();
            model.addAttribute("loggedInUser", loggedInUser);
            Long loggedInUserId = kayttajaRepository.findByNimi(loggedInUserName).getId();
            model.addAttribute("loggedInUserId", loggedInUserId);
        }
        return "lisays";
    }

    //Aliluokkien dynaamiseen päivittämiseen /lisaa endpointissa.
    @RequestMapping("/getAliluokat/{id}")
    @ResponseBody
    public List<Aliluokka> getAliluokatOfPaaluokka(@PathVariable("id") Long id) {
        List<Aliluokka> aliluokat = aliluokkaRepository.findByPaaluokkaId(id);
        return aliluokat;
    }

    // /lisays sivun lomakkeen lähetys (virheen käsittelyllä).
    @PreAuthorize("hasAuthority('ROLE_kayttaja') || hasAuthority('ROLE_admin')")
    @RequestMapping(value = "/lisaa")
    public String lisaaMenoera(@Valid Menoera menoera, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("paaluokat", paaluokkaRepository.findAll());
            model.addAttribute("aliluokat", aliluokkaRepository.findAll());
            model.addAttribute("kayttajat", kayttajaRepository.findAll());

            //model.addAttribute("errors", result.getAllErrors());
            return "lisays";
        }
        menoeraRepository.save(menoera);
        System.out.println("Menoera saved: " + menoera.toString());
        return "redirect:/main";
    }

    // /main sivun poista -napin toiminnallisuus.
    @PreAuthorize("hasRole('ROLE_admin') or (#menoeraMaksajaId == #loggedInUserId)")
    @RequestMapping("/poista/{id}")
    public String poistaMenoera(@PathVariable("id") Long id, Authentication authentication) {
        String loggedInUserName = authentication.getName();
        Long loggedInUserId = kayttajaRepository.findByNimi(loggedInUserName).getId();

        Optional<Menoera> menoeraOptional = menoeraRepository.findById(id);
        if (menoeraOptional.isPresent()) {
            Menoera menoera = menoeraOptional.get();
            Long menoeraMaksajaId = menoera.getMaksaja().getId();

            // virheenetsintää
            System.out.println("loggedInUserId: " + loggedInUserId);
            System.out.println("menoeraId: " + menoeraMaksajaId);

            if (menoeraMaksajaId.equals(loggedInUserId) || authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"))) {
                menoeraRepository.deleteById(id);
                return "redirect:../main";
            } else {
                return "redirect:/main";
            }
        } else {
            return "redirect:/main";
        }
    }

    //Sivu, jolla voi muokata yksittäistä menoerää.
    @PreAuthorize("hasRole('ROLE_admin') or (#menoeraMaksajaId == #loggedInUserId)")
    @RequestMapping("/muokkaus/{id}")
    public String muokkausSivu(@PathVariable("id") Long id, Model model, Authentication authentication) {
        String loggedInUserName = authentication.getName();
        Long loggedInUserId = kayttajaRepository.findByNimi(loggedInUserName).getId();

        Optional<Menoera> menoeraOptional = menoeraRepository.findById(id);

        if (menoeraOptional.isPresent()) {
            Menoera menoera = menoeraOptional.get();
            Long menoeraMaksajaId = menoera.getMaksaja().getId();

            // virheenetsintää
            logger.info("Received Menoera for editing: {}", menoera);
            if (menoeraMaksajaId.equals(loggedInUserId) || authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"))) {
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
        return "redirect:/main";
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
