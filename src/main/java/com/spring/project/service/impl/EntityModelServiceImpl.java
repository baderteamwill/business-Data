package com.spring.project.service.impl;

import com.spring.project.service.EntityModelService;
import com.spring.project.domain.DatabaseModel;
import com.spring.project.domain.EntityModel;
import com.spring.project.repository.EntityModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link EntityModel}.
 */
@Service
public class EntityModelServiceImpl implements EntityModelService {

	private final Logger log = LoggerFactory.getLogger(EntityModelServiceImpl.class);

	private final EntityModelRepository entityModelRepository;

	public EntityModelServiceImpl(EntityModelRepository entityModelRepository) {
		this.entityModelRepository = entityModelRepository;
	}

	/**
	 * Save a entityModel.
	 *
	 * @param entityModel the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public EntityModel save(EntityModel entityModel) {
		log.debug("Request to save EntityModel : {}", entityModel);
		if ((isString(entityModel.getEntityName())==true)&&(checkEntity(entityModel)==true)) {
			return entityModelRepository.save(entityModel);
		}
		return entityModel;
	}

	/**
	 * Get all the entityModels.
	 *
	 * @return the list of entities.
	 */
	@Override
	public List<EntityModel> findAll() {
		log.debug("Request to get all EntityModels");
		return entityModelRepository.findAll();
	}



	/**
	 *  Get all the entityModels where EntityRelation is {@code null}.
	 *  @return the list of entities.
	 */
	public List<EntityModel> findAllWhereEntityRelationIsNull() {
		log.debug("Request to get all entityModels where EntityRelation is null");
		return StreamSupport
				.stream(entityModelRepository.findAll().spliterator(), false)
				.filter(entityModel -> entityModel.getEntityRelation() == null)
				.collect(Collectors.toList());
	}


	/**
	 *  Get all the entityModels where EntityRelation2 is {@code null}.
	 *  @return the list of entities.
	 */
	public List<EntityModel> findAllWhereEntityRelation2IsNull() {
		log.debug("Request to get all entityModels where EntityRelation2 is null");
		return StreamSupport
				.stream(entityModelRepository.findAll().spliterator(), false)
				.filter(entityModel -> entityModel.getEntityRelation2() == null)
				.collect(Collectors.toList());
	}

	/**
	 * Get one entityModel by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	public Optional<EntityModel> findOne(String id) {
		log.debug("Request to get EntityModel : {}", id);
		return entityModelRepository.findById(id);
	}

	/**
	 * Delete the entityModel by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete EntityModel : {}", id);
		entityModelRepository.deleteById(id);
	}


	public boolean isString (String text) {
		if (text.matches("^[a-zA-Z]+$")) {
			return true;
		} 	
		return false  ; 
	}

	public List<EntityModel> trouver(String d)  
	{

		List<EntityModel> list = new ArrayList<EntityModel>();


		for (int i=0 ; i<findAll().size();i++)
		{
			if (d.equalsIgnoreCase(findAll().get(i).getDatabaseModel().getDatabaseModel()))
			{
				list.add(findAll().get(i));
			}

		}

		return list;



	}

	@Override
	public boolean checkEntity(EntityModel entityModel) {
		boolean test=true ;
		List<EntityModel>list =new ArrayList<EntityModel>();
		
		
		for (int i=0;i<findAll().size();i++)
		{if(entityModel.getDatabaseModel().getDatabaseModel().equalsIgnoreCase(findAll().get(i).getDatabaseModel().getDatabaseModel()))
		{
			list.add(findAll().get(i));


		}	
		
		for (int j=0 ; j<list.size();j++)
		{
			if (list.get(j).getEntityName().equalsIgnoreCase(entityModel.getEntityName()))
			{
				
				test=false ;
				
			}
			
		}
		
		}

		return test;
	}

	@Override
	public List<EntityModel> resteEntities(String name) {

		EntityModel e =  entityModelRepository.findByEntityName(name);


		List<EntityModel> list = new ArrayList<EntityModel>();
		for (int i =0;i<findAll().size();i++)
		{
			if (!findAll().get(i).getEntityName().equals(name))

			{

				list.add(findAll().get(i));

			}
			
		}
		return list;
	}



}
