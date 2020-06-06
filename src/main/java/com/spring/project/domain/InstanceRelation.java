package com.spring.project.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A InstanceRelation.
 */
@Document(collection = "instance_relation")
public class InstanceRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("entityInstance")
    private EntityInstance entityInstance;

    @DBRef
    @Field("entityInstance2")
    private EntityInstance entityInstance2;

    @DBRef
    @Field("entityRelation")
    @JsonIgnoreProperties("instanceRelations")
    private EntityRelation entityRelation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    public InstanceRelation entityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
        return this;
    }

    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }

    public EntityInstance getEntityInstance2() {
        return entityInstance2;
    }

    public InstanceRelation entityInstance2(EntityInstance entityInstance) {
        this.entityInstance2 = entityInstance;
        return this;
    }

    public void setEntityInstance2(EntityInstance entityInstance) {
        this.entityInstance2 = entityInstance;
    }

    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    public InstanceRelation entityRelation(EntityRelation entityRelation) {
        this.entityRelation = entityRelation;
        return this;
    }

    public void setEntityRelation(EntityRelation entityRelation) {
        this.entityRelation = entityRelation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstanceRelation)) {
            return false;
        }
        return id != null && id.equals(((InstanceRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InstanceRelation{" +
            "id=" + getId() +
            "}";
    }
}
