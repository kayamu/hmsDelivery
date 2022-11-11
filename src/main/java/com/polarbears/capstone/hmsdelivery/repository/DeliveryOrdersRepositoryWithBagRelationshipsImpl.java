package com.polarbears.capstone.hmsdelivery.repository;

import com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DeliveryOrdersRepositoryWithBagRelationshipsImpl implements DeliveryOrdersRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DeliveryOrders> fetchBagRelationships(Optional<DeliveryOrders> deliveryOrders) {
        return deliveryOrders.map(this::fetchDeliveryTransactions);
    }

    @Override
    public Page<DeliveryOrders> fetchBagRelationships(Page<DeliveryOrders> deliveryOrders) {
        return new PageImpl<>(
            fetchBagRelationships(deliveryOrders.getContent()),
            deliveryOrders.getPageable(),
            deliveryOrders.getTotalElements()
        );
    }

    @Override
    public List<DeliveryOrders> fetchBagRelationships(List<DeliveryOrders> deliveryOrders) {
        return Optional.of(deliveryOrders).map(this::fetchDeliveryTransactions).orElse(Collections.emptyList());
    }

    DeliveryOrders fetchDeliveryTransactions(DeliveryOrders result) {
        return entityManager
            .createQuery(
                "select deliveryOrders from DeliveryOrders deliveryOrders left join fetch deliveryOrders.deliveryTransactions where deliveryOrders is :deliveryOrders",
                DeliveryOrders.class
            )
            .setParameter("deliveryOrders", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DeliveryOrders> fetchDeliveryTransactions(List<DeliveryOrders> deliveryOrders) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, deliveryOrders.size()).forEach(index -> order.put(deliveryOrders.get(index).getId(), index));
        List<DeliveryOrders> result = entityManager
            .createQuery(
                "select distinct deliveryOrders from DeliveryOrders deliveryOrders left join fetch deliveryOrders.deliveryTransactions where deliveryOrders in :deliveryOrders",
                DeliveryOrders.class
            )
            .setParameter("deliveryOrders", deliveryOrders)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
