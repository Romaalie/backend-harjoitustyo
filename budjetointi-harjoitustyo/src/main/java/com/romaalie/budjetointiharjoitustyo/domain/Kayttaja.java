package com.romaalie.budjetointiharjoitustyo.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

public class Kayttaja {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nimi;

    @NotBlank
    private String salasanaHash;

    @NotBlank
    private String kayttajaRooli;

}
