package com.spring.project.web.rest;

import com.spring.project.domain.EntityInstance;
import com.spring.project.domain.EntityModel;
import com.spring.project.service.EntityInstanceService;
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
 * REST controller for managing {@link com.spring.project.domain.EntityInstance}.
 */
@RestController
@RequestMapping("/api")
public class EntityInstanceResource {

    private final Logger log = LoggerFactory.getLogger(EntityInstanceResource.class);

    private static final String ENTITY_NAME = "entityInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityInstanceService entityInstanceService;

    public EntityInstanceResource(EntityInstanceService entityInstanceService) {
        this.entityInstanceService = entityInstanceService;
    }

    /**
     * {@code POST  /entity-instances} : Create a new entityInstance.
     *
     * @param entityInstance the entityInstance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityInstance, or with status {@code 400 (Bad Request)} if the entityInstance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-instances")
    public ResponseEntity<EntityInstance> createEntityInstance(@RequestBody EntityInstance entityInstance) throws URISyntaxException {
        log.debug("REST request to save EntityInstance : {}", entityInstance);
        if (entityInstance.getId() != null) {
            throw new BadRequestAlertException("A new entityInstance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityInstance result = entityInstanceService.save(entityInstance);
        return ResponseEntity.created(new URI("/api/entity-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-instances} : Updates an existing entityInstance.
     *
     * @param entityInstance the entityInstance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityInstance,
     * or with status {@code 400 (Bad Request)} if the entityInstance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityInstance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-instances")
    public ResponseEntity<EntityInstance> updateEntityInstance(@RequestBody EntityInstance entityInstance) throws URISyntaxException {
        log.debug("REST request to update EntityInstance : {}", entityInstance);
        if (entityInstance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntityInstance result = entityInstanceService.save(entityInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityInstance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entity-instances} : get all the entityInstances.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityInstances in body.
     */
    @GetMapping("/entity-instances")
    public List<EntityInstance> getAllEntityInstances(@RequestParam(required = false) String filter) {
        if ("instancerelation-is-null".equals(filter)) {
            log.debug("REST request to get all EntityInstances where instanceRelation is null");
            return entityInstanceService.findAllWhereInstanceRelationIsNull();
        }
        if ("instancerelation2-is-null".equals(filter)) {
            log.debug("REST request to get all EntityInstances where instanceRelation2 is null");
            return entityInstanceService.findAllWhereInstanceRelation2IsNull();
        }
        log.debug("REST request to get all EntityInstances");
        return entityInstanceService.findAll();
    }

    /**
     * {@code GET  /entity-instances/:id} : get the "id" entityInstance.
     *
     * @param id the id of the entityInstance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityInstance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-instances/{id}")
    public ResponseEntity<EntityInstance> getEntityInstance(@PathVariable String id) {
        log.debug("REST request to get EntityInstance : {}", id);
        Optional<EntityInstance> entityInstance = entityInstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entityInstance);
    }

    /**
     * {@code DELETE  /entity-instances/:id} : delete the "id" entityInstance.
     *
     * @param id the id of the entityInstance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-instances/{id}")
    public ResponseEntity<Void> deleteEntityInstance(@PathVariable String id) {
        log.debug("REST request to delete EntityInstance : {}", id);
        entityInstanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
    
    
    
    @GetMapping("/entity-instances/findInstance/{id}")
    public List<EntityInstance> findOtherEntities(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
        List<EntityInstance> entityModel = entityInstanceService.trouverInstance(id);
        return  entityModel;
    }
    
    
    
    @GetMapping("/entity-instances/find/{id}")
    public List<EntityInstance> findbyname(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
        List<EntityInstance> entityModel = entityInstanceService.findbyname(id);
        System.out.println(entityModel);
        return  entityModel;
    }
    
    
    @GetMapping("/entity-instances/findRel/{id}")
    public List<EntityInstance> findRel(@PathVariable String id) {
        log.debug("REST request to get EntityModel : {}", id);
        List<EntityInstance> entityModel = entityInstanceService.findRelations(id);
        System.out.println(entityModel);
        return  entityModel;
    }
    
    
    
    
    
    
    
}
