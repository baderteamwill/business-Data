package com.spring.project.repository;

import com.spring.project.domain.EntityInstance;
import com.spring.project.domain.EntityModel;
import com.spring.project.domain.ProportyModel;
import org.springframework.data.mongodb.repository.Query;

import com.spring.project.domain.enumeration.Type;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ProportyModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProportyModelRepository extends MongoRepository<ProportyModel, String> {
	List<ProportyModel> findByProportyType (Type proportyType ) ;
	ProportyModel findByProportyName (String prop ) ;

}
