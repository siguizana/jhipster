package com.test.web.rest;
import com.test.service.FraudeService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.FraudeDTO;
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
 * REST controller for managing Fraude.
 */
@RestController
@RequestMapping("/api")
public class FraudeResource {

    private final Logger log = LoggerFactory.getLogger(FraudeResource.class);

    private static final String ENTITY_NAME = "fraude";

    private final FraudeService fraudeService;

    public FraudeResource(FraudeService fraudeService) {
        this.fraudeService = fraudeService;
    }

    /**
     * POST  /fraudes : Create a new fraude.
     *
     * @param fraudeDTO the fraudeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fraudeDTO, or with status 400 (Bad Request) if the fraude has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fraudes")
    public ResponseEntity<FraudeDTO> createFraude(@Valid @RequestBody FraudeDTO fraudeDTO) throws URISyntaxException {
        log.debug("REST request to save Fraude : {}", fraudeDTO);
        if (fraudeDTO.getId() != null) {
            throw new BadRequestAlertException("A new fraude cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FraudeDTO result = fraudeService.save(fraudeDTO);
        return ResponseEntity.created(new URI("/api/fraudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fraudes : Updates an existing fraude.
     *
     * @param fraudeDTO the fraudeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fraudeDTO,
     * or with status 400 (Bad Request) if the fraudeDTO is not valid,
     * or with status 500 (Internal Server Error) if the fraudeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fraudes")
    public ResponseEntity<FraudeDTO> updateFraude(@Valid @RequestBody FraudeDTO fraudeDTO) throws URISyntaxException {
        log.debug("REST request to update Fraude : {}", fraudeDTO);
        if (fraudeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FraudeDTO result = fraudeService.save(fraudeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fraudeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fraudes : get all the fraudes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fraudes in body
     */
    @GetMapping("/fraudes")
    public ResponseEntity<List<FraudeDTO>> getAllFraudes(Pageable pageable) {
        log.debug("REST request to get a page of Fraudes");
        Page<FraudeDTO> page = fraudeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fraudes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /fraudes/:id : get the "id" fraude.
     *
     * @param id the id of the fraudeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fraudeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fraudes/{id}")
    public ResponseEntity<FraudeDTO> getFraude(@PathVariable Long id) {
        log.debug("REST request to get Fraude : {}", id);
        Optional<FraudeDTO> fraudeDTO = fraudeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fraudeDTO);
    }

    /**
     * DELETE  /fraudes/:id : delete the "id" fraude.
     *
     * @param id the id of the fraudeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fraudes/{id}")
    public ResponseEntity<Void> deleteFraude(@PathVariable Long id) {
        log.debug("REST request to delete Fraude : {}", id);
        fraudeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
