package com.test.web.rest;
import com.test.service.InscriptionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.InscriptionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Inscription.
 */
@RestController
@RequestMapping("/api")
public class InscriptionResource {

    private final Logger log = LoggerFactory.getLogger(InscriptionResource.class);

    private static final String ENTITY_NAME = "inscription";

    private final InscriptionService inscriptionService;

    public InscriptionResource(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    /**
     * POST  /inscriptions : Create a new inscription.
     *
     * @param inscriptionDTO the inscriptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inscriptionDTO, or with status 400 (Bad Request) if the inscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inscriptions")
    public ResponseEntity<InscriptionDTO> createInscription(@RequestBody InscriptionDTO inscriptionDTO) throws URISyntaxException {
        log.debug("REST request to save Inscription : {}", inscriptionDTO);
        if (inscriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new inscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InscriptionDTO result = inscriptionService.save(inscriptionDTO);
        return ResponseEntity.created(new URI("/api/inscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inscriptions : Updates an existing inscription.
     *
     * @param inscriptionDTO the inscriptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inscriptionDTO,
     * or with status 400 (Bad Request) if the inscriptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the inscriptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inscriptions")
    public ResponseEntity<InscriptionDTO> updateInscription(@RequestBody InscriptionDTO inscriptionDTO) throws URISyntaxException {
        log.debug("REST request to update Inscription : {}", inscriptionDTO);
        if (inscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InscriptionDTO result = inscriptionService.save(inscriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inscriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inscriptions : get all the inscriptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inscriptions in body
     */
    @GetMapping("/inscriptions")
    public ResponseEntity<List<InscriptionDTO>> getAllInscriptions(Pageable pageable) {
        log.debug("REST request to get a page of Inscriptions");
        Page<InscriptionDTO> page = inscriptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inscriptions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /inscriptions/:id : get the "id" inscription.
     *
     * @param id the id of the inscriptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inscriptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<InscriptionDTO> getInscription(@PathVariable Long id) {
        log.debug("REST request to get Inscription : {}", id);
        Optional<InscriptionDTO> inscriptionDTO = inscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inscriptionDTO);
    }

    /**
     * DELETE  /inscriptions/:id : delete the "id" inscription.
     *
     * @param id the id of the inscriptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inscriptions/{id}")
    public ResponseEntity<Void> deleteInscription(@PathVariable Long id) {
        log.debug("REST request to delete Inscription : {}", id);
        inscriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
