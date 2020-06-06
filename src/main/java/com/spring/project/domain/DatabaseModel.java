package com.spring.project.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DatabaseModel.
 */
@Document(collection = "database_model")
public class DatabaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("database_model")
    private String databaseModel;

    @Field("database_description")
    private String databaseDescription;

    @DBRef
    @Field("entityModel")
    private Set<EntityModel> entityModels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatabaseModel() {
        return databaseModel;
    }

    public DatabaseModel databaseModel(String databaseModel) {
        this.databaseModel = databaseModel;
        return this;
    }

    public void setDatabaseModel(String databaseModel) {
        this.databaseModel = databaseModel;
    }

    public String getDatabaseDescription() {
        return databaseDescription;
    }

    public DatabaseModel databaseDescription(String databaseDescription) {
        this.databaseDescription = databaseDescription;
        return this;
    }

    public void setDatabaseDescription(String databaseDescription) {
        this.databaseDescription = databaseDescription;
    }

    public Set<EntityModel> getEntityModels() {
        return entityModels;
    }

    public DatabaseModel entityModels(Set<EntityModel> entityModels) {
        this.entityModels = entityModels;
        return this;
    }

    public DatabaseModel addEntityModel(EntityModel entityModel) {
        this.entityModels.add(entityModel);
        entityModel.setDatabaseModel(this);
        return this;
    }

    public DatabaseModel removeEntityModel(EntityModel entityModel) {
        this.entityModels.remove(entityModel);
        entityModel.setDatabaseModel(null);
        return this;
    }

    public void setEntityModels(Set<EntityModel> entityModels) {
        this.entityModels = entityModels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatabaseModel)) {
            return false;
        }
        return id != null && id.equals(((DatabaseModel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DatabaseModel{" +
            "id=" + getId() +
            ", databaseModel='" + getDatabaseModel() + "'" +
            ", databaseDescription='" + getDatabaseDescription() + "'" +
            "}";
    }
}
