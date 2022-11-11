package com.polarbears.capstone.hmsdelivery.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryTransactionsMapperTest {

    private DeliveryTransactionsMapper deliveryTransactionsMapper;

    @BeforeEach
    public void setUp() {
        deliveryTransactionsMapper = new DeliveryTransactionsMapperImpl();
    }
}
