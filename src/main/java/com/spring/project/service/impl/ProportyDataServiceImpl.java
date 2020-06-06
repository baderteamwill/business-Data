package com.spring.project.service.impl;

import com.spring.project.service.ProportyDataService;
import com.spring.project.domain.ProportyData;
import com.spring.project.domain.enumeration.Type;
import com.spring.project.repository.ProportyDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProportyData}.
 */
@Service
public class ProportyDataServiceImpl implements ProportyDataService {

	private final Logger log = LoggerFactory.getLogger(ProportyDataServiceImpl.class);

	private final ProportyDataRepository proportyDataRepository;

	public ProportyDataServiceImpl(ProportyDataRepository proportyDataRepository) {
		this.proportyDataRepository = proportyDataRepository;
	}

	/**
	 * Save a proportyData.
	 *
	 * @param proportyData
	 *            the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ProportyData save(ProportyData proportyData) {
		log.debug("Request to save ProportyData : {}", proportyData);

		if ((proportyData.getProportyModel().getProportyType() == Type.STRING) && (searchModel(proportyData) == true)) {
			if (proportyData.getProportyValue().matches("^[a-zA-Z]+$")) {
				proportyData.setProportyValue(proportyData.getProportyValue());

				System.out.println(proportyData.getEntityInstance());
				return proportyDataRepository.save(proportyData);

			}
		} else if (proportyData.getProportyModel().getProportyType() == Type.INT && (searchModel(proportyData) == true)) {
			if (proportyData.getProportyValue().matches("^[0-9]+$")) {
				proportyData.setProportyValue(proportyData.getProportyValue());
				return proportyDataRepository.save(proportyData);
			}

		} else if (proportyData.getProportyModel().getProportyType() == Type.FLOAT && (searchModel(proportyData) == true)) {
			if (proportyData.getProportyValue().matches("^(\\d*)([,.]?(\\d*))*$")) {
				proportyData.setProportyValue(proportyData.getProportyValue());
				return proportyDataRepository.save(proportyData);
			}

		} else if (proportyData.getProportyModel().getProportyType() == Type.BOOLEAN && (searchModel(proportyData) == true)) {
			if (proportyData.getProportyValue().equalsIgnoreCase("TRUE")
					|| proportyData.getProportyValue().equalsIgnoreCase("FALSE")) {
				proportyData.setProportyValue(proportyData.getProportyValue());
				return proportyDataRepository.save(proportyData);
			}

		}

		return proportyData;

	}

	/**
	 * Get all the proportyData.
	 *
	 * @return the list of entities.
	 */
	@Override
	public List<ProportyData> findAll() {
		log.debug("Request to get all ProportyData");
		return proportyDataRepository.findAll();
	}

	/**
	 * Get one proportyData by id.
	 *
	 * @param id
	 *            the id of the entity.
	 * @return the entity.
	 */
	@Override
	public Optional<ProportyData> findOne(String id) {
		log.debug("Request to get ProportyData : {}", id);
		return proportyDataRepository.findById(id);
	}

	/**
	 * Delete the proportyData by id.
	 *
	 * @param id
	 *            the id of the entity.
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete ProportyData : {}", id);
		proportyDataRepository.deleteById(id);
	}

	public boolean searchModel(ProportyData proportyData) {
		boolean test = true;
		List<ProportyData> prop = new ArrayList<ProportyData>();
		List<ProportyData> p = new ArrayList<ProportyData>();

		for (int i = 0; i < findAll().size(); i++) {
			if (proportyData.getEntityInstance().equals(findAll().get(i).getEntityInstance()))
				prop.add(findAll().get(i));
		}
		System.out.println("prop" + prop);
		for (int c = 0; c < prop.size(); c++) {
			System.out.println("proporties" + prop.get(c).getProportyValue());

		}

		for (int j = 0; j < prop.size(); j++) {
			if (proportyData.getProportyModel().equals( prop.get(j).getProportyModel())) {
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

	@Override
	public List<ProportyData> trouverproporties(String test) {

		List<ProportyData> list  =  new ArrayList<ProportyData>(); 
		for ( int i=0 ; i<findAll().size();i++)
		{
			if (test.equalsIgnoreCase(findAll().get(i).getProportyModel().getEntityModel().getEntityName()))
			{
				list.add(findAll().get(i));
				
				
			}
						
		}
		
		return list;
	}

	@Override
	public List<ProportyData> proportiesDetails(String instanceName) {
	List<ProportyData> list = new ArrayList<ProportyData>();
		
		for (int i = 0 ;i<findAll().size();i++)
		{
			
		if(findAll().get(i).getEntityInstance().getInstanceName().equalsIgnoreCase(instanceName))	
			
		{
			
			list.add(findAll().get(i));
			
		}
			
			
		}
		
		return list;
	}

	









}



