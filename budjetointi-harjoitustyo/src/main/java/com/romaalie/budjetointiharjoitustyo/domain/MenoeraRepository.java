package com.romaalie.budjetointiharjoitustyo.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenoeraRepository extends CrudRepository<Menoera, Long>{

}
