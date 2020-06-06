package com.spring.project.web.rest;

import com.spring.project.BusinessDataApp;
import com.spring.project.domain.EntityModel;
import com.spring.project.repository.EntityModelRepository;
import com.spring.project.service.EntityModelService;
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
 * Integration tests for the {@link EntityModelResource} REST controller.
 */
@SpringBootTest(classes = BusinessDataApp.class)
public class EntityModelResourceIT {

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EntityModelRepository entityModelRepository;

    @Autowired
    private EntityModelService entityModelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restEntityModelMockMvc;

    private EntityModel entityModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntityModelResource entityModelResource = new EntityModelResource(entityModelService);
        this.restEntityModelMockMvc = MockMvcBuilders.standaloneSetup(entityModelResource)
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
    public static EntityModel createEntity() {
        EntityModel entityModel = new EntityModel()
            .entityName(DEFAULT_ENTITY_NAME)
            .entityDescription(DEFAULT_ENTITY_DESCRIPTION);
        return entityModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityModel createUpdatedEntity() {
        EntityModel entityModel = new EntityModel()
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION);
        return entityModel;
    }

    @BeforeEach
    public void initTest() {
        entityModelRepository.deleteAll();
        entityModel = createEntity();
    }

    @Test
    public void createEntityModel() throws Exception {
        int databaseSizeBeforeCreate = entityModelRepository.findAll().size();

        // Create the EntityModel
        restEntityModelMockMvc.perform(post("/api/entity-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityModel)))
            .andExpect(status().isCreated());

        // Validate the EntityModel in the database
        List<EntityModel> entityModelList = entityModelRepository.findAll();
        assertThat(entityModelList).hasSize(databaseSizeBeforeCreate + 1);
        EntityModel testEntityModel = entityModelList.get(entityModelList.size() - 1);
        assertThat(testEntityModel.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testEntityModel.getEntityDescription()).isEqualTo(DEFAULT_ENTITY_DESCRIPTION);
    }

    @Test
    public void createEntityModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entityModelRepository.findAll().size();

        // Create the EntityModel with an existing ID
        entityModel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityModelMockMvc.perform(post("/api/entity-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityModel)))
            .andExpect(status().isBadRequest());

        // Validate the EntityModel in the database
        List<EntityModel> entityModelList = entityModelRepository.findAll();
        assertThat(entityModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllEntityModels() throws Exception {
        // Initialize the database
        entityModelRepository.save(entityModel);

        // Get all the entityModelList
        restEntityModelMockMvc.perform(get("/api/entity-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityModel.getId())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)));
    }
    
    @Test
    public void getEntityModel() throws Exception {
        // Initialize the database
        entityModelRepository.save(entityModel);

        // Get the entityModel
        restEntityModelMockMvc.perform(get("/api/entity-models/{id}", entityModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entityModel.getId()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.entityDescription").value(DEFAULT_ENTITY_DESCRIPTION));
    }

    @Test
    public void getNonExistingEntityModel() throws Exception {
        // Get the entityModel
        restEntityModelMockMvc.perform(get("/api/entity-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEntityModel() throws Exception {
        // Initialize the database
        entityModelService.save(entityModel);

        int databaseSizeBeforeUpdate = entityModelRepository.findAll().size();

        // Update the entityModel
        EntityModel updatedEntityModel = entityModelRepository.findById(entityModel.getId()).get();
        updatedEntityModel
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION);

        restEntityModelMockMvc.perform(put("/api/entity-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntityModel)))
            .andExpect(status().isOk());

        // Validate the EntityModel in the database
        List<EntityModel> entityModelList = entityModelRepository.findAll();
        assertThat(entityModelList).hasSize(databaseSizeBeforeUpdate);
        EntityModel testEntityModel = entityModelList.get(entityModelList.size() - 1);
        assertThat(testEntityModel.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testEntityModel.getEntityDescription()).isEqualTo(UPDATED_ENTITY_DESCRIPTION);
    }

    @Test
    public void updateNonExistingEntityModel() throws Exception {
        int databaseSizeBeforeUpdate = entityModelRepository.findAll().size();

        // Create the EntityModel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityModelMockMvc.perform(put("/api/entity-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityModel)))
            .andExpect(status().isBadRequest());

        // Validate the EntityModel in the database
        List<EntityModel> entityModelList = entityModelRepository.findAll();
        assertThat(entityModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEntityModel() throws Exception {
        // Initialize the database
        entityModelService.save(entityModel);

        int databaseSizeBeforeDelete = entityModelRepository.findAll().size();

        // Delete the entityModel
        restEntityModelMockMvc.perform(delete("/api/entity-models/{id}", entityModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntityModel> entityModelList = entityModelRepository.findAll();
        assertThat(entityModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
