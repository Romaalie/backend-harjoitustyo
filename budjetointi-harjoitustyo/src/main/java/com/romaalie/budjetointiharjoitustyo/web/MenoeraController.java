package com.romaalie.budjetointiharjoitustyo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
public class MenoeraController {

    @Autowired
    private MenoeraRepository menoeraRepository;

    @Autowired
    private PaaluokkaRepository paaluokkaRepository;

    @Autowired
    private AliluokkaRepository aliluokkaRepository;

    @Autowired
    private KayttajaRepository kayttajaRepository;

    @GetMapping("/testi")
    public String testisivu() {
        return "testi";
    }

    @RequestMapping("/main")
    public String paasivu(Model model) {
        model.addAttribute("menoerat", menoeraRepository.findAll());
        return "main";
    }

    @RequestMapping("/muokkaus/{id}")
    public String muokkausSivu(@PathVariable("id") Long id, Model model) {
        model.addAttribute("menoerat", menoeraRepository.findAll());
        return "muokkaus";
    }

    //Tätä voi joutua vielä muokkaamaan, pitää tutkia miten aliluokat toimivat.
    @RequestMapping("/lisays")
    public String lisaysSivu(Model model) {
        model.addAttribute("menoera", new Menoera());
        model.addAttribute("paaluokat", paaluokkaRepository.findAll());
        model.addAttribute("aliluokat", aliluokkaRepository.findAll());
        model.addAttribute("kayttajat", kayttajaRepository.findAll());
        return "lisays";
    }

    @RequestMapping(value = "/lisaa")
    public String lisaaMenoera(Menoera menoera) {
        menoeraRepository.save(menoera);
        return "redirect:main";
    }

    @RequestMapping("/getAliluokat/{id}")
    @ResponseBody
    public List<Aliluokka> getAliluokatOfPaaluokka(@PathVariable("id") Long id) {
        List<Aliluokka> aliluokat = aliluokkaRepository.findByPaaluokkaId(id);
        return aliluokat;
    }

    @RequestMapping("/poista/{id}")
    public String poistaMenoera(@PathVariable("id") Long id) {
        menoeraRepository.deleteById(id);
        return "redirect:../main";
    }

}
