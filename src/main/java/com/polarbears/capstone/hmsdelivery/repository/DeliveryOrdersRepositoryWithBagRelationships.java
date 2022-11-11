package com.polarbears.capstone.hmsdelivery.repository;

import com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DeliveryOrdersRepositoryWithBagRelationships {
    Optional<DeliveryOrders> fetchBagRelationships(Optional<DeliveryOrders> deliveryOrders);

    List<DeliveryOrders> fetchBagRelationships(List<DeliveryOrders> deliveryOrders);

    Page<DeliveryOrders> fetchBagRelationships(Page<DeliveryOrders> deliveryOrders);
}
