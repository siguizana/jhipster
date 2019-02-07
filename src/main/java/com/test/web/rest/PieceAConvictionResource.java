package com.test.web.rest;
import com.test.service.PieceAConvictionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.PieceAConvictionDTO;
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
 * REST controller for managing PieceAConviction.
 */
@RestController
@RequestMapping("/api")
public class PieceAConvictionResource {

    private final Logger log = LoggerFactory.getLogger(PieceAConvictionResource.class);

    private static final String ENTITY_NAME = "pieceAConviction";

    private final PieceAConvictionService pieceAConvictionService;

    public PieceAConvictionResource(PieceAConvictionService pieceAConvictionService) {
        this.pieceAConvictionService = pieceAConvictionService;
    }

    /**
     * POST  /piece-a-convictions : Create a new pieceAConviction.
     *
     * @param pieceAConvictionDTO the pieceAConvictionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pieceAConvictionDTO, or with status 400 (Bad Request) if the pieceAConviction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/piece-a-convictions")
    public ResponseEntity<PieceAConvictionDTO> createPieceAConviction(@Valid @RequestBody PieceAConvictionDTO pieceAConvictionDTO) throws URISyntaxException {
        log.debug("REST request to save PieceAConviction : {}", pieceAConvictionDTO);
        if (pieceAConvictionDTO.getId() != null) {
            throw new BadRequestAlertException("A new pieceAConviction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PieceAConvictionDTO result = pieceAConvictionService.save(pieceAConvictionDTO);
        return ResponseEntity.created(new URI("/api/piece-a-convictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /piece-a-convictions : Updates an existing pieceAConviction.
     *
     * @param pieceAConvictionDTO the pieceAConvictionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pieceAConvictionDTO,
     * or with status 400 (Bad Request) if the pieceAConvictionDTO is not valid,
     * or with status 500 (Internal Server Error) if the pieceAConvictionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/piece-a-convictions")
    public ResponseEntity<PieceAConvictionDTO> updatePieceAConviction(@Valid @RequestBody PieceAConvictionDTO pieceAConvictionDTO) throws URISyntaxException {
        log.debug("REST request to update PieceAConviction : {}", pieceAConvictionDTO);
        if (pieceAConvictionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PieceAConvictionDTO result = pieceAConvictionService.save(pieceAConvictionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pieceAConvictionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /piece-a-convictions : get all the pieceAConvictions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pieceAConvictions in body
     */
    @GetMapping("/piece-a-convictions")
    public ResponseEntity<List<PieceAConvictionDTO>> getAllPieceAConvictions(Pageable pageable) {
        log.debug("REST request to get a page of PieceAConvictions");
        Page<PieceAConvictionDTO> page = pieceAConvictionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/piece-a-convictions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /piece-a-convictions/:id : get the "id" pieceAConviction.
     *
     * @param id the id of the pieceAConvictionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pieceAConvictionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/piece-a-convictions/{id}")
    public ResponseEntity<PieceAConvictionDTO> getPieceAConviction(@PathVariable Long id) {
        log.debug("REST request to get PieceAConviction : {}", id);
        Optional<PieceAConvictionDTO> pieceAConvictionDTO = pieceAConvictionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pieceAConvictionDTO);
    }

    /**
     * DELETE  /piece-a-convictions/:id : delete the "id" pieceAConviction.
     *
     * @param id the id of the pieceAConvictionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/piece-a-convictions/{id}")
    public ResponseEntity<Void> deletePieceAConviction(@PathVariable Long id) {
        log.debug("REST request to delete PieceAConviction : {}", id);
        pieceAConvictionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
