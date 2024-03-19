package com.romaalie.budjetointiharjoitustyo.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AliluokkaRepository extends CrudRepository<Aliluokka, Long>{

    Optional<Aliluokka> findById(Long id);

}
