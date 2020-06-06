package com.spring.project.service.impl;

import com.spring.project.service.InstanceRelationService;
import com.spring.project.domain.EntityInstance;
import com.spring.project.domain.EntityRelation;
import com.spring.project.domain.InstanceRelation;
import com.spring.project.repository.InstanceRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InstanceRelation}.
 */
@Service
public class InstanceRelationServiceImpl implements InstanceRelationService {
	@Autowired
	private  EntityRelationServiceImpl entityRelationServiceImpl;
	private final Logger log = LoggerFactory.getLogger(InstanceRelationServiceImpl.class);

	private final InstanceRelationRepository instanceRelationRepository;

	public InstanceRelationServiceImpl(InstanceRelationRepository instanceRelationRepository) {
		this.instanceRelationRepository = instanceRelationRepository;
	}

	/**
	 * Save a instanceRelation.
	 *
	 * @param instanceRelation the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public InstanceRelation save(InstanceRelation instanceRelation) {
		log.debug("Request to save InstanceRelation : {}", instanceRelation);
		if ((!instanceRelation.getEntityInstance().equals(instanceRelation.getEntityInstance2()))&&(searchModel(instanceRelation)==true)) {
			return instanceRelationRepository.save(instanceRelation);
		}
		return instanceRelation;}

	/**
	 * Get all the instanceRelations.
	 *
	 * @return the list of entities.
	 */
	@Override
	public List<InstanceRelation> findAll() {
		log.debug("Request to get all InstanceRelations");
		return instanceRelationRepository.findAll();
	}


	/**
	 * Get one instanceRelation by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	public Optional<InstanceRelation> findOne(String id) {
		log.debug("Request to get InstanceRelation : {}", id);
		return instanceRelationRepository.findById(id);
	}

	/**
	 * Delete the instanceRelation by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete InstanceRelation : {}", id);
		instanceRelationRepository.deleteById(id);
	}


	public boolean searchModel(InstanceRelation instanceRelation) {
		boolean test = true;
		List<InstanceRelation> prop = new ArrayList<InstanceRelation>();
		List<InstanceRelation> p = new ArrayList<InstanceRelation>();

		for (int i = 0; i < findAll().size(); i++) {
			if (instanceRelation.getEntityInstance().equals(findAll().get(i).getEntityInstance()))
				prop.add(findAll().get(i));
		}
		System.out.println("prop" + prop);


		for (int j = 0; j < prop.size(); j++) {
			if (instanceRelation.getEntityInstance2().equals( prop.get(j).getEntityInstance2())) {
				p.add(prop.get(j));

			}

		}
		System.out.println("list" + p);
		if (p.size() == 0) {
			test = true;
		} else {

			test = false;
		}

		return test;
	}


	public boolean proportSearch(InstanceRelation instanceRelation) {
		List<EntityRelation> lst = new ArrayList<EntityRelation>();
		List<EntityRelation> listModele = new ArrayList<EntityRelation>();
		lst =  entityRelationServiceImpl.findAll();
		boolean test = true ;
		for (int i = 0;i<lst.size();i++)
		{
			if (instanceRelation.getEntityInstance().getEntityModel().equals(lst.get(i).getEntityModel()))
			{
				listModele.add(lst.get(i));	
			}

		}	

		for (int j= 0 ;j<listModele.size();j++)
		{
			if (instanceRelation.getEntityInstance2().getEntityModel().equals(listModele.get(j).getEntityModel2()))
			{
				test = true ;
			}
			else {
				test=false ;
			}		

		}
		return test;  	
	}

	@Override
	public List<EntityInstance> trouverApresInstance(String test) {
		// TODO Auto-generated method stub
		return null;
	}


}
