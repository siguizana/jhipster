package com.test.web.rest;
import com.test.service.ProvinceService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.ProvinceDTO;
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
 * REST controller for managing Province.
 */
@RestController
@RequestMapping("/api")
public class ProvinceResource {

    private final Logger log = LoggerFactory.getLogger(ProvinceResource.class);

    private static final String ENTITY_NAME = "province";

    private final ProvinceService provinceService;

    public ProvinceResource(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    /**
     * POST  /provinces : Create a new province.
     *
     * @param provinceDTO the provinceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new provinceDTO, or with status 400 (Bad Request) if the province has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provinces")
    public ResponseEntity<ProvinceDTO> createProvince(@Valid @RequestBody ProvinceDTO provinceDTO) throws URISyntaxException {
        log.debug("REST request to save Province : {}", provinceDTO);
        if (provinceDTO.getId() != null) {
            throw new BadRequestAlertException("A new province cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvinceDTO result = provinceService.save(provinceDTO);
        return ResponseEntity.created(new URI("/api/provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provinces : Updates an existing province.
     *
     * @param provinceDTO the provinceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated provinceDTO,
     * or with status 400 (Bad Request) if the provinceDTO is not valid,
     * or with status 500 (Internal Server Error) if the provinceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provinces")
    public ResponseEntity<ProvinceDTO> updateProvince(@Valid @RequestBody ProvinceDTO provinceDTO) throws URISyntaxException {
        log.debug("REST request to update Province : {}", provinceDTO);
        if (provinceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProvinceDTO result = provinceService.save(provinceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, provinceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provinces : get all the provinces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of provinces in body
     */
    @GetMapping("/provinces")
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces(Pageable pageable) {
        log.debug("REST request to get a page of Provinces");
        Page<ProvinceDTO> page = provinceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provinces");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /provinces/:id : get the "id" province.
     *
     * @param id the id of the provinceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the provinceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/provinces/{id}")
    public ResponseEntity<ProvinceDTO> getProvince(@PathVariable Long id) {
        log.debug("REST request to get Province : {}", id);
        Optional<ProvinceDTO> provinceDTO = provinceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provinceDTO);
    }

    /**
     * DELETE  /provinces/:id : delete the "id" province.
     *
     * @param id the id of the provinceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provinces/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        log.debug("REST request to delete Province : {}", id);
        provinceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
