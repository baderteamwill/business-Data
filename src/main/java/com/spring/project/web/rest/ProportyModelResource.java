package com.spring.project.web.rest;

import com.spring.project.domain.EntityModel;
import com.spring.project.domain.ProportyModel;
import com.spring.project.service.ProportyModelService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.spring.project.domain.ProportyModel}.
 */
@RestController
@RequestMapping("/api")
public class ProportyModelResource {

    private final Logger log = LoggerFactory.getLogger(ProportyModelResource.class);

    private static final String ENTITY_NAME = "proportyModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProportyModelService proportyModelService;

    public ProportyModelResource(ProportyModelService proportyModelService) {
        this.proportyModelService = proportyModelService;
    }

    /**
     * {@code POST  /proporty-models} : Create a new proportyModel.
     *
     * @param proportyModel the proportyModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proportyModel, or with status {@code 400 (Bad Request)} if the proportyModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proporty-models")
    public ResponseEntity<ProportyModel> createProportyModel(@RequestBody ProportyModel proportyModel) throws URISyntaxException {
        log.debug("REST request to save ProportyModel : {}", proportyModel);
        if (proportyModel.getId() != null) {
            throw new BadRequestAlertException("A new proportyModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProportyModel result = proportyModelService.save(proportyModel);
        return ResponseEntity.created(new URI("/api/proporty-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proporty-models} : Updates an existing proportyModel.
     *
     * @param proportyModel the proportyModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proportyModel,
     * or with status {@code 400 (Bad Request)} if the proportyModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proportyModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proporty-models")
    public ResponseEntity<ProportyModel> updateProportyModel(@RequestBody ProportyModel proportyModel) throws URISyntaxException {
        log.debug("REST request to update ProportyModel : {}", proportyModel);
        if (proportyModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProportyModel result = proportyModelService.save(proportyModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proportyModel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proporty-models} : get all the proportyModels.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proportyModels in body.
     */
    @GetMapping("/proporty-models")
    public List<ProportyModel> getAllProportyModels(@RequestParam(required = false) String filter) {
        if ("proportydata-is-null".equals(filter)) {
            log.debug("REST request to get all ProportyModels where proportyData is null");
            return proportyModelService.findAllWhereProportyDataIsNull();
        }
        log.debug("REST request to get all ProportyModels");
        return proportyModelService.findAll();
    }

    /**
     * {@code GET  /proporty-models/:id} : get the "id" proportyModel.
     *
     * @param id the id of the proportyModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proportyModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proporty-models/{id}")
    public ResponseEntity<ProportyModel> getProportyModel(@PathVariable String id) {
        log.debug("REST request to get ProportyModel : {}", id);
        Optional<ProportyModel> proportyModel = proportyModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proportyModel);
    }

    /**
     * {@code DELETE  /proporty-models/:id} : delete the "id" proportyModel.
     *
     * @param id the id of the proportyModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proporty-models/{id}")
    public ResponseEntity<Void> deleteProportyModel(@PathVariable String id) {
        log.debug("REST request to delete ProportyModel : {}", id);
        proportyModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
    
    
    
    
    
    
    @GetMapping("/proporty-models/trouver/{id}")
    public List<ProportyModel> trouver(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
        List<ProportyModel> entityModel = proportyModelService.trouverpropentity(id);
        return  entityModel;
    }

    
    
    @GetMapping("/proporty-models/proportiesPerInstance/{id}")
    public List<ProportyModel> proportiesPerInstance(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
        List<ProportyModel> entityModel = proportyModelService.proportiesPerInstance(id);
        return  entityModel;
    }

    @GetMapping("/proporty-models/defineType/{id}")
    public String defineType(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
       System.out.println("mmmmmmmmmmm"+proportyModelService.defineType(id));
        return  proportyModelService.defineType(id);
        
    }
    
    
    
    
    
    
}
