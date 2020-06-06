package com.spring.project.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.spring.project.web.rest.TestUtil;

public class DatabaseModelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatabaseModel.class);
        DatabaseModel databaseModel1 = new DatabaseModel();
        databaseModel1.setId("id1");
        DatabaseModel databaseModel2 = new DatabaseModel();
        databaseModel2.setId(databaseModel1.getId());
        assertThat(databaseModel1).isEqualTo(databaseModel2);
        databaseModel2.setId("id2");
        assertThat(databaseModel1).isNotEqualTo(databaseModel2);
        databaseModel1.setId(null);
        assertThat(databaseModel1).isNotEqualTo(databaseModel2);
    }
}
