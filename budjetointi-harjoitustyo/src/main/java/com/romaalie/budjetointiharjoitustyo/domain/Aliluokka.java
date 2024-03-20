package com.romaalie.budjetointiharjoitustyo.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Aliluokka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nimi;

    @ManyToOne()
    @JoinColumn(name = "paaluokkaid")
    @NotNull
    private Paaluokka paaluokka;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aliluokka")
    @Valid
    private List<Menoera> menoerat;

    public Aliluokka() {
    }

    public Aliluokka(@NotBlank String nimi, @NotNull Paaluokka paaluokka) {
        super();
        this.nimi = nimi;
        this.paaluokka = paaluokka;
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

    public Paaluokka getPaaluokka() {
        return paaluokka;
    }

    public void setPaaluokka(Paaluokka paaluokka) {
        this.paaluokka = paaluokka;
    }

    public List<Menoera> getMenoerat() {
        return menoerat;
    }

    public void setMenoerat(List<Menoera> menoerat) {
        this.menoerat = menoerat;
    }

    @Override
    public String toString() {
        return "Aliluokka [id=" + id + ", nimi=" + nimi + ", paaluokka=" + paaluokka + "]";
    }

}
