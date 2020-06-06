package com.spring.project.web.rest;

import com.spring.project.domain.InstanceRelation;
import com.spring.project.service.InstanceRelationService;
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
 * REST controller for managing {@link com.spring.project.domain.InstanceRelation}.
 */
@RestController
@RequestMapping("/api")
public class InstanceRelationResource {

    private final Logger log = LoggerFactory.getLogger(InstanceRelationResource.class);

    private static final String ENTITY_NAME = "instanceRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstanceRelationService instanceRelationService;

    public InstanceRelationResource(InstanceRelationService instanceRelationService) {
        this.instanceRelationService = instanceRelationService;
    }

    /**
     * {@code POST  /instance-relations} : Create a new instanceRelation.
     *
     * @param instanceRelation the instanceRelation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instanceRelation, or with status {@code 400 (Bad Request)} if the instanceRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instance-relations")
    public ResponseEntity<InstanceRelation> createInstanceRelation(@RequestBody InstanceRelation instanceRelation) throws URISyntaxException {
        log.debug("REST request to save InstanceRelation : {}", instanceRelation);
        if (instanceRelation.getId() != null) {
            throw new BadRequestAlertException("A new instanceRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstanceRelation result = instanceRelationService.save(instanceRelation);
        return ResponseEntity.created(new URI("/api/instance-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instance-relations} : Updates an existing instanceRelation.
     *
     * @param instanceRelation the instanceRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instanceRelation,
     * or with status {@code 400 (Bad Request)} if the instanceRelation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instanceRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instance-relations")
    public ResponseEntity<InstanceRelation> updateInstanceRelation(@RequestBody InstanceRelation instanceRelation) throws URISyntaxException {
        log.debug("REST request to update InstanceRelation : {}", instanceRelation);
        if (instanceRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstanceRelation result = instanceRelationService.save(instanceRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instanceRelation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instance-relations} : get all the instanceRelations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instanceRelations in body.
     */
    @GetMapping("/instance-relations")
    public List<InstanceRelation> getAllInstanceRelations() {
        log.debug("REST request to get all InstanceRelations");
        return instanceRelationService.findAll();
    }

    /**
     * {@code GET  /instance-relations/:id} : get the "id" instanceRelation.
     *
     * @param id the id of the instanceRelation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instanceRelation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instance-relations/{id}")
    public ResponseEntity<InstanceRelation> getInstanceRelation(@PathVariable String id) {
        log.debug("REST request to get InstanceRelation : {}", id);
        Optional<InstanceRelation> instanceRelation = instanceRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instanceRelation);
    }

    /**
     * {@code DELETE  /instance-relations/:id} : delete the "id" instanceRelation.
     *
     * @param id the id of the instanceRelation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instance-relations/{id}")
    public ResponseEntity<Void> deleteInstanceRelation(@PathVariable String id) {
        log.debug("REST request to delete InstanceRelation : {}", id);
        instanceRelationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
