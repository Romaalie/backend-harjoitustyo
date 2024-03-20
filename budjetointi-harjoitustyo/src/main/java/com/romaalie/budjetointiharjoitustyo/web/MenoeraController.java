package com.romaalie.budjetointiharjoitustyo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.romaalie.budjetointiharjoitustyo.domain.MenoeraRepository;

@Controller
public class MenoeraController {

    @Autowired
    private MenoeraRepository menoeraRepository;

    @GetMapping("/main")
    public String paasivu(Model model) {
        model.addAttribute("menoerat", menoeraRepository.findAll());
        return "main";
    }

    @GetMapping("/testi")
    public String testisivu() {
        return "testi";
    }

}
