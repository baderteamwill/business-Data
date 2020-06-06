package com.spring.project.service;

import com.spring.project.domain.EntityRelation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EntityRelation}.
 */
public interface EntityRelationService {

    /**
     * Save a entityRelation.
     *
     * @param entityRelation the entity to save.
     * @return the persisted entity.
     */
    EntityRelation save(EntityRelation entityRelation);

    /**
     * Get all the entityRelations.
     *
     * @return the list of entities.
     */
    List<EntityRelation> findAll();


    /**
     * Get the "id" entityRelation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntityRelation> findOne(String id);

    /**
     * Delete the "id" entityRelation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
    
    
    
    
    boolean checkrelations(EntityRelation entityRelation);
    
    
    
    
    
    List<EntityRelation> listOfSameSataModel(String name);
    
    
    
    
    
    
}
