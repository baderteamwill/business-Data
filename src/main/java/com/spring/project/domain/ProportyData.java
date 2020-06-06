package com.spring.project.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A ProportyData.
 */
@Document(collection = "proporty_data")
public class ProportyData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("proporty_value")
    private String proportyValue;

    @DBRef
    @Field("entityInstance")
    @JsonIgnoreProperties("proportyData")
    private EntityInstance entityInstance;

    @DBRef
    @Field("proportyModel")
    private ProportyModel proportyModel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProportyValue() {
        return proportyValue;
    }

    public ProportyData proportyValue(String proportyValue) {
        this.proportyValue = proportyValue;
        return this;
    }

    public void setProportyValue(String proportyValue) {
        this.proportyValue = proportyValue;
    }

    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    public ProportyData entityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
        return this;
    }

    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }

    public ProportyModel getProportyModel() {
        return proportyModel;
    }

    public ProportyData proportyModel(ProportyModel proportyModel) {
        this.proportyModel = proportyModel;
        return this;
    }

    public void setProportyModel(ProportyModel proportyModel) {
        this.proportyModel = proportyModel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProportyData)) {
            return false;
        }
        return id != null && id.equals(((ProportyData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "ProportyData [id=" + id + ", proportyValue=" + proportyValue + ", entityInstance=" + entityInstance
				+ ", proportyModel=" + proportyModel + "]";
	}

}
