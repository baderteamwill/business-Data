package com.spring.project.service.impl;

import com.spring.project.service.ProportyModelService;
import com.spring.project.domain.ProportyModel;
import com.spring.project.repository.ProportyModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link ProportyModel}.
 */
@Service
public class ProportyModelServiceImpl implements ProportyModelService {

	private final Logger log = LoggerFactory.getLogger(ProportyModelServiceImpl.class);

	private final ProportyModelRepository proportyModelRepository;
@Autowired
	private  EntityInstanceServiceImpl entityInstanceServiceImpl;

	
	
	public ProportyModelServiceImpl(ProportyModelRepository proportyModelRepository) {
		this.proportyModelRepository = proportyModelRepository;
	}

	/**
	 * Save a proportyModel.
	 *
	 * @param proportyModel the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public ProportyModel save(ProportyModel proportyModel) {
		log.debug("Request to save ProportyModel : {}", proportyModel);
		if ((isString(proportyModel.getProportyName())==true)&&(checkProporty(proportyModel)==true))
		{
			return proportyModelRepository.save(proportyModel);
		}
		return proportyModel;
	}

	/**
	 * Get all the proportyModels.
	 *
	 * @return the list of entities.
	 */
	@Override
	public List<ProportyModel> findAll() {
		log.debug("Request to get all ProportyModels");
		return proportyModelRepository.findAll();
	}



	/**
	 *  Get all the proportyModels where ProportyData is {@code null}.
	 *  @return the list of entities.
	 */
	public List<ProportyModel> findAllWhereProportyDataIsNull() {
		log.debug("Request to get all proportyModels where ProportyData is null");
		return StreamSupport
				.stream(proportyModelRepository.findAll().spliterator(), false)
				.filter(proportyModel -> proportyModel.getProportyData() == null)
				.collect(Collectors.toList());
	}

	/**
	 * Get one proportyModel by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	public Optional<ProportyModel> findOne(String id) {
		log.debug("Request to get ProportyModel : {}", id);
		return proportyModelRepository.findById(id);
	}

	/**
	 * Delete the proportyModel by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete ProportyModel : {}", id);
		proportyModelRepository.deleteById(id);
	}


	public boolean isString (String text) {
		if (text.matches("^[a-zA-Z]+$")) {
			return true;
		} 	
		return false  ; 
	}

	@Override
	public boolean checkProporty(ProportyModel proportyModel) {
		boolean test=true ; 
		List<ProportyModel> list = new ArrayList<ProportyModel>();

		for (int j= 0;j<findAll().size();j++)
		{
			if (proportyModel.getEntityModel().getEntityName().equalsIgnoreCase(findAll().get(j).getEntityModel().getEntityName()))
			{
				list.add(findAll().get(j));

			}


		}



		for (int i=0;i<list.size();i++)
		{
			if (proportyModel.getProportyName().equalsIgnoreCase(list.get(i).getProportyName()))
			{
				test=false;
			}

		}


		return test;
	}

	@Override
	public List<ProportyModel> trouverpropentity(String d) {
		List<ProportyModel> list = new ArrayList<ProportyModel>();

		for (int i = 0 ; i<findAll().size();i++)
		{
			if (findAll().get(i).getEntityModel().getEntityName().equalsIgnoreCase(d))
			{
				
				
				list.add(findAll().get(i));
				
				
				
				
			}


		}

		return list;
	}

	@Override
	public List<ProportyModel> proportiesPerInstance(String instanceName) {
		
	String test = 	entityInstanceServiceImpl.searchName(instanceName);
		
		List<ProportyModel>list = new ArrayList<ProportyModel>();
		for (int i = 0 ; i<findAll().size();i++)
		{
			if(findAll().get(i).getEntityModel().getEntityName().equalsIgnoreCase(test))
			{
				
				list.add(findAll().get(i));
				
			}
			
		}
		
		
		return list;
	}

	@Override
	public String defineType(String name) {
		ProportyModel p = proportyModelRepository.findByProportyName(name);
		return p.getProportyType().toString();
	}




}
