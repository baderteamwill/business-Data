package com.spring.project.web.rest;

import com.spring.project.domain.EntityModel;
import com.spring.project.service.EntityModelService;
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
 * REST controller for managing {@link com.spring.project.domain.EntityModel}.
 */
@RestController
@RequestMapping("/api")
public class EntityModelResource {

    private final Logger log = LoggerFactory.getLogger(EntityModelResource.class);

    private static final String ENTITY_NAME = "entityModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityModelService entityModelService;

    public EntityModelResource(EntityModelService entityModelService) {
        this.entityModelService = entityModelService;
    }

    /**
     * {@code POST  /entity-models} : Create a new entityModel.
     *
     * @param entityModel the entityModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityModel, or with status {@code 400 (Bad Request)} if the entityModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-models")
    public ResponseEntity<EntityModel> createEntityModel(@RequestBody EntityModel entityModel) throws URISyntaxException {
        log.debug("REST request to save EntityModel : {}", entityModel);
        if (entityModel.getId() != null) {
            throw new BadRequestAlertException("A new entityModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityModel result = entityModelService.save(entityModel);
        return ResponseEntity.created(new URI("/api/entity-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-models} : Updates an existing entityModel.
     *
     * @param entityModel the entityModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityModel,
     * or with status {@code 400 (Bad Request)} if the entityModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-models")
    public ResponseEntity<EntityModel> updateEntityModel(@RequestBody EntityModel entityModel) throws URISyntaxException {
        log.debug("REST request to update EntityModel : {}", entityModel);
        if (entityModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntityModel result = entityModelService.save(entityModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityModel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entity-models} : get all the entityModels.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityModels in body.
     */
    @GetMapping("/entity-models")
    public List<EntityModel> getAllEntityModels(@RequestParam(required = false) String filter) {
        if ("entityrelation-is-null".equals(filter)) {
            log.debug("REST request to get all EntityModels where entityRelation is null");
            return entityModelService.findAllWhereEntityRelationIsNull();
        }
        if ("entityrelation2-is-null".equals(filter)) {
            log.debug("REST request to get all EntityModels where entityRelation2 is null");
            return entityModelService.findAllWhereEntityRelation2IsNull();
        }
        log.debug("REST request to get all EntityModels");
        return entityModelService.findAll();
    }

    /**
     * {@code GET  /entity-models/:id} : get the "id" entityModel.
     *
     * @param id the id of the entityModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-models/{id}")
    public ResponseEntity<EntityModel> getEntityModel(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
        Optional<EntityModel> entityModel = entityModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entityModel);
    }

    /**
     * {@code DELETE  /entity-models/:id} : delete the "id" entityModel.
     *
     * @param id the id of the entityModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-models/{id}")
    public ResponseEntity<Void> deleteEntityModel(@PathVariable String id) {
        log.debug("REST request to delete EntityModel : {}", id);
        entityModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
    
    
    
    @GetMapping("/entity-models/trouver/{id}")
    public List<EntityModel> trouver(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
        List<EntityModel> entityModel = entityModelService.trouver(id);
        return  entityModel;
    }

    
    
    
    
    @GetMapping("/entity-models/findOtherEntities/{id}")
    public List<EntityModel> findOtherEntities(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
        List<EntityModel> entityModel = entityModelService.resteEntities(id);
        return  entityModel;
    }

    
    
    
    
}
