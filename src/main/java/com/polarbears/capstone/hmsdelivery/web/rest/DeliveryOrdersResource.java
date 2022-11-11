package com.polarbears.capstone.hmsdelivery.web.rest;

import com.polarbears.capstone.hmsdelivery.repository.DeliveryOrdersRepository;
import com.polarbears.capstone.hmsdelivery.service.DeliveryOrdersQueryService;
import com.polarbears.capstone.hmsdelivery.service.DeliveryOrdersService;
import com.polarbears.capstone.hmsdelivery.service.criteria.DeliveryOrdersCriteria;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryOrdersDTO;
import com.polarbears.capstone.hmsdelivery.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders}.
 */
@RestController
@RequestMapping("/api")
public class DeliveryOrdersResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryOrdersResource.class);

    private static final String ENTITY_NAME = "hmsdeliveryDeliveryOrders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryOrdersService deliveryOrdersService;

    private final DeliveryOrdersRepository deliveryOrdersRepository;

    private final DeliveryOrdersQueryService deliveryOrdersQueryService;

    public DeliveryOrdersResource(
        DeliveryOrdersService deliveryOrdersService,
        DeliveryOrdersRepository deliveryOrdersRepository,
        DeliveryOrdersQueryService deliveryOrdersQueryService
    ) {
        this.deliveryOrdersService = deliveryOrdersService;
        this.deliveryOrdersRepository = deliveryOrdersRepository;
        this.deliveryOrdersQueryService = deliveryOrdersQueryService;
    }

    /**
     * {@code POST  /delivery-orders} : Create a new deliveryOrders.
     *
     * @param deliveryOrdersDTO the deliveryOrdersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryOrdersDTO, or with status {@code 400 (Bad Request)} if the deliveryOrders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-orders")
    public ResponseEntity<DeliveryOrdersDTO> createDeliveryOrders(@Valid @RequestBody DeliveryOrdersDTO deliveryOrdersDTO)
        throws URISyntaxException {
        log.debug("REST request to save DeliveryOrders : {}", deliveryOrdersDTO);
        if (deliveryOrdersDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryOrders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryOrdersDTO result = deliveryOrdersService.save(deliveryOrdersDTO);
        return ResponseEntity
            .created(new URI("/api/delivery-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-orders/:id} : Updates an existing deliveryOrders.
     *
     * @param id the id of the deliveryOrdersDTO to save.
     * @param deliveryOrdersDTO the deliveryOrdersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryOrdersDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryOrdersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryOrdersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-orders/{id}")
    public ResponseEntity<DeliveryOrdersDTO> updateDeliveryOrders(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DeliveryOrdersDTO deliveryOrdersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DeliveryOrders : {}, {}", id, deliveryOrdersDTO);
        if (deliveryOrdersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deliveryOrdersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deliveryOrdersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeliveryOrdersDTO result = deliveryOrdersService.update(deliveryOrdersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryOrdersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /delivery-orders/:id} : Partial updates given fields of an existing deliveryOrders, field will ignore if it is null
     *
     * @param id the id of the deliveryOrdersDTO to save.
     * @param deliveryOrdersDTO the deliveryOrdersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryOrdersDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryOrdersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the deliveryOrdersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the deliveryOrdersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/delivery-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeliveryOrdersDTO> partialUpdateDeliveryOrders(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DeliveryOrdersDTO deliveryOrdersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeliveryOrders partially : {}, {}", id, deliveryOrdersDTO);
        if (deliveryOrdersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deliveryOrdersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deliveryOrdersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeliveryOrdersDTO> result = deliveryOrdersService.partialUpdate(deliveryOrdersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryOrdersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /delivery-orders} : get all the deliveryOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryOrders in body.
     */
    @GetMapping("/delivery-orders")
    public ResponseEntity<List<DeliveryOrdersDTO>> getAllDeliveryOrders(DeliveryOrdersCriteria criteria) {
        log.debug("REST request to get DeliveryOrders by criteria: {}", criteria);
        List<DeliveryOrdersDTO> entityList = deliveryOrdersQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /delivery-orders/count} : count all the deliveryOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/delivery-orders/count")
    public ResponseEntity<Long> countDeliveryOrders(DeliveryOrdersCriteria criteria) {
        log.debug("REST request to count DeliveryOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(deliveryOrdersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /delivery-orders/:id} : get the "id" deliveryOrders.
     *
     * @param id the id of the deliveryOrdersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryOrdersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-orders/{id}")
    public ResponseEntity<DeliveryOrdersDTO> getDeliveryOrders(@PathVariable Long id) {
        log.debug("REST request to get DeliveryOrders : {}", id);
        Optional<DeliveryOrdersDTO> deliveryOrdersDTO = deliveryOrdersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryOrdersDTO);
    }

    /**
     * {@code DELETE  /delivery-orders/:id} : delete the "id" deliveryOrders.
     *
     * @param id the id of the deliveryOrdersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-orders/{id}")
    public ResponseEntity<Void> deleteDeliveryOrders(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryOrders : {}", id);
        deliveryOrdersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
