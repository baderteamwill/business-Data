package com.spring.project.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spring.project.web.rest.TestUtil;

public class EntityModelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityModel.class);
        EntityModel entityModel1 = new EntityModel();
        entityModel1.setId("id1");
        EntityModel entityModel2 = new EntityModel();
        entityModel2.setId(entityModel1.getId());
        assertThat(entityModel1).isEqualTo(entityModel2);
        entityModel2.setId("id2");
        assertThat(entityModel1).isNotEqualTo(entityModel2);
        entityModel1.setId(null);
        assertThat(entityModel1).isNotEqualTo(entityModel2);
    }
}
