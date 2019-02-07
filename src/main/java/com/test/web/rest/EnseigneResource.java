package com.test.web.rest;
import com.test.service.EnseigneService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.EnseigneDTO;
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
 * REST controller for managing Enseigne.
 */
@RestController
@RequestMapping("/api")
public class EnseigneResource {

    private final Logger log = LoggerFactory.getLogger(EnseigneResource.class);

    private static final String ENTITY_NAME = "enseigne";

    private final EnseigneService enseigneService;

    public EnseigneResource(EnseigneService enseigneService) {
        this.enseigneService = enseigneService;
    }

    /**
     * POST  /enseignes : Create a new enseigne.
     *
     * @param enseigneDTO the enseigneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enseigneDTO, or with status 400 (Bad Request) if the enseigne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enseignes")
    public ResponseEntity<EnseigneDTO> createEnseigne(@Valid @RequestBody EnseigneDTO enseigneDTO) throws URISyntaxException {
        log.debug("REST request to save Enseigne : {}", enseigneDTO);
        if (enseigneDTO.getId() != null) {
            throw new BadRequestAlertException("A new enseigne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnseigneDTO result = enseigneService.save(enseigneDTO);
        return ResponseEntity.created(new URI("/api/enseignes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enseignes : Updates an existing enseigne.
     *
     * @param enseigneDTO the enseigneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enseigneDTO,
     * or with status 400 (Bad Request) if the enseigneDTO is not valid,
     * or with status 500 (Internal Server Error) if the enseigneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enseignes")
    public ResponseEntity<EnseigneDTO> updateEnseigne(@Valid @RequestBody EnseigneDTO enseigneDTO) throws URISyntaxException {
        log.debug("REST request to update Enseigne : {}", enseigneDTO);
        if (enseigneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnseigneDTO result = enseigneService.save(enseigneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enseigneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enseignes : get all the enseignes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of enseignes in body
     */
    @GetMapping("/enseignes")
    public ResponseEntity<List<EnseigneDTO>> getAllEnseignes(Pageable pageable) {
        log.debug("REST request to get a page of Enseignes");
        Page<EnseigneDTO> page = enseigneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enseignes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /enseignes/:id : get the "id" enseigne.
     *
     * @param id the id of the enseigneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enseigneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enseignes/{id}")
    public ResponseEntity<EnseigneDTO> getEnseigne(@PathVariable Long id) {
        log.debug("REST request to get Enseigne : {}", id);
        Optional<EnseigneDTO> enseigneDTO = enseigneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enseigneDTO);
    }

    /**
     * DELETE  /enseignes/:id : delete the "id" enseigne.
     *
     * @param id the id of the enseigneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enseignes/{id}")
    public ResponseEntity<Void> deleteEnseigne(@PathVariable Long id) {
        log.debug("REST request to delete Enseigne : {}", id);
        enseigneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
