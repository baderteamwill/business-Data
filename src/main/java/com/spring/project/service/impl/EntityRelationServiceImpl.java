package com.spring.project.service.impl;

import com.spring.project.service.EntityRelationService;
import com.spring.project.domain.EntityRelation;
import com.spring.project.repository.EntityRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EntityRelation}.
 */
@Service
public class EntityRelationServiceImpl implements EntityRelationService {

	private final Logger log = LoggerFactory.getLogger(EntityRelationServiceImpl.class);

	private final EntityRelationRepository entityRelationRepository;

	public EntityRelationServiceImpl(EntityRelationRepository entityRelationRepository) {
		this.entityRelationRepository = entityRelationRepository;
	}

	/**
	 * Save a entityRelation.
	 *
	 * @param entityRelation the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public EntityRelation save(EntityRelation entityRelation) {
		log.debug("Request to save EntityRelation : {}", entityRelation);
		if ((recherche(entityRelation)==true)&&(checkrelations(entityRelation)==false)) {
			return entityRelationRepository.save(entityRelation);}
		return entityRelation;

	}

	/**
	 * Get all the entityRelations.
	 *
	 * @return the list of entities.
	 */
	@Override
	public List<EntityRelation> findAll() {
		log.debug("Request to get all EntityRelations");
		return entityRelationRepository.findAll();
	}


	/**
	 * Get one entityRelation by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	public Optional<EntityRelation> findOne(String id) {
		log.debug("Request to get EntityRelation : {}", id);
		return entityRelationRepository.findById(id);
	}

	/**
	 * Delete the entityRelation by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete EntityRelation : {}", id);
		entityRelationRepository.deleteById(id);
	}



	public boolean recherche (EntityRelation entityRelation)
	{

		if(entityRelation.getEntityModel().equals(entityRelation.getEntityModel2()))
		{
			return false ;

		}
		return true;

	}

	//chercher une relation existante ou pas dans les cas extremes    
	@Override
	public boolean checkrelations(EntityRelation entityRelation) {
		boolean test = false ;
		List<EntityRelation> list = new ArrayList<EntityRelation>(); 
		for (int i = 0;i<findAll().size();i++)
		{
			if ((entityRelation.getEntityModel().getEntityName().equalsIgnoreCase(findAll().get(i).getEntityModel().getEntityName()))||(entityRelation.getEntityModel().getEntityName().equalsIgnoreCase(findAll().get(i).getEntityModel2().getEntityName())))
			{
				list.add(findAll().get(i));


			}

			for (int j = 0;j<list.size();j++)	
			{
				if((entityRelation.getEntityModel2().getEntityName().equalsIgnoreCase(list.get(j).getEntityModel2().getEntityName()))||entityRelation.getEntityModel2().getEntityName().equalsIgnoreCase(list.get(j).getEntityModel().getEntityName()))

				{
					test=true;

				}
			}






		}



		return test;
	}

	@Override
	public List<EntityRelation> listOfSameSataModel(String name) {

		List<EntityRelation> list = new ArrayList<EntityRelation>();
		for (int i = 0 ; i<findAll().size();i++)
		{
			if ((findAll().get(i).getEntityModel().getDatabaseModel().getDatabaseModel().equalsIgnoreCase(name))&&(findAll().get(i).getEntityModel2().getDatabaseModel().getDatabaseModel().equalsIgnoreCase(name)))
			{
				
				list.add(findAll().get(i));
				
				
			}
		}
		
		
		return list;
	}













}
