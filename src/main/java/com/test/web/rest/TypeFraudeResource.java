package com.test.web.rest;
import com.test.service.TypeFraudeService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeFraudeDTO;
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
 * REST controller for managing TypeFraude.
 */
@RestController
@RequestMapping("/api")
public class TypeFraudeResource {

    private final Logger log = LoggerFactory.getLogger(TypeFraudeResource.class);

    private static final String ENTITY_NAME = "typeFraude";

    private final TypeFraudeService typeFraudeService;

    public TypeFraudeResource(TypeFraudeService typeFraudeService) {
        this.typeFraudeService = typeFraudeService;
    }

    /**
     * POST  /type-fraudes : Create a new typeFraude.
     *
     * @param typeFraudeDTO the typeFraudeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeFraudeDTO, or with status 400 (Bad Request) if the typeFraude has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-fraudes")
    public ResponseEntity<TypeFraudeDTO> createTypeFraude(@Valid @RequestBody TypeFraudeDTO typeFraudeDTO) throws URISyntaxException {
        log.debug("REST request to save TypeFraude : {}", typeFraudeDTO);
        if (typeFraudeDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeFraude cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeFraudeDTO result = typeFraudeService.save(typeFraudeDTO);
        return ResponseEntity.created(new URI("/api/type-fraudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-fraudes : Updates an existing typeFraude.
     *
     * @param typeFraudeDTO the typeFraudeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeFraudeDTO,
     * or with status 400 (Bad Request) if the typeFraudeDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeFraudeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-fraudes")
    public ResponseEntity<TypeFraudeDTO> updateTypeFraude(@Valid @RequestBody TypeFraudeDTO typeFraudeDTO) throws URISyntaxException {
        log.debug("REST request to update TypeFraude : {}", typeFraudeDTO);
        if (typeFraudeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeFraudeDTO result = typeFraudeService.save(typeFraudeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeFraudeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-fraudes : get all the typeFraudes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeFraudes in body
     */
    @GetMapping("/type-fraudes")
    public ResponseEntity<List<TypeFraudeDTO>> getAllTypeFraudes(Pageable pageable) {
        log.debug("REST request to get a page of TypeFraudes");
        Page<TypeFraudeDTO> page = typeFraudeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-fraudes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-fraudes/:id : get the "id" typeFraude.
     *
     * @param id the id of the typeFraudeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeFraudeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-fraudes/{id}")
    public ResponseEntity<TypeFraudeDTO> getTypeFraude(@PathVariable Long id) {
        log.debug("REST request to get TypeFraude : {}", id);
        Optional<TypeFraudeDTO> typeFraudeDTO = typeFraudeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeFraudeDTO);
    }

    /**
     * DELETE  /type-fraudes/:id : delete the "id" typeFraude.
     *
     * @param id the id of the typeFraudeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-fraudes/{id}")
    public ResponseEntity<Void> deleteTypeFraude(@PathVariable Long id) {
        log.debug("REST request to delete TypeFraude : {}", id);
        typeFraudeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
