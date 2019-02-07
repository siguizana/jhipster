package com.test.web.rest;
import com.test.service.TypeCentreCompositionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeCentreCompositionDTO;
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
 * REST controller for managing TypeCentreComposition.
 */
@RestController
@RequestMapping("/api")
public class TypeCentreCompositionResource {

    private final Logger log = LoggerFactory.getLogger(TypeCentreCompositionResource.class);

    private static final String ENTITY_NAME = "typeCentreComposition";

    private final TypeCentreCompositionService typeCentreCompositionService;

    public TypeCentreCompositionResource(TypeCentreCompositionService typeCentreCompositionService) {
        this.typeCentreCompositionService = typeCentreCompositionService;
    }

    /**
     * POST  /type-centre-compositions : Create a new typeCentreComposition.
     *
     * @param typeCentreCompositionDTO the typeCentreCompositionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeCentreCompositionDTO, or with status 400 (Bad Request) if the typeCentreComposition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-centre-compositions")
    public ResponseEntity<TypeCentreCompositionDTO> createTypeCentreComposition(@Valid @RequestBody TypeCentreCompositionDTO typeCentreCompositionDTO) throws URISyntaxException {
        log.debug("REST request to save TypeCentreComposition : {}", typeCentreCompositionDTO);
        if (typeCentreCompositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeCentreComposition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCentreCompositionDTO result = typeCentreCompositionService.save(typeCentreCompositionDTO);
        return ResponseEntity.created(new URI("/api/type-centre-compositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-centre-compositions : Updates an existing typeCentreComposition.
     *
     * @param typeCentreCompositionDTO the typeCentreCompositionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeCentreCompositionDTO,
     * or with status 400 (Bad Request) if the typeCentreCompositionDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeCentreCompositionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-centre-compositions")
    public ResponseEntity<TypeCentreCompositionDTO> updateTypeCentreComposition(@Valid @RequestBody TypeCentreCompositionDTO typeCentreCompositionDTO) throws URISyntaxException {
        log.debug("REST request to update TypeCentreComposition : {}", typeCentreCompositionDTO);
        if (typeCentreCompositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeCentreCompositionDTO result = typeCentreCompositionService.save(typeCentreCompositionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeCentreCompositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-centre-compositions : get all the typeCentreCompositions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeCentreCompositions in body
     */
    @GetMapping("/type-centre-compositions")
    public ResponseEntity<List<TypeCentreCompositionDTO>> getAllTypeCentreCompositions(Pageable pageable) {
        log.debug("REST request to get a page of TypeCentreCompositions");
        Page<TypeCentreCompositionDTO> page = typeCentreCompositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-centre-compositions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-centre-compositions/:id : get the "id" typeCentreComposition.
     *
     * @param id the id of the typeCentreCompositionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeCentreCompositionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-centre-compositions/{id}")
    public ResponseEntity<TypeCentreCompositionDTO> getTypeCentreComposition(@PathVariable Long id) {
        log.debug("REST request to get TypeCentreComposition : {}", id);
        Optional<TypeCentreCompositionDTO> typeCentreCompositionDTO = typeCentreCompositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeCentreCompositionDTO);
    }

    /**
     * DELETE  /type-centre-compositions/:id : delete the "id" typeCentreComposition.
     *
     * @param id the id of the typeCentreCompositionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-centre-compositions/{id}")
    public ResponseEntity<Void> deleteTypeCentreComposition(@PathVariable Long id) {
        log.debug("REST request to delete TypeCentreComposition : {}", id);
        typeCentreCompositionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
