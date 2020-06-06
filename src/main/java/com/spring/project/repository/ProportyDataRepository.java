package com.spring.project.repository;

import com.spring.project.domain.ProportyData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ProportyData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProportyDataRepository extends MongoRepository<ProportyData, String> {

}
