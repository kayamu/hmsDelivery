package com.polarbears.capstone.hmsdelivery.service;

import com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions;
import com.polarbears.capstone.hmsdelivery.repository.DeliveryTransactionsRepository;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryTransactionsDTO;
import com.polarbears.capstone.hmsdelivery.service.mapper.DeliveryTransactionsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DeliveryTransactions}.
 */
@Service
@Transactional
public class DeliveryTransactionsService {

    private final Logger log = LoggerFactory.getLogger(DeliveryTransactionsService.class);

    private final DeliveryTransactionsRepository deliveryTransactionsRepository;

    private final DeliveryTransactionsMapper deliveryTransactionsMapper;

    public DeliveryTransactionsService(
        DeliveryTransactionsRepository deliveryTransactionsRepository,
        DeliveryTransactionsMapper deliveryTransactionsMapper
    ) {
        this.deliveryTransactionsRepository = deliveryTransactionsRepository;
        this.deliveryTransactionsMapper = deliveryTransactionsMapper;
    }

    /**
     * Save a deliveryTransactions.
     *
     * @param deliveryTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    public DeliveryTransactionsDTO save(DeliveryTransactionsDTO deliveryTransactionsDTO) {
        log.debug("Request to save DeliveryTransactions : {}", deliveryTransactionsDTO);
        DeliveryTransactions deliveryTransactions = deliveryTransactionsMapper.toEntity(deliveryTransactionsDTO);
        deliveryTransactions = deliveryTransactionsRepository.save(deliveryTransactions);
        return deliveryTransactionsMapper.toDto(deliveryTransactions);
    }

    /**
     * Update a deliveryTransactions.
     *
     * @param deliveryTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    public DeliveryTransactionsDTO update(DeliveryTransactionsDTO deliveryTransactionsDTO) {
        log.debug("Request to update DeliveryTransactions : {}", deliveryTransactionsDTO);
        DeliveryTransactions deliveryTransactions = deliveryTransactionsMapper.toEntity(deliveryTransactionsDTO);
        deliveryTransactions = deliveryTransactionsRepository.save(deliveryTransactions);
        return deliveryTransactionsMapper.toDto(deliveryTransactions);
    }

    /**
     * Partially update a deliveryTransactions.
     *
     * @param deliveryTransactionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DeliveryTransactionsDTO> partialUpdate(DeliveryTransactionsDTO deliveryTransactionsDTO) {
        log.debug("Request to partially update DeliveryTransactions : {}", deliveryTransactionsDTO);

        return deliveryTransactionsRepository
            .findById(deliveryTransactionsDTO.getId())
            .map(existingDeliveryTransactions -> {
                deliveryTransactionsMapper.partialUpdate(existingDeliveryTransactions, deliveryTransactionsDTO);

                return existingDeliveryTransactions;
            })
            .map(deliveryTransactionsRepository::save)
            .map(deliveryTransactionsMapper::toDto);
    }

    /**
     * Get all the deliveryTransactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryTransactionsDTO> findAll() {
        log.debug("Request to get all DeliveryTransactions");
        return deliveryTransactionsRepository
            .findAll()
            .stream()
            .map(deliveryTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one deliveryTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeliveryTransactionsDTO> findOne(Long id) {
        log.debug("Request to get DeliveryTransactions : {}", id);
        return deliveryTransactionsRepository.findById(id).map(deliveryTransactionsMapper::toDto);
    }

    /**
     * Delete the deliveryTransactions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeliveryTransactions : {}", id);
        deliveryTransactionsRepository.deleteById(id);
    }
}
