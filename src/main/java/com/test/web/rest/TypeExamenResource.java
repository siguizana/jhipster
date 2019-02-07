package com.test.web.rest;
import com.test.service.TypeExamenService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeExamenDTO;
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
 * REST controller for managing TypeExamen.
 */
@RestController
@RequestMapping("/api")
public class TypeExamenResource {

    private final Logger log = LoggerFactory.getLogger(TypeExamenResource.class);

    private static final String ENTITY_NAME = "typeExamen";

    private final TypeExamenService typeExamenService;

    public TypeExamenResource(TypeExamenService typeExamenService) {
        this.typeExamenService = typeExamenService;
    }

    /**
     * POST  /type-examen : Create a new typeExamen.
     *
     * @param typeExamenDTO the typeExamenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeExamenDTO, or with status 400 (Bad Request) if the typeExamen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-examen")
    public ResponseEntity<TypeExamenDTO> createTypeExamen(@Valid @RequestBody TypeExamenDTO typeExamenDTO) throws URISyntaxException {
        log.debug("REST request to save TypeExamen : {}", typeExamenDTO);
        if (typeExamenDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeExamen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeExamenDTO result = typeExamenService.save(typeExamenDTO);
        return ResponseEntity.created(new URI("/api/type-examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-examen : Updates an existing typeExamen.
     *
     * @param typeExamenDTO the typeExamenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeExamenDTO,
     * or with status 400 (Bad Request) if the typeExamenDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeExamenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-examen")
    public ResponseEntity<TypeExamenDTO> updateTypeExamen(@Valid @RequestBody TypeExamenDTO typeExamenDTO) throws URISyntaxException {
        log.debug("REST request to update TypeExamen : {}", typeExamenDTO);
        if (typeExamenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeExamenDTO result = typeExamenService.save(typeExamenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeExamenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-examen : get all the typeExamen.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeExamen in body
     */
    @GetMapping("/type-examen")
    public ResponseEntity<List<TypeExamenDTO>> getAllTypeExamen(Pageable pageable) {
        log.debug("REST request to get a page of TypeExamen");
        Page<TypeExamenDTO> page = typeExamenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-examen");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-examen/:id : get the "id" typeExamen.
     *
     * @param id the id of the typeExamenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeExamenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-examen/{id}")
    public ResponseEntity<TypeExamenDTO> getTypeExamen(@PathVariable Long id) {
        log.debug("REST request to get TypeExamen : {}", id);
        Optional<TypeExamenDTO> typeExamenDTO = typeExamenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeExamenDTO);
    }

    /**
     * DELETE  /type-examen/:id : delete the "id" typeExamen.
     *
     * @param id the id of the typeExamenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-examen/{id}")
    public ResponseEntity<Void> deleteTypeExamen(@PathVariable Long id) {
        log.debug("REST request to delete TypeExamen : {}", id);
        typeExamenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
