package com.spring.project.repository;

import com.spring.project.domain.DatabaseModel;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the DatabaseModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatabaseModelRepository extends MongoRepository<DatabaseModel, String> {
	List<DatabaseModel> findByDatabaseModel (String dbName ) ;
}
