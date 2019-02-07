package com.test.web.rest;
import com.test.service.ZoneExamenService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.ZoneExamenDTO;
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
 * REST controller for managing ZoneExamen.
 */
@RestController
@RequestMapping("/api")
public class ZoneExamenResource {

    private final Logger log = LoggerFactory.getLogger(ZoneExamenResource.class);

    private static final String ENTITY_NAME = "zoneExamen";

    private final ZoneExamenService zoneExamenService;

    public ZoneExamenResource(ZoneExamenService zoneExamenService) {
        this.zoneExamenService = zoneExamenService;
    }

    /**
     * POST  /zone-examen : Create a new zoneExamen.
     *
     * @param zoneExamenDTO the zoneExamenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zoneExamenDTO, or with status 400 (Bad Request) if the zoneExamen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zone-examen")
    public ResponseEntity<ZoneExamenDTO> createZoneExamen(@Valid @RequestBody ZoneExamenDTO zoneExamenDTO) throws URISyntaxException {
        log.debug("REST request to save ZoneExamen : {}", zoneExamenDTO);
        if (zoneExamenDTO.getId() != null) {
            throw new BadRequestAlertException("A new zoneExamen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZoneExamenDTO result = zoneExamenService.save(zoneExamenDTO);
        return ResponseEntity.created(new URI("/api/zone-examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zone-examen : Updates an existing zoneExamen.
     *
     * @param zoneExamenDTO the zoneExamenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zoneExamenDTO,
     * or with status 400 (Bad Request) if the zoneExamenDTO is not valid,
     * or with status 500 (Internal Server Error) if the zoneExamenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zone-examen")
    public ResponseEntity<ZoneExamenDTO> updateZoneExamen(@Valid @RequestBody ZoneExamenDTO zoneExamenDTO) throws URISyntaxException {
        log.debug("REST request to update ZoneExamen : {}", zoneExamenDTO);
        if (zoneExamenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZoneExamenDTO result = zoneExamenService.save(zoneExamenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zoneExamenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zone-examen : get all the zoneExamen.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of zoneExamen in body
     */
    @GetMapping("/zone-examen")
    public ResponseEntity<List<ZoneExamenDTO>> getAllZoneExamen(Pageable pageable) {
        log.debug("REST request to get a page of ZoneExamen");
        Page<ZoneExamenDTO> page = zoneExamenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zone-examen");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /zone-examen/:id : get the "id" zoneExamen.
     *
     * @param id the id of the zoneExamenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zoneExamenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zone-examen/{id}")
    public ResponseEntity<ZoneExamenDTO> getZoneExamen(@PathVariable Long id) {
        log.debug("REST request to get ZoneExamen : {}", id);
        Optional<ZoneExamenDTO> zoneExamenDTO = zoneExamenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zoneExamenDTO);
    }

    /**
     * DELETE  /zone-examen/:id : delete the "id" zoneExamen.
     *
     * @param id the id of the zoneExamenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zone-examen/{id}")
    public ResponseEntity<Void> deleteZoneExamen(@PathVariable Long id) {
        log.debug("REST request to delete ZoneExamen : {}", id);
        zoneExamenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
