package com.spring.project.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spring.project.web.rest.TestUtil;

public class ProportyModelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProportyModel.class);
        ProportyModel proportyModel1 = new ProportyModel();
        proportyModel1.setId("id1");
        ProportyModel proportyModel2 = new ProportyModel();
        proportyModel2.setId(proportyModel1.getId());
        assertThat(proportyModel1).isEqualTo(proportyModel2);
        proportyModel2.setId("id2");
        assertThat(proportyModel1).isNotEqualTo(proportyModel2);
        proportyModel1.setId(null);
        assertThat(proportyModel1).isNotEqualTo(proportyModel2);
    }
}
