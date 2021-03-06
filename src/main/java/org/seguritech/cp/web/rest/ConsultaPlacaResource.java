package org.seguritech.cp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.seguritech.cp.domain.enumeration.Permiso;
import org.seguritech.cp.service.ConsultaPlacaService;
import org.seguritech.cp.service.RadioService;
import org.seguritech.cp.service.dto.*;
import org.seguritech.cp.web.rest.errors.BadRequestAlertException;
import org.seguritech.cp.web.rest.util.DateUtil;
import org.seguritech.cp.web.rest.util.HeaderUtil;
import org.seguritech.cp.web.rest.util.PaginationUtil;
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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.json.BasicJsonParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seguritech.cp.service.soap.client.ClientWebService;
import org.seguritech.cp.service.soap.ConsultaBDResponse;

import java.util.ArrayList;
/**
 * REST controller for managing ConsultaPlaca.
 */
@RestController
@RequestMapping("/api")
public class ConsultaPlacaResource {

    private final Logger log = LoggerFactory.getLogger(ConsultaPlacaResource.class);

    private static final String ENTITY_NAME = "consultaPlaca";

    private final ConsultaPlacaService consultaPlacaService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private  ClientWebService clientService;

    private final RadioService radioService;

    public ConsultaPlacaResource(ConsultaPlacaService consultaPlacaService,RadioService radioService) {
        this.consultaPlacaService = consultaPlacaService;
        this.radioService = radioService;
    }

    /**
     * POST  /consulta-placas : Create a new consultaPlaca.
     *
     * @param consultaPlacaDTO the consultaPlacaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultaPlacaDTO, or with status 400 (Bad Request) if the consultaPlaca has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consulta-placas")
    @Timed
    public ResponseEntity<ConsultaPlacaDTO> createConsultaPlaca(@Valid @RequestBody ConsultaPlacaDTO consultaPlacaDTO) throws URISyntaxException {
        log.debug("REST request to save ConsultaPlaca : {}", consultaPlacaDTO);
        if (consultaPlacaDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultaPlaca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultaPlacaDTO result = consultaPlacaService.save(consultaPlacaDTO);
        return ResponseEntity.created(new URI("/api/consulta-placas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consulta-placas : Updates an existing consultaPlaca.
     *
     * @param consultaPlacaDTO the consultaPlacaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultaPlacaDTO,
     * or with status 400 (Bad Request) if the consultaPlacaDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultaPlacaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consulta-placas")
    @Timed
    public ResponseEntity<ConsultaPlacaDTO> updateConsultaPlaca(@Valid @RequestBody ConsultaPlacaDTO consultaPlacaDTO) throws URISyntaxException {
        log.debug("REST request to update ConsultaPlaca : {}", consultaPlacaDTO);
        if (consultaPlacaDTO.getId() == null) {
            return createConsultaPlaca(consultaPlacaDTO);
        }
        ConsultaPlacaDTO result = consultaPlacaService.save(consultaPlacaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultaPlacaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consulta-placas : get all the consultaPlacas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultaPlacas in body
     */
    @GetMapping("/consulta-placas")
    @Timed
    public ResponseEntity<List<ConsultaPlacaDTO>> getAllConsultaPlacas(Pageable pageable) {
        log.debug("REST request to get a page of ConsultaPlacas");
        Page<ConsultaPlacaDTO> page = consultaPlacaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consulta-placas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consulta-placas/:id : get the "id" consultaPlaca.
     *
     * @param id the id of the consultaPlacaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultaPlacaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consulta-placas/{id}")
    @Timed
    public ResponseEntity<ConsultaPlacaDTO> getConsultaPlaca(@PathVariable Long id) {
        log.debug("REST request to get ConsultaPlaca : {}", id);
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultaPlacaDTO));
    }

    /**
     * DELETE  /consulta-placas/:id : delete the "id" consultaPlaca.
     *
     * @param id the id of the consultaPlacaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consulta-placas/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultaPlaca(@PathVariable Long id) {
        log.debug("REST request to delete ConsultaPlaca : {}", id);
        consultaPlacaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /consulta-placas : get all the consultaPlacas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultaPlacas in body
     */
    @GetMapping("/consulta-placas/filter")
    @Timed
    public ResponseEntity<List<ConsultaPlacaDTO>> getAllConsultaPlacasFilter(Pageable pageable, @RequestParam(required=false) String issi,
                                                                             @RequestParam(required=false) String municipio,
                                                                             @RequestParam(required=false) String corporacion,
                                                                             @RequestParam(required=false) String estado,
                                                                             @RequestParam(required=false) String desde,
                                                                             @RequestParam(required=false) String hasta) {
        log.debug("REST request to get a page of ConsultaPlacas");
        Page<ConsultaPlacaDTO> page = consultaPlacaService.findAllFilter(pageable, issi, municipio, corporacion, estado, desde, hasta);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consulta-placas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
