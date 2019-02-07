package com.test.web.rest;
import com.test.service.TypeDecisionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeDecisionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypeDecision.
 */
@RestController
@RequestMapping("/api")
public class TypeDecisionResource {

    private final Logger log = LoggerFactory.getLogger(TypeDecisionResource.class);

    private static final String ENTITY_NAME = "typeDecision";

    private final TypeDecisionService typeDecisionService;

    public TypeDecisionResource(TypeDecisionService typeDecisionService) {
        this.typeDecisionService = typeDecisionService;
    }

    /**
     * POST  /type-decisions : Create a new typeDecision.
     *
     * @param typeDecisionDTO the typeDecisionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeDecisionDTO, or with status 400 (Bad Request) if the typeDecision has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-decisions")
    public ResponseEntity<TypeDecisionDTO> createTypeDecision(@Valid @RequestBody TypeDecisionDTO typeDecisionDTO) throws URISyntaxException {
        log.debug("REST request to save TypeDecision : {}", typeDecisionDTO);
        if (typeDecisionDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeDecision cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDecisionDTO result = typeDecisionService.save(typeDecisionDTO);
        return ResponseEntity.created(new URI("/api/type-decisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-decisions : Updates an existing typeDecision.
     *
     * @param typeDecisionDTO the typeDecisionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeDecisionDTO,
     * or with status 400 (Bad Request) if the typeDecisionDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeDecisionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-decisions")
    public ResponseEntity<TypeDecisionDTO> updateTypeDecision(@Valid @RequestBody TypeDecisionDTO typeDecisionDTO) throws URISyntaxException {
        log.debug("REST request to update TypeDecision : {}", typeDecisionDTO);
        if (typeDecisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDecisionDTO result = typeDecisionService.save(typeDecisionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeDecisionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-decisions : get all the typeDecisions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeDecisions in body
     */
    @GetMapping("/type-decisions")
    public ResponseEntity<List<TypeDecisionDTO>> getAllTypeDecisions(Pageable pageable) {
        log.debug("REST request to get a page of TypeDecisions");
        Page<TypeDecisionDTO> page = typeDecisionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-decisions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-decisions/:id : get the "id" typeDecision.
     *
     * @param id the id of the typeDecisionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeDecisionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-decisions/{id}")
    public ResponseEntity<TypeDecisionDTO> getTypeDecision(@PathVariable Long id) {
        log.debug("REST request to get TypeDecision : {}", id);
        Optional<TypeDecisionDTO> typeDecisionDTO = typeDecisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDecisionDTO);
    }

    /**
     * DELETE  /type-decisions/:id : delete the "id" typeDecision.
     *
     * @param id the id of the typeDecisionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-decisions/{id}")
    public ResponseEntity<Void> deleteTypeDecision(@PathVariable Long id) {
        log.debug("REST request to delete TypeDecision : {}", id);
        typeDecisionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
