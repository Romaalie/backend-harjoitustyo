package com.romaalie.budjetointiharjoitustyo.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KayttajaRepository extends CrudRepository<Kayttaja, Long>{

    Optional<Kayttaja> findById(Long id);

}
