package com.test.web.rest;
import com.test.service.JuryService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.JuryDTO;
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
 * REST controller for managing Jury.
 */
@RestController
@RequestMapping("/api")
public class JuryResource {

    private final Logger log = LoggerFactory.getLogger(JuryResource.class);

    private static final String ENTITY_NAME = "jury";

    private final JuryService juryService;

    public JuryResource(JuryService juryService) {
        this.juryService = juryService;
    }

    /**
     * POST  /juries : Create a new jury.
     *
     * @param juryDTO the juryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new juryDTO, or with status 400 (Bad Request) if the jury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/juries")
    public ResponseEntity<JuryDTO> createJury(@Valid @RequestBody JuryDTO juryDTO) throws URISyntaxException {
        log.debug("REST request to save Jury : {}", juryDTO);
        if (juryDTO.getId() != null) {
            throw new BadRequestAlertException("A new jury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JuryDTO result = juryService.save(juryDTO);
        return ResponseEntity.created(new URI("/api/juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /juries : Updates an existing jury.
     *
     * @param juryDTO the juryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated juryDTO,
     * or with status 400 (Bad Request) if the juryDTO is not valid,
     * or with status 500 (Internal Server Error) if the juryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/juries")
    public ResponseEntity<JuryDTO> updateJury(@Valid @RequestBody JuryDTO juryDTO) throws URISyntaxException {
        log.debug("REST request to update Jury : {}", juryDTO);
        if (juryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JuryDTO result = juryService.save(juryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, juryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /juries : get all the juries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of juries in body
     */
    @GetMapping("/juries")
    public ResponseEntity<List<JuryDTO>> getAllJuries(Pageable pageable) {
        log.debug("REST request to get a page of Juries");
        Page<JuryDTO> page = juryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/juries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /juries/:id : get the "id" jury.
     *
     * @param id the id of the juryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the juryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/juries/{id}")
    public ResponseEntity<JuryDTO> getJury(@PathVariable Long id) {
        log.debug("REST request to get Jury : {}", id);
        Optional<JuryDTO> juryDTO = juryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(juryDTO);
    }

    /**
     * DELETE  /juries/:id : delete the "id" jury.
     *
     * @param id the id of the juryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/juries/{id}")
    public ResponseEntity<Void> deleteJury(@PathVariable Long id) {
        log.debug("REST request to delete Jury : {}", id);
        juryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
