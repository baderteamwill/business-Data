package com.spring.project.web.rest;

import com.spring.project.BusinessDataApp;
import com.spring.project.domain.InstanceRelation;
import com.spring.project.repository.InstanceRelationRepository;
import com.spring.project.service.InstanceRelationService;
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
 * Integration tests for the {@link InstanceRelationResource} REST controller.
 */
@SpringBootTest(classes = BusinessDataApp.class)
public class InstanceRelationResourceIT {

    @Autowired
    private InstanceRelationRepository instanceRelationRepository;

    @Autowired
    private InstanceRelationService instanceRelationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restInstanceRelationMockMvc;

    private InstanceRelation instanceRelation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstanceRelationResource instanceRelationResource = new InstanceRelationResource(instanceRelationService);
        this.restInstanceRelationMockMvc = MockMvcBuilders.standaloneSetup(instanceRelationResource)
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
    public static InstanceRelation createEntity() {
        InstanceRelation instanceRelation = new InstanceRelation();
        return instanceRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstanceRelation createUpdatedEntity() {
        InstanceRelation instanceRelation = new InstanceRelation();
        return instanceRelation;
    }

    @BeforeEach
    public void initTest() {
        instanceRelationRepository.deleteAll();
        instanceRelation = createEntity();
    }

    @Test
    public void createInstanceRelation() throws Exception {
        int databaseSizeBeforeCreate = instanceRelationRepository.findAll().size();

        // Create the InstanceRelation
        restInstanceRelationMockMvc.perform(post("/api/instance-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceRelation)))
            .andExpect(status().isCreated());

        // Validate the InstanceRelation in the database
        List<InstanceRelation> instanceRelationList = instanceRelationRepository.findAll();
        assertThat(instanceRelationList).hasSize(databaseSizeBeforeCreate + 1);
        InstanceRelation testInstanceRelation = instanceRelationList.get(instanceRelationList.size() - 1);
    }

    @Test
    public void createInstanceRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instanceRelationRepository.findAll().size();

        // Create the InstanceRelation with an existing ID
        instanceRelation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstanceRelationMockMvc.perform(post("/api/instance-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceRelation)))
            .andExpect(status().isBadRequest());

        // Validate the InstanceRelation in the database
        List<InstanceRelation> instanceRelationList = instanceRelationRepository.findAll();
        assertThat(instanceRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllInstanceRelations() throws Exception {
        // Initialize the database
        instanceRelationRepository.save(instanceRelation);

        // Get all the instanceRelationList
        restInstanceRelationMockMvc.perform(get("/api/instance-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instanceRelation.getId())));
    }
    
    @Test
    public void getInstanceRelation() throws Exception {
        // Initialize the database
        instanceRelationRepository.save(instanceRelation);

        // Get the instanceRelation
        restInstanceRelationMockMvc.perform(get("/api/instance-relations/{id}", instanceRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instanceRelation.getId()));
    }

    @Test
    public void getNonExistingInstanceRelation() throws Exception {
        // Get the instanceRelation
        restInstanceRelationMockMvc.perform(get("/api/instance-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInstanceRelation() throws Exception {
        // Initialize the database
        instanceRelationService.save(instanceRelation);

        int databaseSizeBeforeUpdate = instanceRelationRepository.findAll().size();

        // Update the instanceRelation
        InstanceRelation updatedInstanceRelation = instanceRelationRepository.findById(instanceRelation.getId()).get();

        restInstanceRelationMockMvc.perform(put("/api/instance-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstanceRelation)))
            .andExpect(status().isOk());

        // Validate the InstanceRelation in the database
        List<InstanceRelation> instanceRelationList = instanceRelationRepository.findAll();
        assertThat(instanceRelationList).hasSize(databaseSizeBeforeUpdate);
        InstanceRelation testInstanceRelation = instanceRelationList.get(instanceRelationList.size() - 1);
    }

    @Test
    public void updateNonExistingInstanceRelation() throws Exception {
        int databaseSizeBeforeUpdate = instanceRelationRepository.findAll().size();

        // Create the InstanceRelation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstanceRelationMockMvc.perform(put("/api/instance-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceRelation)))
            .andExpect(status().isBadRequest());

        // Validate the InstanceRelation in the database
        List<InstanceRelation> instanceRelationList = instanceRelationRepository.findAll();
        assertThat(instanceRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteInstanceRelation() throws Exception {
        // Initialize the database
        instanceRelationService.save(instanceRelation);

        int databaseSizeBeforeDelete = instanceRelationRepository.findAll().size();

        // Delete the instanceRelation
        restInstanceRelationMockMvc.perform(delete("/api/instance-relations/{id}", instanceRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstanceRelation> instanceRelationList = instanceRelationRepository.findAll();
        assertThat(instanceRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
