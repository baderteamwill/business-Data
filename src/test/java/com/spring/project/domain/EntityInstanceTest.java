package com.spring.project.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spring.project.web.rest.TestUtil;

public class EntityInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityInstance.class);
        EntityInstance entityInstance1 = new EntityInstance();
        entityInstance1.setId("id1");
        EntityInstance entityInstance2 = new EntityInstance();
        entityInstance2.setId(entityInstance1.getId());
        assertThat(entityInstance1).isEqualTo(entityInstance2);
        entityInstance2.setId("id2");
        assertThat(entityInstance1).isNotEqualTo(entityInstance2);
        entityInstance1.setId(null);
        assertThat(entityInstance1).isNotEqualTo(entityInstance2);
    }
}
