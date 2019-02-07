package com.test.web.rest;
import com.test.service.CommuneService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CommuneDTO;
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
 * REST controller for managing Commune.
 */
@RestController
@RequestMapping("/api")
public class CommuneResource {

    private final Logger log = LoggerFactory.getLogger(CommuneResource.class);

    private static final String ENTITY_NAME = "commune";

    private final CommuneService communeService;

    public CommuneResource(CommuneService communeService) {
        this.communeService = communeService;
    }

    /**
     * POST  /communes : Create a new commune.
     *
     * @param communeDTO the communeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new communeDTO, or with status 400 (Bad Request) if the commune has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/communes")
    public ResponseEntity<CommuneDTO> createCommune(@Valid @RequestBody CommuneDTO communeDTO) throws URISyntaxException {
        log.debug("REST request to save Commune : {}", communeDTO);
        if (communeDTO.getId() != null) {
            throw new BadRequestAlertException("A new commune cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommuneDTO result = communeService.save(communeDTO);
        return ResponseEntity.created(new URI("/api/communes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /communes : Updates an existing commune.
     *
     * @param communeDTO the communeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated communeDTO,
     * or with status 400 (Bad Request) if the communeDTO is not valid,
     * or with status 500 (Internal Server Error) if the communeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/communes")
    public ResponseEntity<CommuneDTO> updateCommune(@Valid @RequestBody CommuneDTO communeDTO) throws URISyntaxException {
        log.debug("REST request to update Commune : {}", communeDTO);
        if (communeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommuneDTO result = communeService.save(communeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, communeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /communes : get all the communes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of communes in body
     */
    @GetMapping("/communes")
    public ResponseEntity<List<CommuneDTO>> getAllCommunes(Pageable pageable) {
        log.debug("REST request to get a page of Communes");
        Page<CommuneDTO> page = communeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/communes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /communes/:id : get the "id" commune.
     *
     * @param id the id of the communeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the communeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/communes/{id}")
    public ResponseEntity<CommuneDTO> getCommune(@PathVariable Long id) {
        log.debug("REST request to get Commune : {}", id);
        Optional<CommuneDTO> communeDTO = communeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(communeDTO);
    }

    /**
     * DELETE  /communes/:id : delete the "id" commune.
     *
     * @param id the id of the communeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/communes/{id}")
    public ResponseEntity<Void> deleteCommune(@PathVariable Long id) {
        log.debug("REST request to delete Commune : {}", id);
        communeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
