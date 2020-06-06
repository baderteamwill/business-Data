package com.spring.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EntityModel.
 */
@Document(collection = "entity_model")
public class EntityModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("entity_name")
    private String entityName;

    @Field("entity_description")
    private String entityDescription;

    @DBRef
    @Field("databaseModel")
    @JsonIgnoreProperties("entityModels")
    private DatabaseModel databaseModel;

    @DBRef
    @Field("proportyModel")
    private Set<ProportyModel> proportyModels = new HashSet<>();

    @DBRef
    @Field("entityInstance")
    private Set<EntityInstance> entityInstances = new HashSet<>();

    @DBRef
    @Field("entityRelation")
    private EntityRelation entityRelation;

    @DBRef
    @Field("entityRelation2")
    private EntityRelation entityRelation2;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public EntityModel entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public EntityModel entityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
        return this;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    public DatabaseModel getDatabaseModel() {
        return databaseModel;
    }

    public EntityModel databaseModel(DatabaseModel databaseModel) {
        this.databaseModel = databaseModel;
        return this;
    }

    public void setDatabaseModel(DatabaseModel databaseModel) {
        this.databaseModel = databaseModel;
    }

    public Set<ProportyModel> getProportyModels() {
        return proportyModels;
    }

    public EntityModel proportyModels(Set<ProportyModel> proportyModels) {
        this.proportyModels = proportyModels;
        return this;
    }

    public EntityModel addProportyModel(ProportyModel proportyModel) {
        this.proportyModels.add(proportyModel);
        proportyModel.setEntityModel(this);
        return this;
    }

    public EntityModel removeProportyModel(ProportyModel proportyModel) {
        this.proportyModels.remove(proportyModel);
        proportyModel.setEntityModel(null);
        return this;
    }

    public void setProportyModels(Set<ProportyModel> proportyModels) {
        this.proportyModels = proportyModels;
    }

    public Set<EntityInstance> getEntityInstances() {
        return entityInstances;
    }

    public EntityModel entityInstances(Set<EntityInstance> entityInstances) {
        this.entityInstances = entityInstances;
        return this;
    }

    public EntityModel addEntityInstance(EntityInstance entityInstance) {
        this.entityInstances.add(entityInstance);
        entityInstance.setEntityModel(this);
        return this;
    }

    public EntityModel removeEntityInstance(EntityInstance entityInstance) {
        this.entityInstances.remove(entityInstance);
        entityInstance.setEntityModel(null);
        return this;
    }

    public void setEntityInstances(Set<EntityInstance> entityInstances) {
        this.entityInstances = entityInstances;
    }

    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    public EntityModel entityRelation(EntityRelation entityRelation) {
        this.entityRelation = entityRelation;
        return this;
    }

    public void setEntityRelation(EntityRelation entityRelation) {
        this.entityRelation = entityRelation;
    }

    public EntityRelation getEntityRelation2() {
        return entityRelation2;
    }

    public EntityModel entityRelation2(EntityRelation entityRelation) {
        this.entityRelation2 = entityRelation;
        return this;
    }

    public void setEntityRelation2(EntityRelation entityRelation) {
        this.entityRelation2 = entityRelation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityModel)) {
            return false;
        }
        return id != null && id.equals(((EntityModel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntityModel{" +
            "id=" + getId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityDescription='" + getEntityDescription() + "'" +
            "}";
    }
}
