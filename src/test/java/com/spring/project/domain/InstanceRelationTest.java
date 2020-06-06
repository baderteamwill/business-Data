package com.spring.project.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spring.project.web.rest.TestUtil;

public class InstanceRelationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstanceRelation.class);
        InstanceRelation instanceRelation1 = new InstanceRelation();
        instanceRelation1.setId("id1");
        InstanceRelation instanceRelation2 = new InstanceRelation();
        instanceRelation2.setId(instanceRelation1.getId());
        assertThat(instanceRelation1).isEqualTo(instanceRelation2);
        instanceRelation2.setId("id2");
        assertThat(instanceRelation1).isNotEqualTo(instanceRelation2);
        instanceRelation1.setId(null);
        assertThat(instanceRelation1).isNotEqualTo(instanceRelation2);
    }
}
