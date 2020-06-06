package com.spring.project.web.rest;

import com.spring.project.domain.ProportyData;
import com.spring.project.service.ProportyDataService;
import com.spring.project.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.spring.project.domain.ProportyData}.
 */
@RestController
@RequestMapping("/api")
public class ProportyDataResource {

    private final Logger log = LoggerFactory.getLogger(ProportyDataResource.class);

    private static final String ENTITY_NAME = "proportyData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProportyDataService proportyDataService;

    public ProportyDataResource(ProportyDataService proportyDataService) {
        this.proportyDataService = proportyDataService;
    }

    /**
     * {@code POST  /proporty-data} : Create a new proportyData.
     *
     * @param proportyData the proportyData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proportyData, or with status {@code 400 (Bad Request)} if the proportyData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proporty-data")
    public ResponseEntity<ProportyData> createProportyData(@RequestBody ProportyData proportyData) throws URISyntaxException {
        log.debug("REST request to save ProportyData : {}", proportyData);
        if (proportyData.getId() != null) {
            throw new BadRequestAlertException("A new proportyData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProportyData result = proportyDataService.save(proportyData);
        return ResponseEntity.created(new URI("/api/proporty-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proporty-data} : Updates an existing proportyData.
     *
     * @param proportyData the proportyData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proportyData,
     * or with status {@code 400 (Bad Request)} if the proportyData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proportyData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proporty-data")
    public ResponseEntity<ProportyData> updateProportyData(@RequestBody ProportyData proportyData) throws URISyntaxException {
        log.debug("REST request to update ProportyData : {}", proportyData);
        if (proportyData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProportyData result = proportyDataService.save(proportyData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proportyData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proporty-data} : get all the proportyData.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proportyData in body.
     */
    @GetMapping("/proporty-data")
    public List<ProportyData> getAllProportyData() {
        log.debug("REST request to get all ProportyData");
        return proportyDataService.findAll();
    }

    /**
     * {@code GET  /proporty-data/:id} : get the "id" proportyData.
     *
     * @param id the id of the proportyData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proportyData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proporty-data/{id}")
    public ResponseEntity<ProportyData> getProportyData(@PathVariable String id) {
        log.debug("REST request to get ProportyData : {}", id);
        Optional<ProportyData> proportyData = proportyDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proportyData);
    }

    /**
     * {@code DELETE  /proporty-data/:id} : delete the "id" proportyData.
     *
     * @param id the id of the proportyData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proporty-data/{id}")
    public ResponseEntity<Void> deleteProportyData(@PathVariable String id) {
        log.debug("REST request to delete ProportyData : {}", id);
        proportyDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
    
    
    
    @GetMapping("/proporty-data/trouver/{id}")
    public List<ProportyData> trouver(@PathVariable String id) {
       
      List<ProportyData> proportyData = new ArrayList<ProportyData>();
      proportyData=proportyDataService.trouverproporties(id);
        return proportyData ;
    }

    
    
    

    
    @GetMapping("/proporty-data/instanceDetails/{id}")
    public List<ProportyData> instancedetails(@PathVariable String id) {
       
      List<ProportyData> proportyData = new ArrayList<ProportyData>();
      proportyData=proportyDataService.proportiesDetails(id);
        return proportyData ;
    }

    
    
    
    
    
}
