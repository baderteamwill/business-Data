package com.spring.project.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

import com.spring.project.domain.enumeration.Type;

/**
 * A ProportyModel.
 */
@Document(collection = "proporty_model")
public class ProportyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("proporty_name")
    private String proportyName;

    @Field("proporty_type")
    private Type proportyType;

    @DBRef
    @Field("entityModel")
    private EntityModel entityModel;

    @DBRef
    @Field("proportyData")
    private ProportyData proportyData;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProportyName() {
        return proportyName;
    }

    public ProportyModel proportyName(String proportyName) {
        this.proportyName = proportyName;
        return this;
    }

    public void setProportyName(String proportyName) {
        this.proportyName = proportyName;
    }

    public Type getProportyType() {
        return proportyType;
    }

    public ProportyModel proportyType(Type proportyType) {
        this.proportyType = proportyType;
        return this;
    }

    public void setProportyType(Type proportyType) {
        this.proportyType = proportyType;
    }

    public EntityModel getEntityModel() {
        return entityModel;
    }

    public ProportyModel entityModel(EntityModel entityModel) {
        this.entityModel = entityModel;
        return this;
    }

    public void setEntityModel(EntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public ProportyData getProportyData() {
        return proportyData;
    }

    public ProportyModel proportyData(ProportyData proportyData) {
        this.proportyData = proportyData;
        return this;
    }

    public void setProportyData(ProportyData proportyData) {
        this.proportyData = proportyData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProportyModel)) {
            return false;
        }
        return id != null && id.equals(((ProportyModel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProportyModel{" +
            "id=" + getId() +
            ", proportyName='" + getProportyName() + "'" +
            ", proportyType='" + getProportyType() + "'" +
            "}";
    }
}
