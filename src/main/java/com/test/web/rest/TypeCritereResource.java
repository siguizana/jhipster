package com.test.web.rest;
import com.test.service.TypeCritereService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeCritereDTO;
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
 * REST controller for managing TypeCritere.
 */
@RestController
@RequestMapping("/api")
public class TypeCritereResource {

    private final Logger log = LoggerFactory.getLogger(TypeCritereResource.class);

    private static final String ENTITY_NAME = "typeCritere";

    private final TypeCritereService typeCritereService;

    public TypeCritereResource(TypeCritereService typeCritereService) {
        this.typeCritereService = typeCritereService;
    }

    /**
     * POST  /type-criteres : Create a new typeCritere.
     *
     * @param typeCritereDTO the typeCritereDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeCritereDTO, or with status 400 (Bad Request) if the typeCritere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-criteres")
    public ResponseEntity<TypeCritereDTO> createTypeCritere(@Valid @RequestBody TypeCritereDTO typeCritereDTO) throws URISyntaxException {
        log.debug("REST request to save TypeCritere : {}", typeCritereDTO);
        if (typeCritereDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeCritere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCritereDTO result = typeCritereService.save(typeCritereDTO);
        return ResponseEntity.created(new URI("/api/type-criteres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-criteres : Updates an existing typeCritere.
     *
     * @param typeCritereDTO the typeCritereDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeCritereDTO,
     * or with status 400 (Bad Request) if the typeCritereDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeCritereDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-criteres")
    public ResponseEntity<TypeCritereDTO> updateTypeCritere(@Valid @RequestBody TypeCritereDTO typeCritereDTO) throws URISyntaxException {
        log.debug("REST request to update TypeCritere : {}", typeCritereDTO);
        if (typeCritereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeCritereDTO result = typeCritereService.save(typeCritereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeCritereDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-criteres : get all the typeCriteres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeCriteres in body
     */
    @GetMapping("/type-criteres")
    public ResponseEntity<List<TypeCritereDTO>> getAllTypeCriteres(Pageable pageable) {
        log.debug("REST request to get a page of TypeCriteres");
        Page<TypeCritereDTO> page = typeCritereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-criteres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-criteres/:id : get the "id" typeCritere.
     *
     * @param id the id of the typeCritereDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeCritereDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-criteres/{id}")
    public ResponseEntity<TypeCritereDTO> getTypeCritere(@PathVariable Long id) {
        log.debug("REST request to get TypeCritere : {}", id);
        Optional<TypeCritereDTO> typeCritereDTO = typeCritereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeCritereDTO);
    }

    /**
     * DELETE  /type-criteres/:id : delete the "id" typeCritere.
     *
     * @param id the id of the typeCritereDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-criteres/{id}")
    public ResponseEntity<Void> deleteTypeCritere(@PathVariable Long id) {
        log.debug("REST request to delete TypeCritere : {}", id);
        typeCritereService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
