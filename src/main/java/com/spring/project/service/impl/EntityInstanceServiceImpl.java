package com.spring.project.service.impl;

import com.spring.project.service.EntityInstanceService;
import com.spring.project.domain.EntityInstance;
import com.spring.project.domain.EntityRelation;
import com.spring.project.repository.EntityInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link EntityInstance}.
 */
@Service
public class EntityInstanceServiceImpl implements EntityInstanceService {

    private final Logger log = LoggerFactory.getLogger(EntityInstanceServiceImpl.class);

    private final EntityInstanceRepository entityInstanceRepository;

    public EntityInstanceServiceImpl(EntityInstanceRepository entityInstanceRepository) {
        this.entityInstanceRepository = entityInstanceRepository;
    }

    /**
     * Save a entityInstance.
     *
     * @param entityInstance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EntityInstance save(EntityInstance entityInstance) {
        log.debug("Request to save EntityInstance : {}", entityInstance);
        if (checkInstance(entityInstance)==true) {
        return entityInstanceRepository.save(entityInstance);
    }
		return entityInstance;}

    /**
     * Get all the entityInstances.
     *
     * @return the list of entities.
     */
    @Override
    public List<EntityInstance> findAll() {
        log.debug("Request to get all EntityInstances");
        return entityInstanceRepository.findAll();
    }



    /**
    *  Get all the entityInstances where InstanceRelation is {@code null}.
     *  @return the list of entities.
     */
    public List<EntityInstance> findAllWhereInstanceRelationIsNull() {
        log.debug("Request to get all entityInstances where InstanceRelation is null");
        return StreamSupport
            .stream(entityInstanceRepository.findAll().spliterator(), false)
            .filter(entityInstance -> entityInstance.getInstanceRelation() == null)
            .collect(Collectors.toList());
    }


    /**
    *  Get all the entityInstances where InstanceRelation2 is {@code null}.
     *  @return the list of entities.
     */
    public List<EntityInstance> findAllWhereInstanceRelation2IsNull() {
        log.debug("Request to get all entityInstances where InstanceRelation2 is null");
        return StreamSupport
            .stream(entityInstanceRepository.findAll().spliterator(), false)
            .filter(entityInstance -> entityInstance.getInstanceRelation2() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one entityInstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<EntityInstance> findOne(String id) {
        log.debug("Request to get EntityInstance : {}", id);
        return entityInstanceRepository.findById(id);
    }

    /**
     * Delete the entityInstance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete EntityInstance : {}", id);
        entityInstanceRepository.deleteById(id);
    }

	@Override
	public boolean checkInstance(EntityInstance entityInstance) {
		boolean test=true ;
		List<EntityInstance>list = new ArrayList<EntityInstance>();
		for (int i =0;i<findAll().size();i++)
		{
			if (entityInstance.getEntityModel().getEntityName().equalsIgnoreCase(findAll().get(i).getEntityModel().getEntityName()))
			{
				list.add(findAll().get(i));
				
			}
		
			
		for (int j=0;j<list.size();j++)	
		{
			if (entityInstance.getInstanceName().equalsIgnoreCase(list.get(j).getInstanceName()))
			{
				test=false ;
				
			}
			
		}
			
			
			
		}
		
		
		
		
		
		
		return test;
	}

	@Override
	public List<EntityInstance> trouverInstance(String test) {
		List<EntityInstance> list =new ArrayList<>();
		
		for (int i =0;i<findAll().size();i++)
		{
			if(findAll().get(i).getEntityModel().getEntityName().equalsIgnoreCase(test))
			{
				
				list.add(findAll().get(i));
				
			}
			
			
		}
		
		
		
		return list;
	}
	
	
	
	public String searchName (String Name) {
	
		EntityInstance entity = entityInstanceRepository.findByInstanceName(Name);
		
		System.out.println("name"+ entity.getEntityModel().getEntityName());
	return entity.getEntityModel().getEntityName();	
		
	}

	@Override
	public List<EntityInstance> findbyname(String test) {
List<EntityInstance> list = new ArrayList<EntityInstance>();
	for (int i=0;i<findAll().size();i++)
	{
		if (findAll().get(i).getInstanceName().equalsIgnoreCase(test))
		{
			list.add(findAll().get(i));
			
		}
	}
		
		return list;
	}

	@Override
	public List<EntityInstance> findRelations(String relation) {
		List<EntityInstance> list =new ArrayList<EntityInstance>();
	for (int i = 0 ;i<findAll().size();i++)
	{
	if (findAll().get(i).getEntityModel().getEntityName().equalsIgnoreCase(relation))
	{
		
		
		list.add(findAll().get(i));
		
	}
	
	
	}
		
		
		
		
		return list;
	}

	@Override
	public List<EntityInstance> findSol(EntityRelation entityRelation) {
		List<EntityInstance> list =new ArrayList<EntityInstance>();

		for (int i = 0 ;i<findAll().size();i++)
		{
			if (findAll().get(i).getEntityModel().getEntityName().equalsIgnoreCase(entityRelation.getEntityModel().getEntityName()))
			{
				list.add(findAll().get(i));
			}
		}
		
		
		
		return list;
	}

	@Override
	public List<EntityInstance> Relations(String relation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

	
	
	
	
	
	
}
