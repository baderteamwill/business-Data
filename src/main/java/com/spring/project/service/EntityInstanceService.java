package com.spring.project.service;

import com.spring.project.domain.EntityInstance;
import com.spring.project.domain.EntityRelation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EntityInstance}.
 */
public interface EntityInstanceService {

    /**
     * Save a entityInstance.
     *
     * @param entityInstance the entity to save.
     * @return the persisted entity.
     */
    EntityInstance save(EntityInstance entityInstance);

    /**
     * Get all the entityInstances.
     *
     * @return the list of entities.
     */
    List<EntityInstance> findAll();
    /**
     * Get all the EntityInstanceDTO where InstanceRelation is {@code null}.
     *
     * @return the list of entities.
     */
    List<EntityInstance> findAllWhereInstanceRelationIsNull();
    /**
     * Get all the EntityInstanceDTO where InstanceRelation2 is {@code null}.
     *
     * @return the list of entities.
     */
    List<EntityInstance> findAllWhereInstanceRelation2IsNull();


    /**
     * Get the "id" entityInstance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntityInstance> findOne(String id);

    /**
     * Delete the "id" entityInstance.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
    
    
    boolean checkInstance(EntityInstance entityInstance);
    
    
    
    List<EntityInstance> trouverInstance(String test) ;    
    
    String searchName (String Name);
 
    List<EntityInstance> findbyname(String test);
    
    List<EntityInstance>  findSol(EntityRelation entityRelation);
    
    
    List<EntityInstance> findRelations (String relation);  
    
    
    List<EntityInstance> Relations (String relation);    

    
    
    
    
}
