package com.romaalie.budjetointiharjoitustyo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Paaluokka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nimi;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paaluokka")
    @Valid
    @JsonIgnore
    //@JsonManagedReference
    private List<Menoera> menoerat;


    @OneToMany(cascade = CascadeType.ALL, mappedBy= "paaluokka")
    @Valid
    @JsonIgnore
    //@JsonManagedReference
    private List<Aliluokka> aliluokat;

    public Paaluokka() {
    }

    public Paaluokka(@NotBlank String nimi) {
        this.nimi = nimi;
    }

    public Paaluokka(@NotBlank String nimi, @Valid List<Menoera> menoerat, @Valid List<Aliluokka> aliluokat) {
        this.nimi = nimi;
        this.menoerat = menoerat;
        this.aliluokat = aliluokat;
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

    public List<Menoera> getMenoerat() {
        return menoerat;
    }

    public void setMenoerat(List<Menoera> menoerat) {
        this.menoerat = menoerat;
    }

    public List<Aliluokka> getAliluokat() {
        return aliluokat;
    }

    public void setAliluokat(List<Aliluokka> aliluokat) {
        this.aliluokat = aliluokat;
    }

    public void addAliluokka(Aliluokka aliluokka) {
        if (aliluokat == null) {
            aliluokat = new ArrayList<>();
        }
        aliluokat.add(aliluokka);
        aliluokka.setPaaluokka(this);
    }

    @Override
    public String toString() {
        return "Paaluokka [id=" + id + ", nimi=" + nimi + "]";
    }

}
