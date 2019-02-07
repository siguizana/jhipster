package com.test.web.rest;
import com.test.service.ResultatService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.ResultatDTO;
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
 * REST controller for managing Resultat.
 */
@RestController
@RequestMapping("/api")
public class ResultatResource {

    private final Logger log = LoggerFactory.getLogger(ResultatResource.class);

    private static final String ENTITY_NAME = "resultat";

    private final ResultatService resultatService;

    public ResultatResource(ResultatService resultatService) {
        this.resultatService = resultatService;
    }

    /**
     * POST  /resultats : Create a new resultat.
     *
     * @param resultatDTO the resultatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultatDTO, or with status 400 (Bad Request) if the resultat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resultats")
    public ResponseEntity<ResultatDTO> createResultat(@Valid @RequestBody ResultatDTO resultatDTO) throws URISyntaxException {
        log.debug("REST request to save Resultat : {}", resultatDTO);
        if (resultatDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultatDTO result = resultatService.save(resultatDTO);
        return ResponseEntity.created(new URI("/api/resultats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resultats : Updates an existing resultat.
     *
     * @param resultatDTO the resultatDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultatDTO,
     * or with status 400 (Bad Request) if the resultatDTO is not valid,
     * or with status 500 (Internal Server Error) if the resultatDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resultats")
    public ResponseEntity<ResultatDTO> updateResultat(@Valid @RequestBody ResultatDTO resultatDTO) throws URISyntaxException {
        log.debug("REST request to update Resultat : {}", resultatDTO);
        if (resultatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultatDTO result = resultatService.save(resultatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultatDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resultats : get all the resultats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resultats in body
     */
    @GetMapping("/resultats")
    public ResponseEntity<List<ResultatDTO>> getAllResultats(Pageable pageable) {
        log.debug("REST request to get a page of Resultats");
        Page<ResultatDTO> page = resultatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resultats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /resultats/:id : get the "id" resultat.
     *
     * @param id the id of the resultatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/resultats/{id}")
    public ResponseEntity<ResultatDTO> getResultat(@PathVariable Long id) {
        log.debug("REST request to get Resultat : {}", id);
        Optional<ResultatDTO> resultatDTO = resultatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultatDTO);
    }

    /**
     * DELETE  /resultats/:id : delete the "id" resultat.
     *
     * @param id the id of the resultatDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resultats/{id}")
    public ResponseEntity<Void> deleteResultat(@PathVariable Long id) {
        log.debug("REST request to delete Resultat : {}", id);
        resultatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
