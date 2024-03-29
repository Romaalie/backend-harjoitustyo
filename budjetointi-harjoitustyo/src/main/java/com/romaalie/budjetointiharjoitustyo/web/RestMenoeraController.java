package com.romaalie.budjetointiharjoitustyo.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.romaalie.budjetointiharjoitustyo.domain.Menoera;
import com.romaalie.budjetointiharjoitustyo.domain.MenoeraRepository;

import jakarta.validation.Valid;

@RestController
public class RestMenoeraController {

    @Autowired
    MenoeraRepository menoeraRepository;

    @GetMapping("/menoerat")
    public ResponseEntity<?> getMenoerat() {
        return ResponseEntity.ok().body(menoeraRepository.findAll());
    }

    @GetMapping("/menoera/{id}")
    public ResponseEntity<Object> getMenoera(@PathVariable("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (!menoeraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(menoeraRepository.findById(id));

    }

    @PreAuthorize("hasAuthority('rooli_admin')")
    @DeleteMapping("/menoera/{id}")
    public ResponseEntity<Object> deleteMenoera(@PathVariable("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (!menoeraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        menoeraRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('rooli_admin')")
    @PostMapping("/menoera")
    public ResponseEntity<?> postMenoera(@Valid @RequestBody Menoera menoera) {
        if (menoera == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Menoera tallennettuMenoera = menoeraRepository.save(menoera);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tallennettuMenoera.getId())
                .toUri();

        return ResponseEntity.created(location).body(tallennettuMenoera);
    }

    @PreAuthorize("hasAuthority('rooli_admin')")
    @PutMapping("menoera/{id}")
    public ResponseEntity<?> putMenoera(@PathVariable Long id, @Valid @RequestBody Menoera muokattuMenoera) {
        if (!menoeraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        muokattuMenoera.setId(id);
        menoeraRepository.save(muokattuMenoera);
        return ResponseEntity.ok().body(muokattuMenoera);
    }

}
