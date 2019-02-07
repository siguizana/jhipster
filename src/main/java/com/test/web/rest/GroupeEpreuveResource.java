package com.test.web.rest;
import com.test.service.GroupeEpreuveService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.GroupeEpreuveDTO;
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
 * REST controller for managing GroupeEpreuve.
 */
@RestController
@RequestMapping("/api")
public class GroupeEpreuveResource {

    private final Logger log = LoggerFactory.getLogger(GroupeEpreuveResource.class);

    private static final String ENTITY_NAME = "groupeEpreuve";

    private final GroupeEpreuveService groupeEpreuveService;

    public GroupeEpreuveResource(GroupeEpreuveService groupeEpreuveService) {
        this.groupeEpreuveService = groupeEpreuveService;
    }

    /**
     * POST  /groupe-epreuves : Create a new groupeEpreuve.
     *
     * @param groupeEpreuveDTO the groupeEpreuveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupeEpreuveDTO, or with status 400 (Bad Request) if the groupeEpreuve has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groupe-epreuves")
    public ResponseEntity<GroupeEpreuveDTO> createGroupeEpreuve(@Valid @RequestBody GroupeEpreuveDTO groupeEpreuveDTO) throws URISyntaxException {
        log.debug("REST request to save GroupeEpreuve : {}", groupeEpreuveDTO);
        if (groupeEpreuveDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupeEpreuve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupeEpreuveDTO result = groupeEpreuveService.save(groupeEpreuveDTO);
        return ResponseEntity.created(new URI("/api/groupe-epreuves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /groupe-epreuves : Updates an existing groupeEpreuve.
     *
     * @param groupeEpreuveDTO the groupeEpreuveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupeEpreuveDTO,
     * or with status 400 (Bad Request) if the groupeEpreuveDTO is not valid,
     * or with status 500 (Internal Server Error) if the groupeEpreuveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/groupe-epreuves")
    public ResponseEntity<GroupeEpreuveDTO> updateGroupeEpreuve(@Valid @RequestBody GroupeEpreuveDTO groupeEpreuveDTO) throws URISyntaxException {
        log.debug("REST request to update GroupeEpreuve : {}", groupeEpreuveDTO);
        if (groupeEpreuveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupeEpreuveDTO result = groupeEpreuveService.save(groupeEpreuveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupeEpreuveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /groupe-epreuves : get all the groupeEpreuves.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of groupeEpreuves in body
     */
    @GetMapping("/groupe-epreuves")
    public ResponseEntity<List<GroupeEpreuveDTO>> getAllGroupeEpreuves(Pageable pageable) {
        log.debug("REST request to get a page of GroupeEpreuves");
        Page<GroupeEpreuveDTO> page = groupeEpreuveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/groupe-epreuves");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /groupe-epreuves/:id : get the "id" groupeEpreuve.
     *
     * @param id the id of the groupeEpreuveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupeEpreuveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/groupe-epreuves/{id}")
    public ResponseEntity<GroupeEpreuveDTO> getGroupeEpreuve(@PathVariable Long id) {
        log.debug("REST request to get GroupeEpreuve : {}", id);
        Optional<GroupeEpreuveDTO> groupeEpreuveDTO = groupeEpreuveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupeEpreuveDTO);
    }

    /**
     * DELETE  /groupe-epreuves/:id : delete the "id" groupeEpreuve.
     *
     * @param id the id of the groupeEpreuveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/groupe-epreuves/{id}")
    public ResponseEntity<Void> deleteGroupeEpreuve(@PathVariable Long id) {
        log.debug("REST request to delete GroupeEpreuve : {}", id);
        groupeEpreuveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
