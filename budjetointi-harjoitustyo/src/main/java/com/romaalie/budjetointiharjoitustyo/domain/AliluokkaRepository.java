package com.romaalie.budjetointiharjoitustyo.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AliluokkaRepository extends CrudRepository<Aliluokka, Long>{

    @Override
    Optional<Aliluokka> findById(Long id);
    
    List<Aliluokka> findByPaaluokkaId(Long id);

}
