package com.spring.project.repository;

import com.spring.project.domain.DatabaseModel;
import com.spring.project.domain.EntityModel;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the EntityModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityModelRepository extends MongoRepository<EntityModel, String> {
	EntityModel findByEntityName (String entityName ) ;
}
