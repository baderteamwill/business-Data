package com.spring.project.repository;

import com.spring.project.domain.EntityInstance;
import com.spring.project.domain.EntityModel;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the EntityInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityInstanceRepository extends MongoRepository<EntityInstance, String> {

	EntityInstance findByInstanceName (String entityName ) ;

	
	
}
