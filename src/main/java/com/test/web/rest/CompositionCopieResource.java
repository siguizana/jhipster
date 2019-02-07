package com.test.web.rest;
import com.test.service.CompositionCopieService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CompositionCopieDTO;
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
 * REST controller for managing CompositionCopie.
 */
@RestController
@RequestMapping("/api")
public class CompositionCopieResource {

    private final Logger log = LoggerFactory.getLogger(CompositionCopieResource.class);

    private static final String ENTITY_NAME = "compositionCopie";

    private final CompositionCopieService compositionCopieService;

    public CompositionCopieResource(CompositionCopieService compositionCopieService) {
        this.compositionCopieService = compositionCopieService;
    }

    /**
     * POST  /composition-copies : Create a new compositionCopie.
     *
     * @param compositionCopieDTO the compositionCopieDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compositionCopieDTO, or with status 400 (Bad Request) if the compositionCopie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/composition-copies")
    public ResponseEntity<CompositionCopieDTO> createCompositionCopie(@Valid @RequestBody CompositionCopieDTO compositionCopieDTO) throws URISyntaxException {
        log.debug("REST request to save CompositionCopie : {}", compositionCopieDTO);
        if (compositionCopieDTO.getId() != null) {
            throw new BadRequestAlertException("A new compositionCopie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompositionCopieDTO result = compositionCopieService.save(compositionCopieDTO);
        return ResponseEntity.created(new URI("/api/composition-copies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /composition-copies : Updates an existing compositionCopie.
     *
     * @param compositionCopieDTO the compositionCopieDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compositionCopieDTO,
     * or with status 400 (Bad Request) if the compositionCopieDTO is not valid,
     * or with status 500 (Internal Server Error) if the compositionCopieDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/composition-copies")
    public ResponseEntity<CompositionCopieDTO> updateCompositionCopie(@Valid @RequestBody CompositionCopieDTO compositionCopieDTO) throws URISyntaxException {
        log.debug("REST request to update CompositionCopie : {}", compositionCopieDTO);
        if (compositionCopieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompositionCopieDTO result = compositionCopieService.save(compositionCopieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compositionCopieDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /composition-copies : get all the compositionCopies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of compositionCopies in body
     */
    @GetMapping("/composition-copies")
    public ResponseEntity<List<CompositionCopieDTO>> getAllCompositionCopies(Pageable pageable) {
        log.debug("REST request to get a page of CompositionCopies");
        Page<CompositionCopieDTO> page = compositionCopieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/composition-copies");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /composition-copies/:id : get the "id" compositionCopie.
     *
     * @param id the id of the compositionCopieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compositionCopieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/composition-copies/{id}")
    public ResponseEntity<CompositionCopieDTO> getCompositionCopie(@PathVariable Long id) {
        log.debug("REST request to get CompositionCopie : {}", id);
        Optional<CompositionCopieDTO> compositionCopieDTO = compositionCopieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compositionCopieDTO);
    }

    /**
     * DELETE  /composition-copies/:id : delete the "id" compositionCopie.
     *
     * @param id the id of the compositionCopieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/composition-copies/{id}")
    public ResponseEntity<Void> deleteCompositionCopie(@PathVariable Long id) {
        log.debug("REST request to delete CompositionCopie : {}", id);
        compositionCopieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
