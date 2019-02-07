package com.test.web.rest;
import com.test.service.TypeEtablissementService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeEtablissementDTO;
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
 * REST controller for managing TypeEtablissement.
 */
@RestController
@RequestMapping("/api")
public class TypeEtablissementResource {

    private final Logger log = LoggerFactory.getLogger(TypeEtablissementResource.class);

    private static final String ENTITY_NAME = "typeEtablissement";

    private final TypeEtablissementService typeEtablissementService;

    public TypeEtablissementResource(TypeEtablissementService typeEtablissementService) {
        this.typeEtablissementService = typeEtablissementService;
    }

    /**
     * POST  /type-etablissements : Create a new typeEtablissement.
     *
     * @param typeEtablissementDTO the typeEtablissementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeEtablissementDTO, or with status 400 (Bad Request) if the typeEtablissement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-etablissements")
    public ResponseEntity<TypeEtablissementDTO> createTypeEtablissement(@Valid @RequestBody TypeEtablissementDTO typeEtablissementDTO) throws URISyntaxException {
        log.debug("REST request to save TypeEtablissement : {}", typeEtablissementDTO);
        if (typeEtablissementDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeEtablissement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeEtablissementDTO result = typeEtablissementService.save(typeEtablissementDTO);
        return ResponseEntity.created(new URI("/api/type-etablissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-etablissements : Updates an existing typeEtablissement.
     *
     * @param typeEtablissementDTO the typeEtablissementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeEtablissementDTO,
     * or with status 400 (Bad Request) if the typeEtablissementDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeEtablissementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-etablissements")
    public ResponseEntity<TypeEtablissementDTO> updateTypeEtablissement(@Valid @RequestBody TypeEtablissementDTO typeEtablissementDTO) throws URISyntaxException {
        log.debug("REST request to update TypeEtablissement : {}", typeEtablissementDTO);
        if (typeEtablissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeEtablissementDTO result = typeEtablissementService.save(typeEtablissementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeEtablissementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-etablissements : get all the typeEtablissements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeEtablissements in body
     */
    @GetMapping("/type-etablissements")
    public ResponseEntity<List<TypeEtablissementDTO>> getAllTypeEtablissements(Pageable pageable) {
        log.debug("REST request to get a page of TypeEtablissements");
        Page<TypeEtablissementDTO> page = typeEtablissementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-etablissements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-etablissements/:id : get the "id" typeEtablissement.
     *
     * @param id the id of the typeEtablissementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeEtablissementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-etablissements/{id}")
    public ResponseEntity<TypeEtablissementDTO> getTypeEtablissement(@PathVariable Long id) {
        log.debug("REST request to get TypeEtablissement : {}", id);
        Optional<TypeEtablissementDTO> typeEtablissementDTO = typeEtablissementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeEtablissementDTO);
    }

    /**
     * DELETE  /type-etablissements/:id : delete the "id" typeEtablissement.
     *
     * @param id the id of the typeEtablissementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-etablissements/{id}")
    public ResponseEntity<Void> deleteTypeEtablissement(@PathVariable Long id) {
        log.debug("REST request to delete TypeEtablissement : {}", id);
        typeEtablissementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
