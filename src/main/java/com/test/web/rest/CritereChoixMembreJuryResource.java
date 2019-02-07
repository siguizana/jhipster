package com.test.web.rest;
import com.test.service.CritereChoixMembreJuryService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CritereChoixMembreJuryDTO;
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
 * REST controller for managing CritereChoixMembreJury.
 */
@RestController
@RequestMapping("/api")
public class CritereChoixMembreJuryResource {

    private final Logger log = LoggerFactory.getLogger(CritereChoixMembreJuryResource.class);

    private static final String ENTITY_NAME = "critereChoixMembreJury";

    private final CritereChoixMembreJuryService critereChoixMembreJuryService;

    public CritereChoixMembreJuryResource(CritereChoixMembreJuryService critereChoixMembreJuryService) {
        this.critereChoixMembreJuryService = critereChoixMembreJuryService;
    }

    /**
     * POST  /critere-choix-membre-juries : Create a new critereChoixMembreJury.
     *
     * @param critereChoixMembreJuryDTO the critereChoixMembreJuryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new critereChoixMembreJuryDTO, or with status 400 (Bad Request) if the critereChoixMembreJury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/critere-choix-membre-juries")
    public ResponseEntity<CritereChoixMembreJuryDTO> createCritereChoixMembreJury(@Valid @RequestBody CritereChoixMembreJuryDTO critereChoixMembreJuryDTO) throws URISyntaxException {
        log.debug("REST request to save CritereChoixMembreJury : {}", critereChoixMembreJuryDTO);
        if (critereChoixMembreJuryDTO.getId() != null) {
            throw new BadRequestAlertException("A new critereChoixMembreJury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CritereChoixMembreJuryDTO result = critereChoixMembreJuryService.save(critereChoixMembreJuryDTO);
        return ResponseEntity.created(new URI("/api/critere-choix-membre-juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /critere-choix-membre-juries : Updates an existing critereChoixMembreJury.
     *
     * @param critereChoixMembreJuryDTO the critereChoixMembreJuryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated critereChoixMembreJuryDTO,
     * or with status 400 (Bad Request) if the critereChoixMembreJuryDTO is not valid,
     * or with status 500 (Internal Server Error) if the critereChoixMembreJuryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/critere-choix-membre-juries")
    public ResponseEntity<CritereChoixMembreJuryDTO> updateCritereChoixMembreJury(@Valid @RequestBody CritereChoixMembreJuryDTO critereChoixMembreJuryDTO) throws URISyntaxException {
        log.debug("REST request to update CritereChoixMembreJury : {}", critereChoixMembreJuryDTO);
        if (critereChoixMembreJuryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CritereChoixMembreJuryDTO result = critereChoixMembreJuryService.save(critereChoixMembreJuryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, critereChoixMembreJuryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /critere-choix-membre-juries : get all the critereChoixMembreJuries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of critereChoixMembreJuries in body
     */
    @GetMapping("/critere-choix-membre-juries")
    public ResponseEntity<List<CritereChoixMembreJuryDTO>> getAllCritereChoixMembreJuries(Pageable pageable) {
        log.debug("REST request to get a page of CritereChoixMembreJuries");
        Page<CritereChoixMembreJuryDTO> page = critereChoixMembreJuryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/critere-choix-membre-juries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /critere-choix-membre-juries/:id : get the "id" critereChoixMembreJury.
     *
     * @param id the id of the critereChoixMembreJuryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the critereChoixMembreJuryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/critere-choix-membre-juries/{id}")
    public ResponseEntity<CritereChoixMembreJuryDTO> getCritereChoixMembreJury(@PathVariable Long id) {
        log.debug("REST request to get CritereChoixMembreJury : {}", id);
        Optional<CritereChoixMembreJuryDTO> critereChoixMembreJuryDTO = critereChoixMembreJuryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(critereChoixMembreJuryDTO);
    }

    /**
     * DELETE  /critere-choix-membre-juries/:id : delete the "id" critereChoixMembreJury.
     *
     * @param id the id of the critereChoixMembreJuryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/critere-choix-membre-juries/{id}")
    public ResponseEntity<Void> deleteCritereChoixMembreJury(@PathVariable Long id) {
        log.debug("REST request to delete CritereChoixMembreJury : {}", id);
        critereChoixMembreJuryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
