package com.spring.project.service;

import com.spring.project.domain.DatabaseModel;
import com.spring.project.domain.EntityModel;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EntityModel}.
 */
public interface EntityModelService {

	/**
	 * Save a entityModel.
	 *
	 * @param entityModel the entity to save.
	 * @return the persisted entity.
	 */
	EntityModel save(EntityModel entityModel);

	/**
	 * Get all the entityModels.
	 *
	 * @return the list of entities.
	 */
	List<EntityModel> findAll();
	/**
	 * Get all the EntityModelDTO where EntityRelation is {@code null}.
	 *
	 * @return the list of entities.
	 */
	List<EntityModel> findAllWhereEntityRelationIsNull();
	/**
	 * Get all the EntityModelDTO where EntityRelation2 is {@code null}.
	 *
	 * @return the list of entities.
	 */
	List<EntityModel> findAllWhereEntityRelation2IsNull();


	/**
	 * Get the "id" entityModel.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<EntityModel> findOne(String id);

	/**
	 * Delete the "id" entityModel.
	 *
	 * @param id the id of the entity.
	 */
	void delete(String id);


	List<EntityModel> trouver(String d) ; 
	
	
	
	boolean checkEntity(EntityModel entityModel);
	
	
	
	List<EntityModel> resteEntities(String name);
	
}
