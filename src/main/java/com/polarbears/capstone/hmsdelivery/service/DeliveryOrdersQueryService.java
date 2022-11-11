package com.polarbears.capstone.hmsdelivery.service;

import com.polarbears.capstone.hmsdelivery.domain.*; // for static metamodels
import com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders;
import com.polarbears.capstone.hmsdelivery.repository.DeliveryOrdersRepository;
import com.polarbears.capstone.hmsdelivery.service.criteria.DeliveryOrdersCriteria;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryOrdersDTO;
import com.polarbears.capstone.hmsdelivery.service.mapper.DeliveryOrdersMapper;
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
 * Service for executing complex queries for {@link DeliveryOrders} entities in the database.
 * The main input is a {@link DeliveryOrdersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeliveryOrdersDTO} or a {@link Page} of {@link DeliveryOrdersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeliveryOrdersQueryService extends QueryService<DeliveryOrders> {

    private final Logger log = LoggerFactory.getLogger(DeliveryOrdersQueryService.class);

    private final DeliveryOrdersRepository deliveryOrdersRepository;

    private final DeliveryOrdersMapper deliveryOrdersMapper;

    public DeliveryOrdersQueryService(DeliveryOrdersRepository deliveryOrdersRepository, DeliveryOrdersMapper deliveryOrdersMapper) {
        this.deliveryOrdersRepository = deliveryOrdersRepository;
        this.deliveryOrdersMapper = deliveryOrdersMapper;
    }

    /**
     * Return a {@link List} of {@link DeliveryOrdersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryOrdersDTO> findByCriteria(DeliveryOrdersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeliveryOrders> specification = createSpecification(criteria);
        return deliveryOrdersMapper.toDto(deliveryOrdersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeliveryOrdersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeliveryOrdersDTO> findByCriteria(DeliveryOrdersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeliveryOrders> specification = createSpecification(criteria);
        return deliveryOrdersRepository.findAll(specification, page).map(deliveryOrdersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeliveryOrdersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeliveryOrders> specification = createSpecification(criteria);
        return deliveryOrdersRepository.count(specification);
    }

    /**
     * Function to convert {@link DeliveryOrdersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DeliveryOrders> createSpecification(DeliveryOrdersCriteria criteria) {
        Specification<DeliveryOrders> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DeliveryOrders_.id));
            }
            if (criteria.getDeliveryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryId(), DeliveryOrders_.deliveryId));
            }
            if (criteria.getInvoiceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNumber(), DeliveryOrders_.invoiceNumber));
            }
            if (criteria.getContactId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactId(), DeliveryOrders_.contactId));
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), DeliveryOrders_.contactName));
            }
            if (criteria.getContactAddressId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getContactAddressId(), DeliveryOrders_.contactAddressId));
            }
            if (criteria.getContactCartId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactCartId(), DeliveryOrders_.contactCartId));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), DeliveryOrders_.deliveryDate));
            }
            if (criteria.getRequestDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestDate(), DeliveryOrders_.requestDate));
            }
            if (criteria.getMenuItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMenuItemId(), DeliveryOrders_.menuItemId));
            }
            if (criteria.getMenuItemName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMenuItemName(), DeliveryOrders_.menuItemName));
            }
            if (criteria.getMenuItemCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMenuItemCode(), DeliveryOrders_.menuItemCode));
            }
            if (criteria.getLineNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNumber(), DeliveryOrders_.lineNumber));
            }
            if (criteria.getDetail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetail(), DeliveryOrders_.detail));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), DeliveryOrders_.message));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), DeliveryOrders_.createdDate));
            }
            if (criteria.getDeliveryTransactionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDeliveryTransactionsId(),
                            root -> root.join(DeliveryOrders_.deliveryTransactions, JoinType.LEFT).get(DeliveryTransactions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
