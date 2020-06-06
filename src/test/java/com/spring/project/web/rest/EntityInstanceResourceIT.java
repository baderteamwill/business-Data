package com.spring.project.web.rest;

import com.spring.project.BusinessDataApp;
import com.spring.project.domain.EntityInstance;
import com.spring.project.repository.EntityInstanceRepository;
import com.spring.project.service.EntityInstanceService;
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
 * Integration tests for the {@link EntityInstanceResource} REST controller.
 */
@SpringBootTest(classes = BusinessDataApp.class)
public class EntityInstanceResourceIT {

    private static final String DEFAULT_INSTANCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INSTANCE_NAME = "BBBBBBBBBB";

    @Autowired
    private EntityInstanceRepository entityInstanceRepository;

    @Autowired
    private EntityInstanceService entityInstanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restEntityInstanceMockMvc;

    private EntityInstance entityInstance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntityInstanceResource entityInstanceResource = new EntityInstanceResource(entityInstanceService);
        this.restEntityInstanceMockMvc = MockMvcBuilders.standaloneSetup(entityInstanceResource)
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
    public static EntityInstance createEntity() {
        EntityInstance entityInstance = new EntityInstance()
            .instanceName(DEFAULT_INSTANCE_NAME);
        return entityInstance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityInstance createUpdatedEntity() {
        EntityInstance entityInstance = new EntityInstance()
            .instanceName(UPDATED_INSTANCE_NAME);
        return entityInstance;
    }

    @BeforeEach
    public void initTest() {
        entityInstanceRepository.deleteAll();
        entityInstance = createEntity();
    }

    @Test
    public void createEntityInstance() throws Exception {
        int databaseSizeBeforeCreate = entityInstanceRepository.findAll().size();

        // Create the EntityInstance
        restEntityInstanceMockMvc.perform(post("/api/entity-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityInstance)))
            .andExpect(status().isCreated());

        // Validate the EntityInstance in the database
        List<EntityInstance> entityInstanceList = entityInstanceRepository.findAll();
        assertThat(entityInstanceList).hasSize(databaseSizeBeforeCreate + 1);
        EntityInstance testEntityInstance = entityInstanceList.get(entityInstanceList.size() - 1);
        assertThat(testEntityInstance.getInstanceName()).isEqualTo(DEFAULT_INSTANCE_NAME);
    }

    @Test
    public void createEntityInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entityInstanceRepository.findAll().size();

        // Create the EntityInstance with an existing ID
        entityInstance.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityInstanceMockMvc.perform(post("/api/entity-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityInstance)))
            .andExpect(status().isBadRequest());

        // Validate the EntityInstance in the database
        List<EntityInstance> entityInstanceList = entityInstanceRepository.findAll();
        assertThat(entityInstanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllEntityInstances() throws Exception {
        // Initialize the database
        entityInstanceRepository.save(entityInstance);

        // Get all the entityInstanceList
        restEntityInstanceMockMvc.perform(get("/api/entity-instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityInstance.getId())))
            .andExpect(jsonPath("$.[*].instanceName").value(hasItem(DEFAULT_INSTANCE_NAME)));
    }
    
    @Test
    public void getEntityInstance() throws Exception {
        // Initialize the database
        entityInstanceRepository.save(entityInstance);

        // Get the entityInstance
        restEntityInstanceMockMvc.perform(get("/api/entity-instances/{id}", entityInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entityInstance.getId()))
            .andExpect(jsonPath("$.instanceName").value(DEFAULT_INSTANCE_NAME));
    }

    @Test
    public void getNonExistingEntityInstance() throws Exception {
        // Get the entityInstance
        restEntityInstanceMockMvc.perform(get("/api/entity-instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEntityInstance() throws Exception {
        // Initialize the database
        entityInstanceService.save(entityInstance);

        int databaseSizeBeforeUpdate = entityInstanceRepository.findAll().size();

        // Update the entityInstance
        EntityInstance updatedEntityInstance = entityInstanceRepository.findById(entityInstance.getId()).get();
        updatedEntityInstance
            .instanceName(UPDATED_INSTANCE_NAME);

        restEntityInstanceMockMvc.perform(put("/api/entity-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntityInstance)))
            .andExpect(status().isOk());

        // Validate the EntityInstance in the database
        List<EntityInstance> entityInstanceList = entityInstanceRepository.findAll();
        assertThat(entityInstanceList).hasSize(databaseSizeBeforeUpdate);
        EntityInstance testEntityInstance = entityInstanceList.get(entityInstanceList.size() - 1);
        assertThat(testEntityInstance.getInstanceName()).isEqualTo(UPDATED_INSTANCE_NAME);
    }

    @Test
    public void updateNonExistingEntityInstance() throws Exception {
        int databaseSizeBeforeUpdate = entityInstanceRepository.findAll().size();

        // Create the EntityInstance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityInstanceMockMvc.perform(put("/api/entity-instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityInstance)))
            .andExpect(status().isBadRequest());

        // Validate the EntityInstance in the database
        List<EntityInstance> entityInstanceList = entityInstanceRepository.findAll();
        assertThat(entityInstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEntityInstance() throws Exception {
        // Initialize the database
        entityInstanceService.save(entityInstance);

        int databaseSizeBeforeDelete = entityInstanceRepository.findAll().size();

        // Delete the entityInstance
        restEntityInstanceMockMvc.perform(delete("/api/entity-instances/{id}", entityInstance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntityInstance> entityInstanceList = entityInstanceRepository.findAll();
        assertThat(entityInstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
