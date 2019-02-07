package com.test.web.rest;
import com.test.service.NoteMembreCritereService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.NoteMembreCritereDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NoteMembreCritere.
 */
@RestController
@RequestMapping("/api")
public class NoteMembreCritereResource {

    private final Logger log = LoggerFactory.getLogger(NoteMembreCritereResource.class);

    private static final String ENTITY_NAME = "noteMembreCritere";

    private final NoteMembreCritereService noteMembreCritereService;

    public NoteMembreCritereResource(NoteMembreCritereService noteMembreCritereService) {
        this.noteMembreCritereService = noteMembreCritereService;
    }

    /**
     * POST  /note-membre-criteres : Create a new noteMembreCritere.
     *
     * @param noteMembreCritereDTO the noteMembreCritereDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new noteMembreCritereDTO, or with status 400 (Bad Request) if the noteMembreCritere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/note-membre-criteres")
    public ResponseEntity<NoteMembreCritereDTO> createNoteMembreCritere(@RequestBody NoteMembreCritereDTO noteMembreCritereDTO) throws URISyntaxException {
        log.debug("REST request to save NoteMembreCritere : {}", noteMembreCritereDTO);
        if (noteMembreCritereDTO.getId() != null) {
            throw new BadRequestAlertException("A new noteMembreCritere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoteMembreCritereDTO result = noteMembreCritereService.save(noteMembreCritereDTO);
        return ResponseEntity.created(new URI("/api/note-membre-criteres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /note-membre-criteres : Updates an existing noteMembreCritere.
     *
     * @param noteMembreCritereDTO the noteMembreCritereDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated noteMembreCritereDTO,
     * or with status 400 (Bad Request) if the noteMembreCritereDTO is not valid,
     * or with status 500 (Internal Server Error) if the noteMembreCritereDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/note-membre-criteres")
    public ResponseEntity<NoteMembreCritereDTO> updateNoteMembreCritere(@RequestBody NoteMembreCritereDTO noteMembreCritereDTO) throws URISyntaxException {
        log.debug("REST request to update NoteMembreCritere : {}", noteMembreCritereDTO);
        if (noteMembreCritereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NoteMembreCritereDTO result = noteMembreCritereService.save(noteMembreCritereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, noteMembreCritereDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /note-membre-criteres : get all the noteMembreCriteres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of noteMembreCriteres in body
     */
    @GetMapping("/note-membre-criteres")
    public ResponseEntity<List<NoteMembreCritereDTO>> getAllNoteMembreCriteres(Pageable pageable) {
        log.debug("REST request to get a page of NoteMembreCriteres");
        Page<NoteMembreCritereDTO> page = noteMembreCritereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/note-membre-criteres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /note-membre-criteres/:id : get the "id" noteMembreCritere.
     *
     * @param id the id of the noteMembreCritereDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the noteMembreCritereDTO, or with status 404 (Not Found)
     */
    @GetMapping("/note-membre-criteres/{id}")
    public ResponseEntity<NoteMembreCritereDTO> getNoteMembreCritere(@PathVariable Long id) {
        log.debug("REST request to get NoteMembreCritere : {}", id);
        Optional<NoteMembreCritereDTO> noteMembreCritereDTO = noteMembreCritereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noteMembreCritereDTO);
    }

    /**
     * DELETE  /note-membre-criteres/:id : delete the "id" noteMembreCritere.
     *
     * @param id the id of the noteMembreCritereDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/note-membre-criteres/{id}")
    public ResponseEntity<Void> deleteNoteMembreCritere(@PathVariable Long id) {
        log.debug("REST request to delete NoteMembreCritere : {}", id);
        noteMembreCritereService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
