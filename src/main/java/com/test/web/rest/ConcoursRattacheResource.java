package com.test.web.rest;
import com.test.service.ConcoursRattacheService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.ConcoursRattacheDTO;
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
 * REST controller for managing ConcoursRattache.
 */
@RestController
@RequestMapping("/api")
public class ConcoursRattacheResource {

    private final Logger log = LoggerFactory.getLogger(ConcoursRattacheResource.class);

    private static final String ENTITY_NAME = "concoursRattache";

    private final ConcoursRattacheService concoursRattacheService;

    public ConcoursRattacheResource(ConcoursRattacheService concoursRattacheService) {
        this.concoursRattacheService = concoursRattacheService;
    }

    /**
     * POST  /concours-rattaches : Create a new concoursRattache.
     *
     * @param concoursRattacheDTO the concoursRattacheDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new concoursRattacheDTO, or with status 400 (Bad Request) if the concoursRattache has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/concours-rattaches")
    public ResponseEntity<ConcoursRattacheDTO> createConcoursRattache(@Valid @RequestBody ConcoursRattacheDTO concoursRattacheDTO) throws URISyntaxException {
        log.debug("REST request to save ConcoursRattache : {}", concoursRattacheDTO);
        if (concoursRattacheDTO.getId() != null) {
            throw new BadRequestAlertException("A new concoursRattache cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConcoursRattacheDTO result = concoursRattacheService.save(concoursRattacheDTO);
        return ResponseEntity.created(new URI("/api/concours-rattaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concours-rattaches : Updates an existing concoursRattache.
     *
     * @param concoursRattacheDTO the concoursRattacheDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated concoursRattacheDTO,
     * or with status 400 (Bad Request) if the concoursRattacheDTO is not valid,
     * or with status 500 (Internal Server Error) if the concoursRattacheDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/concours-rattaches")
    public ResponseEntity<ConcoursRattacheDTO> updateConcoursRattache(@Valid @RequestBody ConcoursRattacheDTO concoursRattacheDTO) throws URISyntaxException {
        log.debug("REST request to update ConcoursRattache : {}", concoursRattacheDTO);
        if (concoursRattacheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConcoursRattacheDTO result = concoursRattacheService.save(concoursRattacheDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, concoursRattacheDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /concours-rattaches : get all the concoursRattaches.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of concoursRattaches in body
     */
    @GetMapping("/concours-rattaches")
    public ResponseEntity<List<ConcoursRattacheDTO>> getAllConcoursRattaches(Pageable pageable) {
        log.debug("REST request to get a page of ConcoursRattaches");
        Page<ConcoursRattacheDTO> page = concoursRattacheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/concours-rattaches");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /concours-rattaches/:id : get the "id" concoursRattache.
     *
     * @param id the id of the concoursRattacheDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the concoursRattacheDTO, or with status 404 (Not Found)
     */
    @GetMapping("/concours-rattaches/{id}")
    public ResponseEntity<ConcoursRattacheDTO> getConcoursRattache(@PathVariable Long id) {
        log.debug("REST request to get ConcoursRattache : {}", id);
        Optional<ConcoursRattacheDTO> concoursRattacheDTO = concoursRattacheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concoursRattacheDTO);
    }

    /**
     * DELETE  /concours-rattaches/:id : delete the "id" concoursRattache.
     *
     * @param id the id of the concoursRattacheDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/concours-rattaches/{id}")
    public ResponseEntity<Void> deleteConcoursRattache(@PathVariable Long id) {
        log.debug("REST request to delete ConcoursRattache : {}", id);
        concoursRattacheService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
