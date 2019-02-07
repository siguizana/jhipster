package com.test.web.rest;
import com.test.service.TypeMembreJuryService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeMembreJuryDTO;
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
 * REST controller for managing TypeMembreJury.
 */
@RestController
@RequestMapping("/api")
public class TypeMembreJuryResource {

    private final Logger log = LoggerFactory.getLogger(TypeMembreJuryResource.class);

    private static final String ENTITY_NAME = "typeMembreJury";

    private final TypeMembreJuryService typeMembreJuryService;

    public TypeMembreJuryResource(TypeMembreJuryService typeMembreJuryService) {
        this.typeMembreJuryService = typeMembreJuryService;
    }

    /**
     * POST  /type-membre-juries : Create a new typeMembreJury.
     *
     * @param typeMembreJuryDTO the typeMembreJuryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeMembreJuryDTO, or with status 400 (Bad Request) if the typeMembreJury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-membre-juries")
    public ResponseEntity<TypeMembreJuryDTO> createTypeMembreJury(@Valid @RequestBody TypeMembreJuryDTO typeMembreJuryDTO) throws URISyntaxException {
        log.debug("REST request to save TypeMembreJury : {}", typeMembreJuryDTO);
        if (typeMembreJuryDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeMembreJury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeMembreJuryDTO result = typeMembreJuryService.save(typeMembreJuryDTO);
        return ResponseEntity.created(new URI("/api/type-membre-juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-membre-juries : Updates an existing typeMembreJury.
     *
     * @param typeMembreJuryDTO the typeMembreJuryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeMembreJuryDTO,
     * or with status 400 (Bad Request) if the typeMembreJuryDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeMembreJuryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-membre-juries")
    public ResponseEntity<TypeMembreJuryDTO> updateTypeMembreJury(@Valid @RequestBody TypeMembreJuryDTO typeMembreJuryDTO) throws URISyntaxException {
        log.debug("REST request to update TypeMembreJury : {}", typeMembreJuryDTO);
        if (typeMembreJuryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeMembreJuryDTO result = typeMembreJuryService.save(typeMembreJuryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeMembreJuryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-membre-juries : get all the typeMembreJuries.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of typeMembreJuries in body
     */
    @GetMapping("/type-membre-juries")
    public ResponseEntity<List<TypeMembreJuryDTO>> getAllTypeMembreJuries(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of TypeMembreJuries");
        Page<TypeMembreJuryDTO> page;
        if (eagerload) {
            page = typeMembreJuryService.findAllWithEagerRelationships(pageable);
        } else {
            page = typeMembreJuryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/type-membre-juries?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-membre-juries/:id : get the "id" typeMembreJury.
     *
     * @param id the id of the typeMembreJuryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeMembreJuryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-membre-juries/{id}")
    public ResponseEntity<TypeMembreJuryDTO> getTypeMembreJury(@PathVariable Long id) {
        log.debug("REST request to get TypeMembreJury : {}", id);
        Optional<TypeMembreJuryDTO> typeMembreJuryDTO = typeMembreJuryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeMembreJuryDTO);
    }

    /**
     * DELETE  /type-membre-juries/:id : delete the "id" typeMembreJury.
     *
     * @param id the id of the typeMembreJuryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-membre-juries/{id}")
    public ResponseEntity<Void> deleteTypeMembreJury(@PathVariable Long id) {
        log.debug("REST request to delete TypeMembreJury : {}", id);
        typeMembreJuryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
