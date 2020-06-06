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
 * A EntityInstance.
 */
@Document(collection = "entity_instance")
public class EntityInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("instance_name")
    private String instanceName;

    @DBRef
    @Field("proportyData")
    private Set<ProportyData> proportyData = new HashSet<>();

    @DBRef
    @Field("entityModel")
    private EntityModel entityModel;

    @DBRef
    @Field("instanceRelation")
    private InstanceRelation instanceRelation;

    @DBRef
    @Field("instanceRelation2")
    private InstanceRelation instanceRelation2;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public EntityInstance instanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Set<ProportyData> getProportyData() {
        return proportyData;
    }

    public EntityInstance proportyData(Set<ProportyData> proportyData) {
        this.proportyData = proportyData;
        return this;
    }

    public EntityInstance addProportyData(ProportyData proportyData) {
        this.proportyData.add(proportyData);
        proportyData.setEntityInstance(this);
        return this;
    }

    public EntityInstance removeProportyData(ProportyData proportyData) {
        this.proportyData.remove(proportyData);
        proportyData.setEntityInstance(null);
        return this;
    }

    public void setProportyData(Set<ProportyData> proportyData) {
        this.proportyData = proportyData;
    }

    public EntityModel getEntityModel() {
        return entityModel;
    }

    public EntityInstance entityModel(EntityModel entityModel) {
        this.entityModel = entityModel;
        return this;
    }

    public void setEntityModel(EntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public InstanceRelation getInstanceRelation() {
        return instanceRelation;
    }

    public EntityInstance instanceRelation(InstanceRelation instanceRelation) {
        this.instanceRelation = instanceRelation;
        return this;
    }

    public void setInstanceRelation(InstanceRelation instanceRelation) {
        this.instanceRelation = instanceRelation;
    }

    public InstanceRelation getInstanceRelation2() {
        return instanceRelation2;
    }

    public EntityInstance instanceRelation2(InstanceRelation instanceRelation) {
        this.instanceRelation2 = instanceRelation;
        return this;
    }

    public void setInstanceRelation2(InstanceRelation instanceRelation) {
        this.instanceRelation2 = instanceRelation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityInstance)) {
            return false;
        }
        return id != null && id.equals(((EntityInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntityInstance{" +
            "id=" + getId() +
            ", instanceName='" + getInstanceName() + "'" +
            "}";
    }
}
