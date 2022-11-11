package com.polarbears.capstone.hmsdelivery.service;

import com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders;
import com.polarbears.capstone.hmsdelivery.repository.DeliveryOrdersRepository;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryOrdersDTO;
import com.polarbears.capstone.hmsdelivery.service.mapper.DeliveryOrdersMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeliveryOrders}.
 */
@Service
@Transactional
public class DeliveryOrdersService {

    private final Logger log = LoggerFactory.getLogger(DeliveryOrdersService.class);

    private final DeliveryOrdersRepository deliveryOrdersRepository;

    private final DeliveryOrdersMapper deliveryOrdersMapper;

    public DeliveryOrdersService(DeliveryOrdersRepository deliveryOrdersRepository, DeliveryOrdersMapper deliveryOrdersMapper) {
        this.deliveryOrdersRepository = deliveryOrdersRepository;
        this.deliveryOrdersMapper = deliveryOrdersMapper;
    }

    /**
     * Save a deliveryOrders.
     *
     * @param deliveryOrdersDTO the entity to save.
     * @return the persisted entity.
     */
    public DeliveryOrdersDTO save(DeliveryOrdersDTO deliveryOrdersDTO) {
        log.debug("Request to save DeliveryOrders : {}", deliveryOrdersDTO);
        DeliveryOrders deliveryOrders = deliveryOrdersMapper.toEntity(deliveryOrdersDTO);
        deliveryOrders = deliveryOrdersRepository.save(deliveryOrders);
        return deliveryOrdersMapper.toDto(deliveryOrders);
    }

    /**
     * Update a deliveryOrders.
     *
     * @param deliveryOrdersDTO the entity to save.
     * @return the persisted entity.
     */
    public DeliveryOrdersDTO update(DeliveryOrdersDTO deliveryOrdersDTO) {
        log.debug("Request to update DeliveryOrders : {}", deliveryOrdersDTO);
        DeliveryOrders deliveryOrders = deliveryOrdersMapper.toEntity(deliveryOrdersDTO);
        deliveryOrders = deliveryOrdersRepository.save(deliveryOrders);
        return deliveryOrdersMapper.toDto(deliveryOrders);
    }

    /**
     * Partially update a deliveryOrders.
     *
     * @param deliveryOrdersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DeliveryOrdersDTO> partialUpdate(DeliveryOrdersDTO deliveryOrdersDTO) {
        log.debug("Request to partially update DeliveryOrders : {}", deliveryOrdersDTO);

        return deliveryOrdersRepository
            .findById(deliveryOrdersDTO.getId())
            .map(existingDeliveryOrders -> {
                deliveryOrdersMapper.partialUpdate(existingDeliveryOrders, deliveryOrdersDTO);

                return existingDeliveryOrders;
            })
            .map(deliveryOrdersRepository::save)
            .map(deliveryOrdersMapper::toDto);
    }

    /**
     * Get all the deliveryOrders.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryOrdersDTO> findAll() {
        log.debug("Request to get all DeliveryOrders");
        return deliveryOrdersRepository
            .findAll()
            .stream()
            .map(deliveryOrdersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the deliveryOrders with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DeliveryOrdersDTO> findAllWithEagerRelationships(Pageable pageable) {
        return deliveryOrdersRepository.findAllWithEagerRelationships(pageable).map(deliveryOrdersMapper::toDto);
    }

    /**
     * Get one deliveryOrders by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeliveryOrdersDTO> findOne(Long id) {
        log.debug("Request to get DeliveryOrders : {}", id);
        return deliveryOrdersRepository.findOneWithEagerRelationships(id).map(deliveryOrdersMapper::toDto);
    }

    /**
     * Delete the deliveryOrders by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeliveryOrders : {}", id);
        deliveryOrdersRepository.deleteById(id);
    }
}
