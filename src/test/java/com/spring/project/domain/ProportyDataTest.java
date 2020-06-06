package com.spring.project.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spring.project.web.rest.TestUtil;

public class ProportyDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProportyData.class);
        ProportyData proportyData1 = new ProportyData();
        proportyData1.setId("id1");
        ProportyData proportyData2 = new ProportyData();
        proportyData2.setId(proportyData1.getId());
        assertThat(proportyData1).isEqualTo(proportyData2);
        proportyData2.setId("id2");
        assertThat(proportyData1).isNotEqualTo(proportyData2);
        proportyData1.setId(null);
        assertThat(proportyData1).isNotEqualTo(proportyData2);
    }
}
