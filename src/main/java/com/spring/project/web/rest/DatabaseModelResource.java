package com.spring.project.web.rest;

import com.spring.project.domain.DatabaseModel;
import com.spring.project.service.DatabaseModelService;
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
 * REST controller for managing {@link com.spring.project.domain.DatabaseModel}.
 */
@RestController
@RequestMapping("/api")
public class DatabaseModelResource {

    private final Logger log = LoggerFactory.getLogger(DatabaseModelResource.class);

    private static final String ENTITY_NAME = "databaseModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatabaseModelService databaseModelService;

    public DatabaseModelResource(DatabaseModelService databaseModelService) {
        this.databaseModelService = databaseModelService;
    }

    /**
     * {@code POST  /database-models} : Create a new databaseModel.
     *
     * @param databaseModel the databaseModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new databaseModel, or with status {@code 400 (Bad Request)} if the databaseModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/database-models")
    public ResponseEntity<DatabaseModel> createDatabaseModel(@RequestBody DatabaseModel databaseModel) throws URISyntaxException {
        log.debug("REST request to save DatabaseModel : {}", databaseModel);
        if (databaseModel.getId() != null) {
            throw new BadRequestAlertException("A new databaseModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatabaseModel result = databaseModelService.save(databaseModel);
        return ResponseEntity.created(new URI("/api/database-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /database-models} : Updates an existing databaseModel.
     *
     * @param databaseModel the databaseModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated databaseModel,
     * or with status {@code 400 (Bad Request)} if the databaseModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the databaseModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/database-models")
    public ResponseEntity<DatabaseModel> updateDatabaseModel(@RequestBody DatabaseModel databaseModel) throws URISyntaxException {
        log.debug("REST request to update DatabaseModel : {}", databaseModel);
        if (databaseModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatabaseModel result = databaseModelService.save(databaseModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, databaseModel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /database-models} : get all the databaseModels.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of databaseModels in body.
     */
    @GetMapping("/database-models")
    public List<DatabaseModel> getAllDatabaseModels() {
        log.debug("REST request to get all DatabaseModels");
        return databaseModelService.findAll();
    }

    /**
     * {@code GET  /database-models/:id} : get the "id" databaseModel.
     *
     * @param id the id of the databaseModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the databaseModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/database-models/{id}")
    public ResponseEntity<DatabaseModel> getDatabaseModel(@PathVariable String id) {
        log.debug("REST request to get DatabaseModel : {}", id);
        Optional<DatabaseModel> databaseModel = databaseModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(databaseModel);
    }

    /**
     * {@code DELETE  /database-models/:id} : delete the "id" databaseModel.
     *
     * @param id the id of the databaseModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/database-models/{id}")
    public ResponseEntity<Void> deleteDatabaseModel(@PathVariable String id) {
        log.debug("REST request to delete DatabaseModel : {}", id);
        databaseModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
