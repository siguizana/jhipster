package com.test.web.rest;
import com.test.service.EpreuveService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.EpreuveDTO;
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
 * REST controller for managing Epreuve.
 */
@RestController
@RequestMapping("/api")
public class EpreuveResource {

    private final Logger log = LoggerFactory.getLogger(EpreuveResource.class);

    private static final String ENTITY_NAME = "epreuve";

    private final EpreuveService epreuveService;

    public EpreuveResource(EpreuveService epreuveService) {
        this.epreuveService = epreuveService;
    }

    /**
     * POST  /epreuves : Create a new epreuve.
     *
     * @param epreuveDTO the epreuveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new epreuveDTO, or with status 400 (Bad Request) if the epreuve has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/epreuves")
    public ResponseEntity<EpreuveDTO> createEpreuve(@Valid @RequestBody EpreuveDTO epreuveDTO) throws URISyntaxException {
        log.debug("REST request to save Epreuve : {}", epreuveDTO);
        if (epreuveDTO.getId() != null) {
            throw new BadRequestAlertException("A new epreuve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EpreuveDTO result = epreuveService.save(epreuveDTO);
        return ResponseEntity.created(new URI("/api/epreuves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /epreuves : Updates an existing epreuve.
     *
     * @param epreuveDTO the epreuveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated epreuveDTO,
     * or with status 400 (Bad Request) if the epreuveDTO is not valid,
     * or with status 500 (Internal Server Error) if the epreuveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/epreuves")
    public ResponseEntity<EpreuveDTO> updateEpreuve(@Valid @RequestBody EpreuveDTO epreuveDTO) throws URISyntaxException {
        log.debug("REST request to update Epreuve : {}", epreuveDTO);
        if (epreuveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EpreuveDTO result = epreuveService.save(epreuveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, epreuveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /epreuves : get all the epreuves.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of epreuves in body
     */
    @GetMapping("/epreuves")
    public ResponseEntity<List<EpreuveDTO>> getAllEpreuves(Pageable pageable) {
        log.debug("REST request to get a page of Epreuves");
        Page<EpreuveDTO> page = epreuveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/epreuves");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /epreuves/:id : get the "id" epreuve.
     *
     * @param id the id of the epreuveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the epreuveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/epreuves/{id}")
    public ResponseEntity<EpreuveDTO> getEpreuve(@PathVariable Long id) {
        log.debug("REST request to get Epreuve : {}", id);
        Optional<EpreuveDTO> epreuveDTO = epreuveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(epreuveDTO);
    }

    /**
     * DELETE  /epreuves/:id : delete the "id" epreuve.
     *
     * @param id the id of the epreuveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/epreuves/{id}")
    public ResponseEntity<Void> deleteEpreuve(@PathVariable Long id) {
        log.debug("REST request to delete Epreuve : {}", id);
        epreuveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
