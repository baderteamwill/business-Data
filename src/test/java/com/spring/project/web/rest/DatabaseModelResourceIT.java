package com.spring.project.web.rest;

import com.spring.project.BusinessDataApp;
import com.spring.project.domain.DatabaseModel;
import com.spring.project.repository.DatabaseModelRepository;
import com.spring.project.service.DatabaseModelService;
import com.spring.project.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.List;

import static com.spring.project.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DatabaseModelResource} REST controller.
 */
@SpringBootTest(classes = BusinessDataApp.class)
public class DatabaseModelResourceIT {

    private static final String DEFAULT_DATABASE_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_DATABASE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DatabaseModelRepository databaseModelRepository;

    @Autowired
    private DatabaseModelService databaseModelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDatabaseModelMockMvc;

    private DatabaseModel databaseModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatabaseModelResource databaseModelResource = new DatabaseModelResource(databaseModelService);
        this.restDatabaseModelMockMvc = MockMvcBuilders.standaloneSetup(databaseModelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatabaseModel createEntity() {
        DatabaseModel databaseModel = new DatabaseModel()
            .databaseModel(DEFAULT_DATABASE_MODEL)
            .databaseDescription(DEFAULT_DATABASE_DESCRIPTION);
        return databaseModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatabaseModel createUpdatedEntity() {
        DatabaseModel databaseModel = new DatabaseModel()
            .databaseModel(UPDATED_DATABASE_MODEL)
            .databaseDescription(UPDATED_DATABASE_DESCRIPTION);
        return databaseModel;
    }

    @BeforeEach
    public void initTest() {
        databaseModelRepository.deleteAll();
        databaseModel = createEntity();
    }

    @Test
    public void createDatabaseModel() throws Exception {
        int databaseSizeBeforeCreate = databaseModelRepository.findAll().size();

        // Create the DatabaseModel
        restDatabaseModelMockMvc.perform(post("/api/database-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(databaseModel)))
            .andExpect(status().isCreated());

        // Validate the DatabaseModel in the database
        List<DatabaseModel> databaseModelList = databaseModelRepository.findAll();
        assertThat(databaseModelList).hasSize(databaseSizeBeforeCreate + 1);
        DatabaseModel testDatabaseModel = databaseModelList.get(databaseModelList.size() - 1);
        assertThat(testDatabaseModel.getDatabaseModel()).isEqualTo(DEFAULT_DATABASE_MODEL);
        assertThat(testDatabaseModel.getDatabaseDescription()).isEqualTo(DEFAULT_DATABASE_DESCRIPTION);
    }

    @Test
    public void createDatabaseModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = databaseModelRepository.findAll().size();

        // Create the DatabaseModel with an existing ID
        databaseModel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatabaseModelMockMvc.perform(post("/api/database-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(databaseModel)))
            .andExpect(status().isBadRequest());

        // Validate the DatabaseModel in the database
        List<DatabaseModel> databaseModelList = databaseModelRepository.findAll();
        assertThat(databaseModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllDatabaseModels() throws Exception {
        // Initialize the database
        databaseModelRepository.save(databaseModel);

        // Get all the databaseModelList
        restDatabaseModelMockMvc.perform(get("/api/database-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(databaseModel.getId())))
            .andExpect(jsonPath("$.[*].databaseModel").value(hasItem(DEFAULT_DATABASE_MODEL)))
            .andExpect(jsonPath("$.[*].databaseDescription").value(hasItem(DEFAULT_DATABASE_DESCRIPTION)));
    }
    
    @Test
    public void getDatabaseModel() throws Exception {
        // Initialize the database
        databaseModelRepository.save(databaseModel);

        // Get the databaseModel
        restDatabaseModelMockMvc.perform(get("/api/database-models/{id}", databaseModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(databaseModel.getId()))
            .andExpect(jsonPath("$.databaseModel").value(DEFAULT_DATABASE_MODEL))
            .andExpect(jsonPath("$.databaseDescription").value(DEFAULT_DATABASE_DESCRIPTION));
    }

    @Test
    public void getNonExistingDatabaseModel() throws Exception {
        // Get the databaseModel
        restDatabaseModelMockMvc.perform(get("/api/database-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDatabaseModel() throws Exception {
        // Initialize the database
        databaseModelService.save(databaseModel);

        int databaseSizeBeforeUpdate = databaseModelRepository.findAll().size();

        // Update the databaseModel
        DatabaseModel updatedDatabaseModel = databaseModelRepository.findById(databaseModel.getId()).get();
        updatedDatabaseModel
            .databaseModel(UPDATED_DATABASE_MODEL)
            .databaseDescription(UPDATED_DATABASE_DESCRIPTION);

        restDatabaseModelMockMvc.perform(put("/api/database-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDatabaseModel)))
            .andExpect(status().isOk());

        // Validate the DatabaseModel in the database
        List<DatabaseModel> databaseModelList = databaseModelRepository.findAll();
        assertThat(databaseModelList).hasSize(databaseSizeBeforeUpdate);
        DatabaseModel testDatabaseModel = databaseModelList.get(databaseModelList.size() - 1);
        assertThat(testDatabaseModel.getDatabaseModel()).isEqualTo(UPDATED_DATABASE_MODEL);
        assertThat(testDatabaseModel.getDatabaseDescription()).isEqualTo(UPDATED_DATABASE_DESCRIPTION);
    }

    @Test
    public void updateNonExistingDatabaseModel() throws Exception {
        int databaseSizeBeforeUpdate = databaseModelRepository.findAll().size();

        // Create the DatabaseModel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatabaseModelMockMvc.perform(put("/api/database-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(databaseModel)))
            .andExpect(status().isBadRequest());

        // Validate the DatabaseModel in the database
        List<DatabaseModel> databaseModelList = databaseModelRepository.findAll();
        assertThat(databaseModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDatabaseModel() throws Exception {
        // Initialize the database
        databaseModelService.save(databaseModel);

        int databaseSizeBeforeDelete = databaseModelRepository.findAll().size();

        // Delete the databaseModel
        restDatabaseModelMockMvc.perform(delete("/api/database-models/{id}", databaseModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatabaseModel> databaseModelList = databaseModelRepository.findAll();
        assertThat(databaseModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
