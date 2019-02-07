package com.test.web.rest;
import com.test.service.EnseignantService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.EnseignantDTO;
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
 * REST controller for managing Enseignant.
 */
@RestController
@RequestMapping("/api")
public class EnseignantResource {

    private final Logger log = LoggerFactory.getLogger(EnseignantResource.class);

    private static final String ENTITY_NAME = "enseignant";

    private final EnseignantService enseignantService;

    public EnseignantResource(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    /**
     * POST  /enseignants : Create a new enseignant.
     *
     * @param enseignantDTO the enseignantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enseignantDTO, or with status 400 (Bad Request) if the enseignant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enseignants")
    public ResponseEntity<EnseignantDTO> createEnseignant(@Valid @RequestBody EnseignantDTO enseignantDTO) throws URISyntaxException {
        log.debug("REST request to save Enseignant : {}", enseignantDTO);
        if (enseignantDTO.getId() != null) {
            throw new BadRequestAlertException("A new enseignant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnseignantDTO result = enseignantService.save(enseignantDTO);
        return ResponseEntity.created(new URI("/api/enseignants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enseignants : Updates an existing enseignant.
     *
     * @param enseignantDTO the enseignantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enseignantDTO,
     * or with status 400 (Bad Request) if the enseignantDTO is not valid,
     * or with status 500 (Internal Server Error) if the enseignantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enseignants")
    public ResponseEntity<EnseignantDTO> updateEnseignant(@Valid @RequestBody EnseignantDTO enseignantDTO) throws URISyntaxException {
        log.debug("REST request to update Enseignant : {}", enseignantDTO);
        if (enseignantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnseignantDTO result = enseignantService.save(enseignantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enseignantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enseignants : get all the enseignants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of enseignants in body
     */
    @GetMapping("/enseignants")
    public ResponseEntity<List<EnseignantDTO>> getAllEnseignants(Pageable pageable) {
        log.debug("REST request to get a page of Enseignants");
        Page<EnseignantDTO> page = enseignantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enseignants");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /enseignants/:id : get the "id" enseignant.
     *
     * @param id the id of the enseignantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enseignantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enseignants/{id}")
    public ResponseEntity<EnseignantDTO> getEnseignant(@PathVariable Long id) {
        log.debug("REST request to get Enseignant : {}", id);
        Optional<EnseignantDTO> enseignantDTO = enseignantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enseignantDTO);
    }

    /**
     * DELETE  /enseignants/:id : delete the "id" enseignant.
     *
     * @param id the id of the enseignantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enseignants/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        log.debug("REST request to delete Enseignant : {}", id);
        enseignantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
