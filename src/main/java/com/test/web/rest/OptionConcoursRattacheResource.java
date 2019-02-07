package com.test.web.rest;
import com.test.service.OptionConcoursRattacheService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.OptionConcoursRattacheDTO;
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
 * REST controller for managing OptionConcoursRattache.
 */
@RestController
@RequestMapping("/api")
public class OptionConcoursRattacheResource {

    private final Logger log = LoggerFactory.getLogger(OptionConcoursRattacheResource.class);

    private static final String ENTITY_NAME = "optionConcoursRattache";

    private final OptionConcoursRattacheService optionConcoursRattacheService;

    public OptionConcoursRattacheResource(OptionConcoursRattacheService optionConcoursRattacheService) {
        this.optionConcoursRattacheService = optionConcoursRattacheService;
    }

    /**
     * POST  /option-concours-rattaches : Create a new optionConcoursRattache.
     *
     * @param optionConcoursRattacheDTO the optionConcoursRattacheDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new optionConcoursRattacheDTO, or with status 400 (Bad Request) if the optionConcoursRattache has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/option-concours-rattaches")
    public ResponseEntity<OptionConcoursRattacheDTO> createOptionConcoursRattache(@Valid @RequestBody OptionConcoursRattacheDTO optionConcoursRattacheDTO) throws URISyntaxException {
        log.debug("REST request to save OptionConcoursRattache : {}", optionConcoursRattacheDTO);
        if (optionConcoursRattacheDTO.getId() != null) {
            throw new BadRequestAlertException("A new optionConcoursRattache cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionConcoursRattacheDTO result = optionConcoursRattacheService.save(optionConcoursRattacheDTO);
        return ResponseEntity.created(new URI("/api/option-concours-rattaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /option-concours-rattaches : Updates an existing optionConcoursRattache.
     *
     * @param optionConcoursRattacheDTO the optionConcoursRattacheDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated optionConcoursRattacheDTO,
     * or with status 400 (Bad Request) if the optionConcoursRattacheDTO is not valid,
     * or with status 500 (Internal Server Error) if the optionConcoursRattacheDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/option-concours-rattaches")
    public ResponseEntity<OptionConcoursRattacheDTO> updateOptionConcoursRattache(@Valid @RequestBody OptionConcoursRattacheDTO optionConcoursRattacheDTO) throws URISyntaxException {
        log.debug("REST request to update OptionConcoursRattache : {}", optionConcoursRattacheDTO);
        if (optionConcoursRattacheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptionConcoursRattacheDTO result = optionConcoursRattacheService.save(optionConcoursRattacheDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, optionConcoursRattacheDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /option-concours-rattaches : get all the optionConcoursRattaches.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of optionConcoursRattaches in body
     */
    @GetMapping("/option-concours-rattaches")
    public ResponseEntity<List<OptionConcoursRattacheDTO>> getAllOptionConcoursRattaches(Pageable pageable) {
        log.debug("REST request to get a page of OptionConcoursRattaches");
        Page<OptionConcoursRattacheDTO> page = optionConcoursRattacheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/option-concours-rattaches");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /option-concours-rattaches/:id : get the "id" optionConcoursRattache.
     *
     * @param id the id of the optionConcoursRattacheDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the optionConcoursRattacheDTO, or with status 404 (Not Found)
     */
    @GetMapping("/option-concours-rattaches/{id}")
    public ResponseEntity<OptionConcoursRattacheDTO> getOptionConcoursRattache(@PathVariable Long id) {
        log.debug("REST request to get OptionConcoursRattache : {}", id);
        Optional<OptionConcoursRattacheDTO> optionConcoursRattacheDTO = optionConcoursRattacheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionConcoursRattacheDTO);
    }

    /**
     * DELETE  /option-concours-rattaches/:id : delete the "id" optionConcoursRattache.
     *
     * @param id the id of the optionConcoursRattacheDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/option-concours-rattaches/{id}")
    public ResponseEntity<Void> deleteOptionConcoursRattache(@PathVariable Long id) {
        log.debug("REST request to delete OptionConcoursRattache : {}", id);
        optionConcoursRattacheService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
