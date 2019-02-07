package com.test.web.rest;
import com.test.service.FiliereService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.FiliereDTO;
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
 * REST controller for managing Filiere.
 */
@RestController
@RequestMapping("/api")
public class FiliereResource {

    private final Logger log = LoggerFactory.getLogger(FiliereResource.class);

    private static final String ENTITY_NAME = "filiere";

    private final FiliereService filiereService;

    public FiliereResource(FiliereService filiereService) {
        this.filiereService = filiereService;
    }

    /**
     * POST  /filieres : Create a new filiere.
     *
     * @param filiereDTO the filiereDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filiereDTO, or with status 400 (Bad Request) if the filiere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filieres")
    public ResponseEntity<FiliereDTO> createFiliere(@Valid @RequestBody FiliereDTO filiereDTO) throws URISyntaxException {
        log.debug("REST request to save Filiere : {}", filiereDTO);
        if (filiereDTO.getId() != null) {
            throw new BadRequestAlertException("A new filiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FiliereDTO result = filiereService.save(filiereDTO);
        return ResponseEntity.created(new URI("/api/filieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filieres : Updates an existing filiere.
     *
     * @param filiereDTO the filiereDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filiereDTO,
     * or with status 400 (Bad Request) if the filiereDTO is not valid,
     * or with status 500 (Internal Server Error) if the filiereDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filieres")
    public ResponseEntity<FiliereDTO> updateFiliere(@Valid @RequestBody FiliereDTO filiereDTO) throws URISyntaxException {
        log.debug("REST request to update Filiere : {}", filiereDTO);
        if (filiereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FiliereDTO result = filiereService.save(filiereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filiereDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filieres : get all the filieres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of filieres in body
     */
    @GetMapping("/filieres")
    public ResponseEntity<List<FiliereDTO>> getAllFilieres(Pageable pageable) {
        log.debug("REST request to get a page of Filieres");
        Page<FiliereDTO> page = filiereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filieres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /filieres/:id : get the "id" filiere.
     *
     * @param id the id of the filiereDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filiereDTO, or with status 404 (Not Found)
     */
    @GetMapping("/filieres/{id}")
    public ResponseEntity<FiliereDTO> getFiliere(@PathVariable Long id) {
        log.debug("REST request to get Filiere : {}", id);
        Optional<FiliereDTO> filiereDTO = filiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filiereDTO);
    }

    /**
     * DELETE  /filieres/:id : delete the "id" filiere.
     *
     * @param id the id of the filiereDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filieres/{id}")
    public ResponseEntity<Void> deleteFiliere(@PathVariable Long id) {
        log.debug("REST request to delete Filiere : {}", id);
        filiereService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
