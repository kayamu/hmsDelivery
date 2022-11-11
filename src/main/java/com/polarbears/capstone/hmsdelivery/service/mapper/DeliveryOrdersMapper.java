package com.polarbears.capstone.hmsdelivery.service.mapper;

import com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders;
import com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryOrdersDTO;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryTransactionsDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryOrders} and its DTO {@link DeliveryOrdersDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeliveryOrdersMapper extends EntityMapper<DeliveryOrdersDTO, DeliveryOrders> {
    @Mapping(target = "deliveryTransactions", source = "deliveryTransactions", qualifiedByName = "deliveryTransactionsIdSet")
    DeliveryOrdersDTO toDto(DeliveryOrders s);

    @Mapping(target = "removeDeliveryTransactions", ignore = true)
    DeliveryOrders toEntity(DeliveryOrdersDTO deliveryOrdersDTO);

    @Named("deliveryTransactionsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeliveryTransactionsDTO toDtoDeliveryTransactionsId(DeliveryTransactions deliveryTransactions);

    @Named("deliveryTransactionsIdSet")
    default Set<DeliveryTransactionsDTO> toDtoDeliveryTransactionsIdSet(Set<DeliveryTransactions> deliveryTransactions) {
        return deliveryTransactions.stream().map(this::toDtoDeliveryTransactionsId).collect(Collectors.toSet());
    }
}
