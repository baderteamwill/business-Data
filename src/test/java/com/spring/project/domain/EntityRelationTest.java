package com.spring.project.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spring.project.web.rest.TestUtil;

public class EntityRelationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityRelation.class);
        EntityRelation entityRelation1 = new EntityRelation();
        entityRelation1.setId("id1");
        EntityRelation entityRelation2 = new EntityRelation();
        entityRelation2.setId(entityRelation1.getId());
        assertThat(entityRelation1).isEqualTo(entityRelation2);
        entityRelation2.setId("id2");
        assertThat(entityRelation1).isNotEqualTo(entityRelation2);
        entityRelation1.setId(null);
        assertThat(entityRelation1).isNotEqualTo(entityRelation2);
    }
}
