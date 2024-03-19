package com.romaalie.budjetointiharjoitustyo.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Menoera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1, message = "Laita positiivinen hinta")
    private double hinta;

    @NotNull
    private LocalDate AikaLeima;

    private String lisatietoja;

    @ManyToOne()
    @JoinColumn(name = "maksajaid")
    private Kayttaja maksaja;

    @ManyToOne
    @JoinColumn(name = "paaluokkaid")
    private Paaluokka paaluokka;

    public Menoera() {
    }

    public Menoera(@NotNull @Min(value = 1, message = "Laita positiivinen hinta") double hinta,
            @NotNull LocalDate aikaLeima, String lisatietoja, Kayttaja maksaja, Paaluokka paaluokka) {
        this.hinta = hinta;
        AikaLeima = aikaLeima;
        this.lisatietoja = lisatietoja;
        this.maksaja = maksaja;
        this.paaluokka = paaluokka;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public LocalDate getAikaLeima() {
        return AikaLeima;
    }

    public void setAikaLeima(LocalDate aikaLeima) {
        AikaLeima = aikaLeima;
    }

    public String getLisatietoja() {
        return lisatietoja;
    }

    public void setLisatietoja(String lisatietoja) {
        this.lisatietoja = lisatietoja;
    }

    public Kayttaja getMaksaja() {
        return maksaja;
    }

    public void setMaksaja(Kayttaja maksaja) {
        this.maksaja = maksaja;
    }

    public Paaluokka getPaaluokka() {
        return paaluokka;
    }

    public void setPaaluokka(Paaluokka paaluokka) {
        this.paaluokka = paaluokka;
    }

    @Override
    public String toString() {
        return "Menoera [id=" + id + ", hinta=" + hinta + ", AikaLeima=" + AikaLeima + ", lisatietoja=" + lisatietoja
                + ", maksaja=" + maksaja + ", paaluokka=" + paaluokka + "]";
    }

}
