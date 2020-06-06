package com.spring.project.repository;

import com.spring.project.domain.EntityRelation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the EntityRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityRelationRepository extends MongoRepository<EntityRelation, String> {

}
