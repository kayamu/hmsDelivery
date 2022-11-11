package com.polarbears.capstone.hmsdelivery.service;

import com.polarbears.capstone.hmsdelivery.domain.*; // for static metamodels
import com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions;
import com.polarbears.capstone.hmsdelivery.repository.DeliveryTransactionsRepository;
import com.polarbears.capstone.hmsdelivery.service.criteria.DeliveryTransactionsCriteria;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryTransactionsDTO;
import com.polarbears.capstone.hmsdelivery.service.mapper.DeliveryTransactionsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DeliveryTransactions} entities in the database.
 * The main input is a {@link DeliveryTransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeliveryTransactionsDTO} or a {@link Page} of {@link DeliveryTransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeliveryTransactionsQueryService extends QueryService<DeliveryTransactions> {

    private final Logger log = LoggerFactory.getLogger(DeliveryTransactionsQueryService.class);

    private final DeliveryTransactionsRepository deliveryTransactionsRepository;

    private final DeliveryTransactionsMapper deliveryTransactionsMapper;

    public DeliveryTransactionsQueryService(
        DeliveryTransactionsRepository deliveryTransactionsRepository,
        DeliveryTransactionsMapper deliveryTransactionsMapper
    ) {
        this.deliveryTransactionsRepository = deliveryTransactionsRepository;
        this.deliveryTransactionsMapper = deliveryTransactionsMapper;
    }

    /**
     * Return a {@link List} of {@link DeliveryTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryTransactionsDTO> findByCriteria(DeliveryTransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeliveryTransactions> specification = createSpecification(criteria);
        return deliveryTransactionsMapper.toDto(deliveryTransactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeliveryTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeliveryTransactionsDTO> findByCriteria(DeliveryTransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeliveryTransactions> specification = createSpecification(criteria);
        return deliveryTransactionsRepository.findAll(specification, page).map(deliveryTransactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeliveryTransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeliveryTransactions> specification = createSpecification(criteria);
        return deliveryTransactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link DeliveryTransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DeliveryTransactions> createSpecification(DeliveryTransactionsCriteria criteria) {
        Specification<DeliveryTransactions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DeliveryTransactions_.id));
            }
            if (criteria.getStatusChangedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getStatusChangedDate(), DeliveryTransactions_.statusChangedDate));
            }
            if (criteria.getTransactionDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTransactionDate(), DeliveryTransactions_.transactionDate));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), DeliveryTransactions_.type));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), DeliveryTransactions_.createdDate));
            }
            if (criteria.getDeliveryOrdersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDeliveryOrdersId(),
                            root -> root.join(DeliveryTransactions_.deliveryOrders, JoinType.LEFT).get(DeliveryOrders_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
