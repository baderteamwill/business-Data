package com.spring.project.repository;

import com.spring.project.domain.InstanceRelation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the InstanceRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstanceRelationRepository extends MongoRepository<InstanceRelation, String> {

}
