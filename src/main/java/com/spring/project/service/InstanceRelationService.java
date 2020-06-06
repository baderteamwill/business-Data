package com.spring.project.service;

import com.spring.project.domain.EntityInstance;
import com.spring.project.domain.InstanceRelation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InstanceRelation}.
 */
public interface InstanceRelationService {

    /**
     * Save a instanceRelation.
     *
     * @param instanceRelation the entity to save.
     * @return the persisted entity.
     */
    InstanceRelation save(InstanceRelation instanceRelation);

    /**
     * Get all the instanceRelations.
     *
     * @return the list of entities.
     */
    List<InstanceRelation> findAll();


    /**
     * Get the "id" instanceRelation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstanceRelation> findOne(String id);

    /**
     * Delete the "id" instanceRelation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
   
    
    
    List<EntityInstance> trouverApresInstance(String test);
    
}
