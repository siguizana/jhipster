package com.test.web.rest;
import com.test.service.EtapeExamenService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.EtapeExamenDTO;
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
 * REST controller for managing EtapeExamen.
 */
@RestController
@RequestMapping("/api")
public class EtapeExamenResource {

    private final Logger log = LoggerFactory.getLogger(EtapeExamenResource.class);

    private static final String ENTITY_NAME = "etapeExamen";

    private final EtapeExamenService etapeExamenService;

    public EtapeExamenResource(EtapeExamenService etapeExamenService) {
        this.etapeExamenService = etapeExamenService;
    }

    /**
     * POST  /etape-examen : Create a new etapeExamen.
     *
     * @param etapeExamenDTO the etapeExamenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etapeExamenDTO, or with status 400 (Bad Request) if the etapeExamen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etape-examen")
    public ResponseEntity<EtapeExamenDTO> createEtapeExamen(@Valid @RequestBody EtapeExamenDTO etapeExamenDTO) throws URISyntaxException {
        log.debug("REST request to save EtapeExamen : {}", etapeExamenDTO);
        if (etapeExamenDTO.getId() != null) {
            throw new BadRequestAlertException("A new etapeExamen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtapeExamenDTO result = etapeExamenService.save(etapeExamenDTO);
        return ResponseEntity.created(new URI("/api/etape-examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etape-examen : Updates an existing etapeExamen.
     *
     * @param etapeExamenDTO the etapeExamenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etapeExamenDTO,
     * or with status 400 (Bad Request) if the etapeExamenDTO is not valid,
     * or with status 500 (Internal Server Error) if the etapeExamenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etape-examen")
    public ResponseEntity<EtapeExamenDTO> updateEtapeExamen(@Valid @RequestBody EtapeExamenDTO etapeExamenDTO) throws URISyntaxException {
        log.debug("REST request to update EtapeExamen : {}", etapeExamenDTO);
        if (etapeExamenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtapeExamenDTO result = etapeExamenService.save(etapeExamenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etapeExamenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etape-examen : get all the etapeExamen.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of etapeExamen in body
     */
    @GetMapping("/etape-examen")
    public ResponseEntity<List<EtapeExamenDTO>> getAllEtapeExamen(Pageable pageable) {
        log.debug("REST request to get a page of EtapeExamen");
        Page<EtapeExamenDTO> page = etapeExamenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etape-examen");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /etape-examen/:id : get the "id" etapeExamen.
     *
     * @param id the id of the etapeExamenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etapeExamenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/etape-examen/{id}")
    public ResponseEntity<EtapeExamenDTO> getEtapeExamen(@PathVariable Long id) {
        log.debug("REST request to get EtapeExamen : {}", id);
        Optional<EtapeExamenDTO> etapeExamenDTO = etapeExamenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etapeExamenDTO);
    }

    /**
     * DELETE  /etape-examen/:id : delete the "id" etapeExamen.
     *
     * @param id the id of the etapeExamenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etape-examen/{id}")
    public ResponseEntity<Void> deleteEtapeExamen(@PathVariable Long id) {
        log.debug("REST request to delete EtapeExamen : {}", id);
        etapeExamenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
