package com.spring.project.web.rest;

import com.spring.project.BusinessDataApp;
import com.spring.project.domain.EntityRelation;
import com.spring.project.repository.EntityRelationRepository;
import com.spring.project.service.EntityRelationService;
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

import com.spring.project.domain.enumeration.RelationType;
/**
 * Integration tests for the {@link EntityRelationResource} REST controller.
 */
@SpringBootTest(classes = BusinessDataApp.class)
public class EntityRelationResourceIT {

    private static final RelationType DEFAULT_RELATION = RelationType.ONE_TO_ONE;
    private static final RelationType UPDATED_RELATION = RelationType.ONE_TO_MANY;

    @Autowired
    private EntityRelationRepository entityRelationRepository;

    @Autowired
    private EntityRelationService entityRelationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restEntityRelationMockMvc;

    private EntityRelation entityRelation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntityRelationResource entityRelationResource = new EntityRelationResource(entityRelationService);
        this.restEntityRelationMockMvc = MockMvcBuilders.standaloneSetup(entityRelationResource)
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
    public static EntityRelation createEntity() {
        EntityRelation entityRelation = new EntityRelation()
            .relation(DEFAULT_RELATION);
        return entityRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityRelation createUpdatedEntity() {
        EntityRelation entityRelation = new EntityRelation()
            .relation(UPDATED_RELATION);
        return entityRelation;
    }

    @BeforeEach
    public void initTest() {
        entityRelationRepository.deleteAll();
        entityRelation = createEntity();
    }

    @Test
    public void createEntityRelation() throws Exception {
        int databaseSizeBeforeCreate = entityRelationRepository.findAll().size();

        // Create the EntityRelation
        restEntityRelationMockMvc.perform(post("/api/entity-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityRelation)))
            .andExpect(status().isCreated());

        // Validate the EntityRelation in the database
        List<EntityRelation> entityRelationList = entityRelationRepository.findAll();
        assertThat(entityRelationList).hasSize(databaseSizeBeforeCreate + 1);
        EntityRelation testEntityRelation = entityRelationList.get(entityRelationList.size() - 1);
        assertThat(testEntityRelation.getRelation()).isEqualTo(DEFAULT_RELATION);
    }

    @Test
    public void createEntityRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entityRelationRepository.findAll().size();

        // Create the EntityRelation with an existing ID
        entityRelation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityRelationMockMvc.perform(post("/api/entity-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityRelation)))
            .andExpect(status().isBadRequest());

        // Validate the EntityRelation in the database
        List<EntityRelation> entityRelationList = entityRelationRepository.findAll();
        assertThat(entityRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllEntityRelations() throws Exception {
        // Initialize the database
        entityRelationRepository.save(entityRelation);

        // Get all the entityRelationList
        restEntityRelationMockMvc.perform(get("/api/entity-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityRelation.getId())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())));
    }
    
    @Test
    public void getEntityRelation() throws Exception {
        // Initialize the database
        entityRelationRepository.save(entityRelation);

        // Get the entityRelation
        restEntityRelationMockMvc.perform(get("/api/entity-relations/{id}", entityRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entityRelation.getId()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()));
    }

    @Test
    public void getNonExistingEntityRelation() throws Exception {
        // Get the entityRelation
        restEntityRelationMockMvc.perform(get("/api/entity-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEntityRelation() throws Exception {
        // Initialize the database
        entityRelationService.save(entityRelation);

        int databaseSizeBeforeUpdate = entityRelationRepository.findAll().size();

        // Update the entityRelation
        EntityRelation updatedEntityRelation = entityRelationRepository.findById(entityRelation.getId()).get();
        updatedEntityRelation
            .relation(UPDATED_RELATION);

        restEntityRelationMockMvc.perform(put("/api/entity-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntityRelation)))
            .andExpect(status().isOk());

        // Validate the EntityRelation in the database
        List<EntityRelation> entityRelationList = entityRelationRepository.findAll();
        assertThat(entityRelationList).hasSize(databaseSizeBeforeUpdate);
        EntityRelation testEntityRelation = entityRelationList.get(entityRelationList.size() - 1);
        assertThat(testEntityRelation.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    public void updateNonExistingEntityRelation() throws Exception {
        int databaseSizeBeforeUpdate = entityRelationRepository.findAll().size();

        // Create the EntityRelation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityRelationMockMvc.perform(put("/api/entity-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entityRelation)))
            .andExpect(status().isBadRequest());

        // Validate the EntityRelation in the database
        List<EntityRelation> entityRelationList = entityRelationRepository.findAll();
        assertThat(entityRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEntityRelation() throws Exception {
        // Initialize the database
        entityRelationService.save(entityRelation);

        int databaseSizeBeforeDelete = entityRelationRepository.findAll().size();

        // Delete the entityRelation
        restEntityRelationMockMvc.perform(delete("/api/entity-relations/{id}", entityRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntityRelation> entityRelationList = entityRelationRepository.findAll();
        assertThat(entityRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
