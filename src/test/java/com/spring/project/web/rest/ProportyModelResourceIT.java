package com.spring.project.web.rest;

import com.spring.project.BusinessDataApp;
import com.spring.project.domain.ProportyModel;
import com.spring.project.repository.ProportyModelRepository;
import com.spring.project.service.ProportyModelService;
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

import com.spring.project.domain.enumeration.Type;
/**
 * Integration tests for the {@link ProportyModelResource} REST controller.
 */
@SpringBootTest(classes = BusinessDataApp.class)
public class ProportyModelResourceIT {

    private static final String DEFAULT_PROPORTY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROPORTY_NAME = "BBBBBBBBBB";

    private static final Type DEFAULT_PROPORTY_TYPE = Type.INT;
    private static final Type UPDATED_PROPORTY_TYPE = Type.FLOAT;

    @Autowired
    private ProportyModelRepository proportyModelRepository;

    @Autowired
    private ProportyModelService proportyModelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProportyModelMockMvc;

    private ProportyModel proportyModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProportyModelResource proportyModelResource = new ProportyModelResource(proportyModelService);
        this.restProportyModelMockMvc = MockMvcBuilders.standaloneSetup(proportyModelResource)
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
    public static ProportyModel createEntity() {
        ProportyModel proportyModel = new ProportyModel()
            .proportyName(DEFAULT_PROPORTY_NAME)
            .proportyType(DEFAULT_PROPORTY_TYPE);
        return proportyModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProportyModel createUpdatedEntity() {
        ProportyModel proportyModel = new ProportyModel()
            .proportyName(UPDATED_PROPORTY_NAME)
            .proportyType(UPDATED_PROPORTY_TYPE);
        return proportyModel;
    }

    @BeforeEach
    public void initTest() {
        proportyModelRepository.deleteAll();
        proportyModel = createEntity();
    }

    @Test
    public void createProportyModel() throws Exception {
        int databaseSizeBeforeCreate = proportyModelRepository.findAll().size();

        // Create the ProportyModel
        restProportyModelMockMvc.perform(post("/api/proporty-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proportyModel)))
            .andExpect(status().isCreated());

        // Validate the ProportyModel in the database
        List<ProportyModel> proportyModelList = proportyModelRepository.findAll();
        assertThat(proportyModelList).hasSize(databaseSizeBeforeCreate + 1);
        ProportyModel testProportyModel = proportyModelList.get(proportyModelList.size() - 1);
        assertThat(testProportyModel.getProportyName()).isEqualTo(DEFAULT_PROPORTY_NAME);
        assertThat(testProportyModel.getProportyType()).isEqualTo(DEFAULT_PROPORTY_TYPE);
    }

    @Test
    public void createProportyModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proportyModelRepository.findAll().size();

        // Create the ProportyModel with an existing ID
        proportyModel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProportyModelMockMvc.perform(post("/api/proporty-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proportyModel)))
            .andExpect(status().isBadRequest());

        // Validate the ProportyModel in the database
        List<ProportyModel> proportyModelList = proportyModelRepository.findAll();
        assertThat(proportyModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllProportyModels() throws Exception {
        // Initialize the database
        proportyModelRepository.save(proportyModel);

        // Get all the proportyModelList
        restProportyModelMockMvc.perform(get("/api/proporty-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proportyModel.getId())))
            .andExpect(jsonPath("$.[*].proportyName").value(hasItem(DEFAULT_PROPORTY_NAME)))
            .andExpect(jsonPath("$.[*].proportyType").value(hasItem(DEFAULT_PROPORTY_TYPE.toString())));
    }
    
    @Test
    public void getProportyModel() throws Exception {
        // Initialize the database
        proportyModelRepository.save(proportyModel);

        // Get the proportyModel
        restProportyModelMockMvc.perform(get("/api/proporty-models/{id}", proportyModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proportyModel.getId()))
            .andExpect(jsonPath("$.proportyName").value(DEFAULT_PROPORTY_NAME))
            .andExpect(jsonPath("$.proportyType").value(DEFAULT_PROPORTY_TYPE.toString()));
    }

    @Test
    public void getNonExistingProportyModel() throws Exception {
        // Get the proportyModel
        restProportyModelMockMvc.perform(get("/api/proporty-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProportyModel() throws Exception {
        // Initialize the database
        proportyModelService.save(proportyModel);

        int databaseSizeBeforeUpdate = proportyModelRepository.findAll().size();

        // Update the proportyModel
        ProportyModel updatedProportyModel = proportyModelRepository.findById(proportyModel.getId()).get();
        updatedProportyModel
            .proportyName(UPDATED_PROPORTY_NAME)
            .proportyType(UPDATED_PROPORTY_TYPE);

        restProportyModelMockMvc.perform(put("/api/proporty-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProportyModel)))
            .andExpect(status().isOk());

        // Validate the ProportyModel in the database
        List<ProportyModel> proportyModelList = proportyModelRepository.findAll();
        assertThat(proportyModelList).hasSize(databaseSizeBeforeUpdate);
        ProportyModel testProportyModel = proportyModelList.get(proportyModelList.size() - 1);
        assertThat(testProportyModel.getProportyName()).isEqualTo(UPDATED_PROPORTY_NAME);
        assertThat(testProportyModel.getProportyType()).isEqualTo(UPDATED_PROPORTY_TYPE);
    }

    @Test
    public void updateNonExistingProportyModel() throws Exception {
        int databaseSizeBeforeUpdate = proportyModelRepository.findAll().size();

        // Create the ProportyModel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProportyModelMockMvc.perform(put("/api/proporty-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proportyModel)))
            .andExpect(status().isBadRequest());

        // Validate the ProportyModel in the database
        List<ProportyModel> proportyModelList = proportyModelRepository.findAll();
        assertThat(proportyModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProportyModel() throws Exception {
        // Initialize the database
        proportyModelService.save(proportyModel);

        int databaseSizeBeforeDelete = proportyModelRepository.findAll().size();

        // Delete the proportyModel
        restProportyModelMockMvc.perform(delete("/api/proporty-models/{id}", proportyModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProportyModel> proportyModelList = proportyModelRepository.findAll();
        assertThat(proportyModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
