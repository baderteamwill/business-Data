package com.spring.project.service;

import com.spring.project.domain.ProportyData;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProportyData}.
 */
public interface ProportyDataService {

    /**
     * Save a proportyData.
     *
     * @param proportyData the entity to save.
     * @return the persisted entity.
     */
    ProportyData save(ProportyData proportyData);

    /**
     * Get all the proportyData.
     *
     * @return the list of entities.
     */
    List<ProportyData> findAll();


    /**
     * Get the "id" proportyData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProportyData> findOne(String id);

    /**
     * Delete the "id" proportyData.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
    
    
    
    List<ProportyData> trouverproporties(String test);
    
    
    
    List<ProportyData> proportiesDetails(String instanceName);
    
    
    
    
    
    
    
    
    
    
    
}
