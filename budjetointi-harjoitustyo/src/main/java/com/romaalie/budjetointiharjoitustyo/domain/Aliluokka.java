package com.romaalie.budjetointiharjoitustyo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Aliluokka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nimi;

    @ManyToOne()
    @JoinColumn(name = "paaluokkaid")
    private Paaluokka paaluokka;

    public Aliluokka() {
    }

    public Aliluokka(@NotBlank String nimi, Paaluokka paaluokka) {
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

    @Override
    public String toString() {
        return "Aliluokka [id=" + id + ", nimi=" + nimi + ", paaluokka=" + paaluokka + "]";
    }

}
