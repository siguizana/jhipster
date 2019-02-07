package com.test.web.rest;
import com.test.service.ChoixEtablissementService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.ChoixEtablissementDTO;
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
 * REST controller for managing ChoixEtablissement.
 */
@RestController
@RequestMapping("/api")
public class ChoixEtablissementResource {

    private final Logger log = LoggerFactory.getLogger(ChoixEtablissementResource.class);

    private static final String ENTITY_NAME = "choixEtablissement";

    private final ChoixEtablissementService choixEtablissementService;

    public ChoixEtablissementResource(ChoixEtablissementService choixEtablissementService) {
        this.choixEtablissementService = choixEtablissementService;
    }

    /**
     * POST  /choix-etablissements : Create a new choixEtablissement.
     *
     * @param choixEtablissementDTO the choixEtablissementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new choixEtablissementDTO, or with status 400 (Bad Request) if the choixEtablissement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/choix-etablissements")
    public ResponseEntity<ChoixEtablissementDTO> createChoixEtablissement(@RequestBody ChoixEtablissementDTO choixEtablissementDTO) throws URISyntaxException {
        log.debug("REST request to save ChoixEtablissement : {}", choixEtablissementDTO);
        if (choixEtablissementDTO.getId() != null) {
            throw new BadRequestAlertException("A new choixEtablissement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChoixEtablissementDTO result = choixEtablissementService.save(choixEtablissementDTO);
        return ResponseEntity.created(new URI("/api/choix-etablissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /choix-etablissements : Updates an existing choixEtablissement.
     *
     * @param choixEtablissementDTO the choixEtablissementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated choixEtablissementDTO,
     * or with status 400 (Bad Request) if the choixEtablissementDTO is not valid,
     * or with status 500 (Internal Server Error) if the choixEtablissementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/choix-etablissements")
    public ResponseEntity<ChoixEtablissementDTO> updateChoixEtablissement(@RequestBody ChoixEtablissementDTO choixEtablissementDTO) throws URISyntaxException {
        log.debug("REST request to update ChoixEtablissement : {}", choixEtablissementDTO);
        if (choixEtablissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChoixEtablissementDTO result = choixEtablissementService.save(choixEtablissementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, choixEtablissementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /choix-etablissements : get all the choixEtablissements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of choixEtablissements in body
     */
    @GetMapping("/choix-etablissements")
    public ResponseEntity<List<ChoixEtablissementDTO>> getAllChoixEtablissements(Pageable pageable) {
        log.debug("REST request to get a page of ChoixEtablissements");
        Page<ChoixEtablissementDTO> page = choixEtablissementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/choix-etablissements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /choix-etablissements/:id : get the "id" choixEtablissement.
     *
     * @param id the id of the choixEtablissementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the choixEtablissementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/choix-etablissements/{id}")
    public ResponseEntity<ChoixEtablissementDTO> getChoixEtablissement(@PathVariable Long id) {
        log.debug("REST request to get ChoixEtablissement : {}", id);
        Optional<ChoixEtablissementDTO> choixEtablissementDTO = choixEtablissementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(choixEtablissementDTO);
    }

    /**
     * DELETE  /choix-etablissements/:id : delete the "id" choixEtablissement.
     *
     * @param id the id of the choixEtablissementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/choix-etablissements/{id}")
    public ResponseEntity<Void> deleteChoixEtablissement(@PathVariable Long id) {
        log.debug("REST request to delete ChoixEtablissement : {}", id);
        choixEtablissementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
