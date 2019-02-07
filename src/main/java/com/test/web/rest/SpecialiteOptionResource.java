package com.test.web.rest;
import com.test.service.SpecialiteOptionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.SpecialiteOptionDTO;
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
 * REST controller for managing SpecialiteOption.
 */
@RestController
@RequestMapping("/api")
public class SpecialiteOptionResource {

    private final Logger log = LoggerFactory.getLogger(SpecialiteOptionResource.class);

    private static final String ENTITY_NAME = "specialiteOption";

    private final SpecialiteOptionService specialiteOptionService;

    public SpecialiteOptionResource(SpecialiteOptionService specialiteOptionService) {
        this.specialiteOptionService = specialiteOptionService;
    }

    /**
     * POST  /specialite-options : Create a new specialiteOption.
     *
     * @param specialiteOptionDTO the specialiteOptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new specialiteOptionDTO, or with status 400 (Bad Request) if the specialiteOption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/specialite-options")
    public ResponseEntity<SpecialiteOptionDTO> createSpecialiteOption(@Valid @RequestBody SpecialiteOptionDTO specialiteOptionDTO) throws URISyntaxException {
        log.debug("REST request to save SpecialiteOption : {}", specialiteOptionDTO);
        if (specialiteOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new specialiteOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialiteOptionDTO result = specialiteOptionService.save(specialiteOptionDTO);
        return ResponseEntity.created(new URI("/api/specialite-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /specialite-options : Updates an existing specialiteOption.
     *
     * @param specialiteOptionDTO the specialiteOptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated specialiteOptionDTO,
     * or with status 400 (Bad Request) if the specialiteOptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the specialiteOptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/specialite-options")
    public ResponseEntity<SpecialiteOptionDTO> updateSpecialiteOption(@Valid @RequestBody SpecialiteOptionDTO specialiteOptionDTO) throws URISyntaxException {
        log.debug("REST request to update SpecialiteOption : {}", specialiteOptionDTO);
        if (specialiteOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpecialiteOptionDTO result = specialiteOptionService.save(specialiteOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, specialiteOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /specialite-options : get all the specialiteOptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of specialiteOptions in body
     */
    @GetMapping("/specialite-options")
    public ResponseEntity<List<SpecialiteOptionDTO>> getAllSpecialiteOptions(Pageable pageable) {
        log.debug("REST request to get a page of SpecialiteOptions");
        Page<SpecialiteOptionDTO> page = specialiteOptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/specialite-options");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /specialite-options/:id : get the "id" specialiteOption.
     *
     * @param id the id of the specialiteOptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the specialiteOptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/specialite-options/{id}")
    public ResponseEntity<SpecialiteOptionDTO> getSpecialiteOption(@PathVariable Long id) {
        log.debug("REST request to get SpecialiteOption : {}", id);
        Optional<SpecialiteOptionDTO> specialiteOptionDTO = specialiteOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialiteOptionDTO);
    }

    /**
     * DELETE  /specialite-options/:id : delete the "id" specialiteOption.
     *
     * @param id the id of the specialiteOptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/specialite-options/{id}")
    public ResponseEntity<Void> deleteSpecialiteOption(@PathVariable Long id) {
        log.debug("REST request to delete SpecialiteOption : {}", id);
        specialiteOptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
