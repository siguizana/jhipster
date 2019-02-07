package com.test.web.rest;
import com.test.service.TypeEpreuveService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeEpreuveDTO;
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
 * REST controller for managing TypeEpreuve.
 */
@RestController
@RequestMapping("/api")
public class TypeEpreuveResource {

    private final Logger log = LoggerFactory.getLogger(TypeEpreuveResource.class);

    private static final String ENTITY_NAME = "typeEpreuve";

    private final TypeEpreuveService typeEpreuveService;

    public TypeEpreuveResource(TypeEpreuveService typeEpreuveService) {
        this.typeEpreuveService = typeEpreuveService;
    }

    /**
     * POST  /type-epreuves : Create a new typeEpreuve.
     *
     * @param typeEpreuveDTO the typeEpreuveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeEpreuveDTO, or with status 400 (Bad Request) if the typeEpreuve has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-epreuves")
    public ResponseEntity<TypeEpreuveDTO> createTypeEpreuve(@Valid @RequestBody TypeEpreuveDTO typeEpreuveDTO) throws URISyntaxException {
        log.debug("REST request to save TypeEpreuve : {}", typeEpreuveDTO);
        if (typeEpreuveDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeEpreuve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeEpreuveDTO result = typeEpreuveService.save(typeEpreuveDTO);
        return ResponseEntity.created(new URI("/api/type-epreuves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-epreuves : Updates an existing typeEpreuve.
     *
     * @param typeEpreuveDTO the typeEpreuveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeEpreuveDTO,
     * or with status 400 (Bad Request) if the typeEpreuveDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeEpreuveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-epreuves")
    public ResponseEntity<TypeEpreuveDTO> updateTypeEpreuve(@Valid @RequestBody TypeEpreuveDTO typeEpreuveDTO) throws URISyntaxException {
        log.debug("REST request to update TypeEpreuve : {}", typeEpreuveDTO);
        if (typeEpreuveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeEpreuveDTO result = typeEpreuveService.save(typeEpreuveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeEpreuveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-epreuves : get all the typeEpreuves.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeEpreuves in body
     */
    @GetMapping("/type-epreuves")
    public ResponseEntity<List<TypeEpreuveDTO>> getAllTypeEpreuves(Pageable pageable) {
        log.debug("REST request to get a page of TypeEpreuves");
        Page<TypeEpreuveDTO> page = typeEpreuveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-epreuves");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-epreuves/:id : get the "id" typeEpreuve.
     *
     * @param id the id of the typeEpreuveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeEpreuveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-epreuves/{id}")
    public ResponseEntity<TypeEpreuveDTO> getTypeEpreuve(@PathVariable Long id) {
        log.debug("REST request to get TypeEpreuve : {}", id);
        Optional<TypeEpreuveDTO> typeEpreuveDTO = typeEpreuveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeEpreuveDTO);
    }

    /**
     * DELETE  /type-epreuves/:id : delete the "id" typeEpreuve.
     *
     * @param id the id of the typeEpreuveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-epreuves/{id}")
    public ResponseEntity<Void> deleteTypeEpreuve(@PathVariable Long id) {
        log.debug("REST request to delete TypeEpreuve : {}", id);
        typeEpreuveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
