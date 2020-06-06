package com.spring.project.service;

import com.spring.project.domain.EntityModel;
import com.spring.project.domain.ProportyModel;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProportyModel}.
 */
public interface ProportyModelService {

	/**
	 * Save a proportyModel.
	 *
	 * @param proportyModel the entity to save.
	 * @return the persisted entity.
	 */
	ProportyModel save(ProportyModel proportyModel);

	/**
	 * Get all the proportyModels.
	 *
	 * @return the list of entities.
	 */
	List<ProportyModel> findAll();
	/**
	 * Get all the ProportyModelDTO where ProportyData is {@code null}.
	 *
	 * @return the list of entities.
	 */
	List<ProportyModel> findAllWhereProportyDataIsNull();


	/**
	 * Get the "id" proportyModel.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<ProportyModel> findOne(String id);

	/**
	 * Delete the "id" proportyModel.
	 *
	 * @param id the id of the entity.
	 */
	void delete(String id);

	boolean checkProporty(ProportyModel proportyModel);



	List<ProportyModel> trouverpropentity(String d) ; 


	List<ProportyModel> proportiesPerInstance(String instanceName);
	
	
	
	String  defineType(String name);






}
