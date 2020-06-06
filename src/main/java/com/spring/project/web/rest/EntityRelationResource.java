package com.spring.project.web.rest;

import com.spring.project.domain.EntityModel;
import com.spring.project.domain.EntityRelation;
import com.spring.project.service.EntityRelationService;
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

/**
 * REST controller for managing {@link com.spring.project.domain.EntityRelation}.
 */
@RestController
@RequestMapping("/api")
public class EntityRelationResource {

    private final Logger log = LoggerFactory.getLogger(EntityRelationResource.class);

    private static final String ENTITY_NAME = "entityRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityRelationService entityRelationService;

    public EntityRelationResource(EntityRelationService entityRelationService) {
        this.entityRelationService = entityRelationService;
    }

    /**
     * {@code POST  /entity-relations} : Create a new entityRelation.
     *
     * @param entityRelation the entityRelation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityRelation, or with status {@code 400 (Bad Request)} if the entityRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-relations")
    public ResponseEntity<EntityRelation> createEntityRelation(@RequestBody EntityRelation entityRelation) throws URISyntaxException {
        log.debug("REST request to save EntityRelation : {}", entityRelation);
        if (entityRelation.getId() != null) {
            throw new BadRequestAlertException("A new entityRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityRelation result = entityRelationService.save(entityRelation);
        return ResponseEntity.created(new URI("/api/entity-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-relations} : Updates an existing entityRelation.
     *
     * @param entityRelation the entityRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityRelation,
     * or with status {@code 400 (Bad Request)} if the entityRelation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-relations")
    public ResponseEntity<EntityRelation> updateEntityRelation(@RequestBody EntityRelation entityRelation) throws URISyntaxException {
        log.debug("REST request to update EntityRelation : {}", entityRelation);
        if (entityRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntityRelation result = entityRelationService.save(entityRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityRelation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entity-relations} : get all the entityRelations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityRelations in body.
     */
    @GetMapping("/entity-relations")
    public List<EntityRelation> getAllEntityRelations() {
        log.debug("REST request to get all EntityRelations");
        return entityRelationService.findAll();
    }

    /**
     * {@code GET  /entity-relations/:id} : get the "id" entityRelation.
     *
     * @param id the id of the entityRelation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityRelation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-relations/{id}")
    public ResponseEntity<EntityRelation> getEntityRelation(@PathVariable String id) {
        log.debug("REST request to get EntityRelation : {}", id);
        Optional<EntityRelation> entityRelation = entityRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entityRelation);
    }

    /**
     * {@code DELETE  /entity-relations/:id} : delete the "id" entityRelation.
     *
     * @param id the id of the entityRelation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-relations/{id}")
    public ResponseEntity<Void> deleteEntityRelation(@PathVariable String id) {
        log.debug("REST request to delete EntityRelation : {}", id);
        entityRelationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
    
    
    
    @GetMapping("/entity-relations/listSameData/{id}")
    public List<EntityRelation> findOtherEntities(@PathVariable String id) {
        
        List<EntityRelation> entityModel = entityRelationService.listOfSameSataModel(id);
        return  entityModel;
    }

    
    
    
    
    
    
    
    
}
