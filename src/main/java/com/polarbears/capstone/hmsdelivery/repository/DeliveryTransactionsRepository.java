package com.polarbears.capstone.hmsdelivery.repository;

import com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DeliveryTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryTransactionsRepository
    extends JpaRepository<DeliveryTransactions, Long>, JpaSpecificationExecutor<DeliveryTransactions> {}
