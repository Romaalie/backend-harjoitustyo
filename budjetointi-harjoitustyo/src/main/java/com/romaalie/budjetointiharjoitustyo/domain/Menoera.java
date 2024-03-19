package com.romaalie.budjetointiharjoitustyo.domain;



import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Menoera {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min (value=1, message="Laita positiivinen hinta")
    private double hinta;

    @NotNull
    private LocalDate AikaLeima;

    private String lisatietoja;

    @ManyToOne()
    @JoinColumn(name="")
    private Kayttaja maksaja;

}
