package com.spring.project.service;

import com.spring.project.domain.DatabaseModel;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DatabaseModel}.
 */
public interface DatabaseModelService {

    /**
     * Save a databaseModel.
     *
     * @param databaseModel the entity to save.
     * @return the persisted entity.
     */
    DatabaseModel save(DatabaseModel databaseModel);

    /**
     * Get all the databaseModels.
     *
     * @return the list of entities.
     */
    List<DatabaseModel> findAll();


    /**
     * Get the "id" databaseModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DatabaseModel> findOne(String id);

    /**
     * Delete the "id" databaseModel.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
    
    
    
    boolean check(DatabaseModel databaseModel);
    
    
    
    
}
