package com.spring.project.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.spring.project.domain.enumeration.RelationType;

/**
 * A EntityRelation.
 */
@Document(collection = "entity_relation")
public class EntityRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("relation")
    private RelationType relation;

    @DBRef
    @Field("entityModel")
    private EntityModel entityModel;

    @DBRef
    @Field("entityModel2")
    private EntityModel entityModel2;

    @DBRef
    @Field("instanceRelation")
    private Set<InstanceRelation> instanceRelations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RelationType getRelation() {
        return relation;
    }

    public EntityRelation relation(RelationType relation) {
        this.relation = relation;
        return this;
    }

    public void setRelation(RelationType relation) {
        this.relation = relation;
    }

    public EntityModel getEntityModel() {
        return entityModel;
    }

    public EntityRelation entityModel(EntityModel entityModel) {
        this.entityModel = entityModel;
        return this;
    }

    public void setEntityModel(EntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public EntityModel getEntityModel2() {
        return entityModel2;
    }

    public EntityRelation entityModel2(EntityModel entityModel) {
        this.entityModel2 = entityModel;
        return this;
    }

    public void setEntityModel2(EntityModel entityModel) {
        this.entityModel2 = entityModel;
    }

    public Set<InstanceRelation> getInstanceRelations() {
        return instanceRelations;
    }

    public EntityRelation instanceRelations(Set<InstanceRelation> instanceRelations) {
        this.instanceRelations = instanceRelations;
        return this;
    }

    public EntityRelation addInstanceRelation(InstanceRelation instanceRelation) {
        this.instanceRelations.add(instanceRelation);
        instanceRelation.setEntityRelation(this);
        return this;
    }

    public EntityRelation removeInstanceRelation(InstanceRelation instanceRelation) {
        this.instanceRelations.remove(instanceRelation);
        instanceRelation.setEntityRelation(null);
        return this;
    }

    public void setInstanceRelations(Set<InstanceRelation> instanceRelations) {
        this.instanceRelations = instanceRelations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityRelation)) {
            return false;
        }
        return id != null && id.equals(((EntityRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntityRelation{" +
            "id=" + getId() +
            ", relation='" + getRelation() + "'" +
            "}";
    }
}
