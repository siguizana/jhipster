package com.test.web.rest;
import com.test.service.CommissionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CommissionDTO;
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
 * REST controller for managing Commission.
 */
@RestController
@RequestMapping("/api")
public class CommissionResource {

    private final Logger log = LoggerFactory.getLogger(CommissionResource.class);

    private static final String ENTITY_NAME = "commission";

    private final CommissionService commissionService;

    public CommissionResource(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    /**
     * POST  /commissions : Create a new commission.
     *
     * @param commissionDTO the commissionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commissionDTO, or with status 400 (Bad Request) if the commission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commissions")
    public ResponseEntity<CommissionDTO> createCommission(@Valid @RequestBody CommissionDTO commissionDTO) throws URISyntaxException {
        log.debug("REST request to save Commission : {}", commissionDTO);
        if (commissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new commission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommissionDTO result = commissionService.save(commissionDTO);
        return ResponseEntity.created(new URI("/api/commissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commissions : Updates an existing commission.
     *
     * @param commissionDTO the commissionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commissionDTO,
     * or with status 400 (Bad Request) if the commissionDTO is not valid,
     * or with status 500 (Internal Server Error) if the commissionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commissions")
    public ResponseEntity<CommissionDTO> updateCommission(@Valid @RequestBody CommissionDTO commissionDTO) throws URISyntaxException {
        log.debug("REST request to update Commission : {}", commissionDTO);
        if (commissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommissionDTO result = commissionService.save(commissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commissions : get all the commissions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commissions in body
     */
    @GetMapping("/commissions")
    public ResponseEntity<List<CommissionDTO>> getAllCommissions(Pageable pageable) {
        log.debug("REST request to get a page of Commissions");
        Page<CommissionDTO> page = commissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commissions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /commissions/:id : get the "id" commission.
     *
     * @param id the id of the commissionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commissionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commissions/{id}")
    public ResponseEntity<CommissionDTO> getCommission(@PathVariable Long id) {
        log.debug("REST request to get Commission : {}", id);
        Optional<CommissionDTO> commissionDTO = commissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commissionDTO);
    }

    /**
     * DELETE  /commissions/:id : delete the "id" commission.
     *
     * @param id the id of the commissionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commissions/{id}")
    public ResponseEntity<Void> deleteCommission(@PathVariable Long id) {
        log.debug("REST request to delete Commission : {}", id);
        commissionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
