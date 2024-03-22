package com.romaalie.budjetointiharjoitustyo.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Menoera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private double hinta;

    @NotNull
    private LocalDate aikaLeima;

    private String lisatietoja;

    @ManyToOne()
    @JoinColumn(name = "maksajaid")
    @NotNull
    @JsonIgnore
    private Kayttaja maksaja;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "paaluokkaid")
    @JsonIgnore
    private Paaluokka paaluokka;

    @Transient
    @NotNull
    private Aliluokka aliluokka;

    private String aliluokkaNimi;

    private Long aliluokkaId;

    public Menoera() {
    }

    public Menoera(@NotNull @Positive double hinta, @NotNull LocalDate aikaLeima, String lisatietoja,
            @NotNull Kayttaja maksaja, @NotNull Paaluokka paaluokka, @NotNull Aliluokka aliluokka) {
        this.hinta = hinta;
        this.aikaLeima = aikaLeima;
        this.lisatietoja = lisatietoja;
        this.maksaja = maksaja;
        this.paaluokka = paaluokka;
        this.aliluokka = aliluokka;
        this.aliluokkaNimi = aliluokka.getNimi();
        this.aliluokkaId = aliluokka.getId();
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
        return aikaLeima;
    }

    public void setAikaLeima(LocalDate aikaLeima) {
        this.aikaLeima = aikaLeima;
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

    public Aliluokka getAliluokka() {
        return aliluokka;
    }

    public void setAliluokka(Aliluokka aliluokka) {
        this.aliluokka = aliluokka;
        setAliluokkaNimi(aliluokka);
        setAliluokkaId(aliluokka);
    }

    public String getAliluokkaNimi() {
        return aliluokkaNimi;
    }

    public void setAliluokkaNimi(Aliluokka aliluokka) {
        this.aliluokkaNimi = aliluokka.getNimi();
    }

    public Long getAliluokkaId() {
        return aliluokkaId;
    }

    public void setAliluokkaId(Aliluokka aliluokka) {
        this.aliluokkaId = aliluokka.getId();
    }

    @Override
    public String toString() {
        return "Menoera [id=" + id + ", hinta=" + hinta + ", aikaLeima=" + aikaLeima + ", lisatietoja=" + lisatietoja
                + ", maksaja=" + maksaja + ", paaluokka=" + paaluokka + ", aliluokka=" + aliluokka + "]";
    }

}
