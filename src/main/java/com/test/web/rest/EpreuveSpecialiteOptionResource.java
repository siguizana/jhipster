package com.test.web.rest;
import com.test.service.EpreuveSpecialiteOptionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.EpreuveSpecialiteOptionDTO;
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
 * REST controller for managing EpreuveSpecialiteOption.
 */
@RestController
@RequestMapping("/api")
public class EpreuveSpecialiteOptionResource {

    private final Logger log = LoggerFactory.getLogger(EpreuveSpecialiteOptionResource.class);

    private static final String ENTITY_NAME = "epreuveSpecialiteOption";

    private final EpreuveSpecialiteOptionService epreuveSpecialiteOptionService;

    public EpreuveSpecialiteOptionResource(EpreuveSpecialiteOptionService epreuveSpecialiteOptionService) {
        this.epreuveSpecialiteOptionService = epreuveSpecialiteOptionService;
    }

    /**
     * POST  /epreuve-specialite-options : Create a new epreuveSpecialiteOption.
     *
     * @param epreuveSpecialiteOptionDTO the epreuveSpecialiteOptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new epreuveSpecialiteOptionDTO, or with status 400 (Bad Request) if the epreuveSpecialiteOption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/epreuve-specialite-options")
    public ResponseEntity<EpreuveSpecialiteOptionDTO> createEpreuveSpecialiteOption(@Valid @RequestBody EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO) throws URISyntaxException {
        log.debug("REST request to save EpreuveSpecialiteOption : {}", epreuveSpecialiteOptionDTO);
        if (epreuveSpecialiteOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new epreuveSpecialiteOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EpreuveSpecialiteOptionDTO result = epreuveSpecialiteOptionService.save(epreuveSpecialiteOptionDTO);
        return ResponseEntity.created(new URI("/api/epreuve-specialite-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /epreuve-specialite-options : Updates an existing epreuveSpecialiteOption.
     *
     * @param epreuveSpecialiteOptionDTO the epreuveSpecialiteOptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated epreuveSpecialiteOptionDTO,
     * or with status 400 (Bad Request) if the epreuveSpecialiteOptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the epreuveSpecialiteOptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/epreuve-specialite-options")
    public ResponseEntity<EpreuveSpecialiteOptionDTO> updateEpreuveSpecialiteOption(@Valid @RequestBody EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO) throws URISyntaxException {
        log.debug("REST request to update EpreuveSpecialiteOption : {}", epreuveSpecialiteOptionDTO);
        if (epreuveSpecialiteOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EpreuveSpecialiteOptionDTO result = epreuveSpecialiteOptionService.save(epreuveSpecialiteOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, epreuveSpecialiteOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /epreuve-specialite-options : get all the epreuveSpecialiteOptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of epreuveSpecialiteOptions in body
     */
    @GetMapping("/epreuve-specialite-options")
    public ResponseEntity<List<EpreuveSpecialiteOptionDTO>> getAllEpreuveSpecialiteOptions(Pageable pageable) {
        log.debug("REST request to get a page of EpreuveSpecialiteOptions");
        Page<EpreuveSpecialiteOptionDTO> page = epreuveSpecialiteOptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/epreuve-specialite-options");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /epreuve-specialite-options/:id : get the "id" epreuveSpecialiteOption.
     *
     * @param id the id of the epreuveSpecialiteOptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the epreuveSpecialiteOptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/epreuve-specialite-options/{id}")
    public ResponseEntity<EpreuveSpecialiteOptionDTO> getEpreuveSpecialiteOption(@PathVariable Long id) {
        log.debug("REST request to get EpreuveSpecialiteOption : {}", id);
        Optional<EpreuveSpecialiteOptionDTO> epreuveSpecialiteOptionDTO = epreuveSpecialiteOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(epreuveSpecialiteOptionDTO);
    }

    /**
     * DELETE  /epreuve-specialite-options/:id : delete the "id" epreuveSpecialiteOption.
     *
     * @param id the id of the epreuveSpecialiteOptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/epreuve-specialite-options/{id}")
    public ResponseEntity<Void> deleteEpreuveSpecialiteOption(@PathVariable Long id) {
        log.debug("REST request to delete EpreuveSpecialiteOption : {}", id);
        epreuveSpecialiteOptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
