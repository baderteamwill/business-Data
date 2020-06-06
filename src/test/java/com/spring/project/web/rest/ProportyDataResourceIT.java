package com.spring.project.web.rest;

import com.spring.project.BusinessDataApp;
import com.spring.project.domain.ProportyData;
import com.spring.project.repository.ProportyDataRepository;
import com.spring.project.service.ProportyDataService;
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
 * Integration tests for the {@link ProportyDataResource} REST controller.
 */
@SpringBootTest(classes = BusinessDataApp.class)
public class ProportyDataResourceIT {

    private static final String DEFAULT_PROPORTY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PROPORTY_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProportyDataRepository proportyDataRepository;

    @Autowired
    private ProportyDataService proportyDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProportyDataMockMvc;

    private ProportyData proportyData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProportyDataResource proportyDataResource = new ProportyDataResource(proportyDataService);
        this.restProportyDataMockMvc = MockMvcBuilders.standaloneSetup(proportyDataResource)
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
    public static ProportyData createEntity() {
        ProportyData proportyData = new ProportyData()
            .proportyValue(DEFAULT_PROPORTY_VALUE);
        return proportyData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProportyData createUpdatedEntity() {
        ProportyData proportyData = new ProportyData()
            .proportyValue(UPDATED_PROPORTY_VALUE);
        return proportyData;
    }

    @BeforeEach
    public void initTest() {
        proportyDataRepository.deleteAll();
        proportyData = createEntity();
    }

    @Test
    public void createProportyData() throws Exception {
        int databaseSizeBeforeCreate = proportyDataRepository.findAll().size();

        // Create the ProportyData
        restProportyDataMockMvc.perform(post("/api/proporty-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proportyData)))
            .andExpect(status().isCreated());

        // Validate the ProportyData in the database
        List<ProportyData> proportyDataList = proportyDataRepository.findAll();
        assertThat(proportyDataList).hasSize(databaseSizeBeforeCreate + 1);
        ProportyData testProportyData = proportyDataList.get(proportyDataList.size() - 1);
        assertThat(testProportyData.getProportyValue()).isEqualTo(DEFAULT_PROPORTY_VALUE);
    }

    @Test
    public void createProportyDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proportyDataRepository.findAll().size();

        // Create the ProportyData with an existing ID
        proportyData.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProportyDataMockMvc.perform(post("/api/proporty-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proportyData)))
            .andExpect(status().isBadRequest());

        // Validate the ProportyData in the database
        List<ProportyData> proportyDataList = proportyDataRepository.findAll();
        assertThat(proportyDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllProportyData() throws Exception {
        // Initialize the database
        proportyDataRepository.save(proportyData);

        // Get all the proportyDataList
        restProportyDataMockMvc.perform(get("/api/proporty-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proportyData.getId())))
            .andExpect(jsonPath("$.[*].proportyValue").value(hasItem(DEFAULT_PROPORTY_VALUE)));
    }
    
    @Test
    public void getProportyData() throws Exception {
        // Initialize the database
        proportyDataRepository.save(proportyData);

        // Get the proportyData
        restProportyDataMockMvc.perform(get("/api/proporty-data/{id}", proportyData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proportyData.getId()))
            .andExpect(jsonPath("$.proportyValue").value(DEFAULT_PROPORTY_VALUE));
    }

    @Test
    public void getNonExistingProportyData() throws Exception {
        // Get the proportyData
        restProportyDataMockMvc.perform(get("/api/proporty-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProportyData() throws Exception {
        // Initialize the database
        proportyDataService.save(proportyData);

        int databaseSizeBeforeUpdate = proportyDataRepository.findAll().size();

        // Update the proportyData
        ProportyData updatedProportyData = proportyDataRepository.findById(proportyData.getId()).get();
        updatedProportyData
            .proportyValue(UPDATED_PROPORTY_VALUE);

        restProportyDataMockMvc.perform(put("/api/proporty-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProportyData)))
            .andExpect(status().isOk());

        // Validate the ProportyData in the database
        List<ProportyData> proportyDataList = proportyDataRepository.findAll();
        assertThat(proportyDataList).hasSize(databaseSizeBeforeUpdate);
        ProportyData testProportyData = proportyDataList.get(proportyDataList.size() - 1);
        assertThat(testProportyData.getProportyValue()).isEqualTo(UPDATED_PROPORTY_VALUE);
    }

    @Test
    public void updateNonExistingProportyData() throws Exception {
        int databaseSizeBeforeUpdate = proportyDataRepository.findAll().size();

        // Create the ProportyData

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProportyDataMockMvc.perform(put("/api/proporty-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proportyData)))
            .andExpect(status().isBadRequest());

        // Validate the ProportyData in the database
        List<ProportyData> proportyDataList = proportyDataRepository.findAll();
        assertThat(proportyDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProportyData() throws Exception {
        // Initialize the database
        proportyDataService.save(proportyData);

        int databaseSizeBeforeDelete = proportyDataRepository.findAll().size();

        // Delete the proportyData
        restProportyDataMockMvc.perform(delete("/api/proporty-data/{id}", proportyData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProportyData> proportyDataList = proportyDataRepository.findAll();
        assertThat(proportyDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
