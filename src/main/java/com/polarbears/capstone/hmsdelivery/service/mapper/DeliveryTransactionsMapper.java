package com.polarbears.capstone.hmsdelivery.service.mapper;

import com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryTransactionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryTransactions} and its DTO {@link DeliveryTransactionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeliveryTransactionsMapper extends EntityMapper<DeliveryTransactionsDTO, DeliveryTransactions> {}
