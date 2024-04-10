package com.romaalie.budjetointiharjoitustyo.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity (name = "kayttaja")
public class Kayttaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nimi;

    @NotBlank
    private String salasanaHash;

    @NotBlank
    private String kayttajaRooli;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maksaja")
    @Valid
    private List<Menoera> menoerat;

    public Kayttaja() {
    }

    public Kayttaja(@NotBlank String nimi, @NotBlank String salasanaHash, @NotBlank String kayttajaRooli) {
        this.nimi = nimi;
        this.salasanaHash = salasanaHash;
        this.kayttajaRooli = kayttajaRooli;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getSalasanaHash() {
        return salasanaHash;
    }

    public void setSalasanaHash(String salasanaHash) {
        this.salasanaHash = salasanaHash;
    }

    public String getKayttajaRooli() {
        return kayttajaRooli;
    }

    public void setKayttajaRooli(String kayttajaRooli) {
        this.kayttajaRooli = kayttajaRooli;
    }

    public List<Menoera> getMenoerat() {
        return menoerat;
    }

    public void setMenoerat(List<Menoera> menoerat) {
        this.menoerat = menoerat;
    }

    @Override
    public String toString() {
        return "Kayttaja [id=" + id + ", nimi=" + nimi + ", salasanaHash=" + salasanaHash + ", kayttajaRooli="
                + kayttajaRooli + "]";
    }
}
