package com.test.web.rest;
import com.test.service.TypeDiplomeService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.TypeDiplomeDTO;
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
 * REST controller for managing TypeDiplome.
 */
@RestController
@RequestMapping("/api")
public class TypeDiplomeResource {

    private final Logger log = LoggerFactory.getLogger(TypeDiplomeResource.class);

    private static final String ENTITY_NAME = "typeDiplome";

    private final TypeDiplomeService typeDiplomeService;

    public TypeDiplomeResource(TypeDiplomeService typeDiplomeService) {
        this.typeDiplomeService = typeDiplomeService;
    }

    /**
     * POST  /type-diplomes : Create a new typeDiplome.
     *
     * @param typeDiplomeDTO the typeDiplomeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeDiplomeDTO, or with status 400 (Bad Request) if the typeDiplome has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-diplomes")
    public ResponseEntity<TypeDiplomeDTO> createTypeDiplome(@Valid @RequestBody TypeDiplomeDTO typeDiplomeDTO) throws URISyntaxException {
        log.debug("REST request to save TypeDiplome : {}", typeDiplomeDTO);
        if (typeDiplomeDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeDiplome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDiplomeDTO result = typeDiplomeService.save(typeDiplomeDTO);
        return ResponseEntity.created(new URI("/api/type-diplomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-diplomes : Updates an existing typeDiplome.
     *
     * @param typeDiplomeDTO the typeDiplomeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeDiplomeDTO,
     * or with status 400 (Bad Request) if the typeDiplomeDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeDiplomeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-diplomes")
    public ResponseEntity<TypeDiplomeDTO> updateTypeDiplome(@Valid @RequestBody TypeDiplomeDTO typeDiplomeDTO) throws URISyntaxException {
        log.debug("REST request to update TypeDiplome : {}", typeDiplomeDTO);
        if (typeDiplomeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDiplomeDTO result = typeDiplomeService.save(typeDiplomeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeDiplomeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-diplomes : get all the typeDiplomes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeDiplomes in body
     */
    @GetMapping("/type-diplomes")
    public ResponseEntity<List<TypeDiplomeDTO>> getAllTypeDiplomes(Pageable pageable) {
        log.debug("REST request to get a page of TypeDiplomes");
        Page<TypeDiplomeDTO> page = typeDiplomeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-diplomes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-diplomes/:id : get the "id" typeDiplome.
     *
     * @param id the id of the typeDiplomeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeDiplomeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-diplomes/{id}")
    public ResponseEntity<TypeDiplomeDTO> getTypeDiplome(@PathVariable Long id) {
        log.debug("REST request to get TypeDiplome : {}", id);
        Optional<TypeDiplomeDTO> typeDiplomeDTO = typeDiplomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDiplomeDTO);
    }

    /**
     * DELETE  /type-diplomes/:id : delete the "id" typeDiplome.
     *
     * @param id the id of the typeDiplomeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-diplomes/{id}")
    public ResponseEntity<Void> deleteTypeDiplome(@PathVariable Long id) {
        log.debug("REST request to delete TypeDiplome : {}", id);
        typeDiplomeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
