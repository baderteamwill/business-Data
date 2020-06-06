package com.spring.project.service.impl;

import com.spring.project.service.DatabaseModelService;
import com.spring.project.domain.DatabaseModel;
import com.spring.project.repository.DatabaseModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DatabaseModel}.
 */
@Service
public class DatabaseModelServiceImpl implements DatabaseModelService {

	private final Logger log = LoggerFactory.getLogger(DatabaseModelServiceImpl.class);

	private final DatabaseModelRepository databaseModelRepository;

	public DatabaseModelServiceImpl(DatabaseModelRepository databaseModelRepository) {
		this.databaseModelRepository = databaseModelRepository;
	}

	/**
	 * Save a databaseModel.
	 *
	 * @param databaseModel the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public DatabaseModel save(DatabaseModel databaseModel) {
		log.debug("Request to save DatabaseModel : {}", databaseModel);
		if ((isString(databaseModel.getDatabaseModel())==true)&&(check(databaseModel)==true)) {
		return databaseModelRepository.save(databaseModel);
	}
		return databaseModel;
		
	}
		

	/**
	 * Get all the databaseModels.
	 *
	 * @return the list of entities.
	 */
	@Override
	public List<DatabaseModel> findAll() {
		log.debug("Request to get all DatabaseModels");
		return databaseModelRepository.findAll();
	}


	/**
	 * Get one databaseModel by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	public Optional<DatabaseModel> findOne(String id) {
		log.debug("Request to get DatabaseModel : {}", id);
		return databaseModelRepository.findById(id);
	}

	/**
	 * Delete the databaseModel by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete DatabaseModel : {}", id);
		databaseModelRepository.deleteById(id);
	}


	public boolean isString (String text) {
		if (text.matches("^[a-zA-Z]+$")) {
			return true;
		} 	
		return false  ; 
	}

	@Override
	public boolean check(DatabaseModel databaseModel) {
	boolean test=true ;
		for (int i=0;i<findAll().size();i++)
		{
			if(databaseModel.getDatabaseModel().equalsIgnoreCase(findAll().get(i).getDatabaseModel()))
			{
				test=false ;
			}
			
			
			
			
		}
		
		return test;
	}

	
	
	
	
}
