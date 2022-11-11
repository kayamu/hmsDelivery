package com.polarbears.capstone.hmsdelivery.web.rest;

import com.polarbears.capstone.hmsdelivery.repository.DeliveryTransactionsRepository;
import com.polarbears.capstone.hmsdelivery.service.DeliveryTransactionsQueryService;
import com.polarbears.capstone.hmsdelivery.service.DeliveryTransactionsService;
import com.polarbears.capstone.hmsdelivery.service.criteria.DeliveryTransactionsCriteria;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryTransactionsDTO;
import com.polarbears.capstone.hmsdelivery.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions}.
 */
@RestController
@RequestMapping("/api")
public class DeliveryTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryTransactionsResource.class);

    private static final String ENTITY_NAME = "hmsdeliveryDeliveryTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryTransactionsService deliveryTransactionsService;

    private final DeliveryTransactionsRepository deliveryTransactionsRepository;

    private final DeliveryTransactionsQueryService deliveryTransactionsQueryService;

    public DeliveryTransactionsResource(
        DeliveryTransactionsService deliveryTransactionsService,
        DeliveryTransactionsRepository deliveryTransactionsRepository,
        DeliveryTransactionsQueryService deliveryTransactionsQueryService
    ) {
        this.deliveryTransactionsService = deliveryTransactionsService;
        this.deliveryTransactionsRepository = deliveryTransactionsRepository;
        this.deliveryTransactionsQueryService = deliveryTransactionsQueryService;
    }

    /**
     * {@code POST  /delivery-transactions} : Create a new deliveryTransactions.
     *
     * @param deliveryTransactionsDTO the deliveryTransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryTransactionsDTO, or with status {@code 400 (Bad Request)} if the deliveryTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-transactions")
    public ResponseEntity<DeliveryTransactionsDTO> createDeliveryTransactions(@RequestBody DeliveryTransactionsDTO deliveryTransactionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save DeliveryTransactions : {}", deliveryTransactionsDTO);
        if (deliveryTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryTransactionsDTO result = deliveryTransactionsService.save(deliveryTransactionsDTO);
        return ResponseEntity
            .created(new URI("/api/delivery-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-transactions/:id} : Updates an existing deliveryTransactions.
     *
     * @param id the id of the deliveryTransactionsDTO to save.
     * @param deliveryTransactionsDTO the deliveryTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryTransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-transactions/{id}")
    public ResponseEntity<DeliveryTransactionsDTO> updateDeliveryTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeliveryTransactionsDTO deliveryTransactionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DeliveryTransactions : {}, {}", id, deliveryTransactionsDTO);
        if (deliveryTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deliveryTransactionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deliveryTransactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeliveryTransactionsDTO result = deliveryTransactionsService.update(deliveryTransactionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /delivery-transactions/:id} : Partial updates given fields of an existing deliveryTransactions, field will ignore if it is null
     *
     * @param id the id of the deliveryTransactionsDTO to save.
     * @param deliveryTransactionsDTO the deliveryTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryTransactionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the deliveryTransactionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the deliveryTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/delivery-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeliveryTransactionsDTO> partialUpdateDeliveryTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeliveryTransactionsDTO deliveryTransactionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeliveryTransactions partially : {}, {}", id, deliveryTransactionsDTO);
        if (deliveryTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deliveryTransactionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deliveryTransactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeliveryTransactionsDTO> result = deliveryTransactionsService.partialUpdate(deliveryTransactionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryTransactionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /delivery-transactions} : get all the deliveryTransactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryTransactions in body.
     */
    @GetMapping("/delivery-transactions")
    public ResponseEntity<List<DeliveryTransactionsDTO>> getAllDeliveryTransactions(DeliveryTransactionsCriteria criteria) {
        log.debug("REST request to get DeliveryTransactions by criteria: {}", criteria);
        List<DeliveryTransactionsDTO> entityList = deliveryTransactionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /delivery-transactions/count} : count all the deliveryTransactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/delivery-transactions/count")
    public ResponseEntity<Long> countDeliveryTransactions(DeliveryTransactionsCriteria criteria) {
        log.debug("REST request to count DeliveryTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(deliveryTransactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /delivery-transactions/:id} : get the "id" deliveryTransactions.
     *
     * @param id the id of the deliveryTransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryTransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-transactions/{id}")
    public ResponseEntity<DeliveryTransactionsDTO> getDeliveryTransactions(@PathVariable Long id) {
        log.debug("REST request to get DeliveryTransactions : {}", id);
        Optional<DeliveryTransactionsDTO> deliveryTransactionsDTO = deliveryTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryTransactionsDTO);
    }

    /**
     * {@code DELETE  /delivery-transactions/:id} : delete the "id" deliveryTransactions.
     *
     * @param id the id of the deliveryTransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-transactions/{id}")
    public ResponseEntity<Void> deleteDeliveryTransactions(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryTransactions : {}", id);
        deliveryTransactionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
