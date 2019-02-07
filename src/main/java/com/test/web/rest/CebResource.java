package com.test.web.rest;
import com.test.service.CebService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CebDTO;
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
 * REST controller for managing Ceb.
 */
@RestController
@RequestMapping("/api")
public class CebResource {

    private final Logger log = LoggerFactory.getLogger(CebResource.class);

    private static final String ENTITY_NAME = "ceb";

    private final CebService cebService;

    public CebResource(CebService cebService) {
        this.cebService = cebService;
    }

    /**
     * POST  /cebs : Create a new ceb.
     *
     * @param cebDTO the cebDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cebDTO, or with status 400 (Bad Request) if the ceb has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cebs")
    public ResponseEntity<CebDTO> createCeb(@Valid @RequestBody CebDTO cebDTO) throws URISyntaxException {
        log.debug("REST request to save Ceb : {}", cebDTO);
        if (cebDTO.getId() != null) {
            throw new BadRequestAlertException("A new ceb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CebDTO result = cebService.save(cebDTO);
        return ResponseEntity.created(new URI("/api/cebs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cebs : Updates an existing ceb.
     *
     * @param cebDTO the cebDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cebDTO,
     * or with status 400 (Bad Request) if the cebDTO is not valid,
     * or with status 500 (Internal Server Error) if the cebDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cebs")
    public ResponseEntity<CebDTO> updateCeb(@Valid @RequestBody CebDTO cebDTO) throws URISyntaxException {
        log.debug("REST request to update Ceb : {}", cebDTO);
        if (cebDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CebDTO result = cebService.save(cebDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cebDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cebs : get all the cebs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cebs in body
     */
    @GetMapping("/cebs")
    public ResponseEntity<List<CebDTO>> getAllCebs(Pageable pageable) {
        log.debug("REST request to get a page of Cebs");
        Page<CebDTO> page = cebService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cebs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cebs/:id : get the "id" ceb.
     *
     * @param id the id of the cebDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cebDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cebs/{id}")
    public ResponseEntity<CebDTO> getCeb(@PathVariable Long id) {
        log.debug("REST request to get Ceb : {}", id);
        Optional<CebDTO> cebDTO = cebService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cebDTO);
    }

    /**
     * DELETE  /cebs/:id : delete the "id" ceb.
     *
     * @param id the id of the cebDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cebs/{id}")
    public ResponseEntity<Void> deleteCeb(@PathVariable Long id) {
        log.debug("REST request to delete Ceb : {}", id);
        cebService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
