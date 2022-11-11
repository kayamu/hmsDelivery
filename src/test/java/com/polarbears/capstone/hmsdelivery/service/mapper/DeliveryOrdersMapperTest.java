package com.polarbears.capstone.hmsdelivery.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryOrdersMapperTest {

    private DeliveryOrdersMapper deliveryOrdersMapper;

    @BeforeEach
    public void setUp() {
        deliveryOrdersMapper = new DeliveryOrdersMapperImpl();
    }
}
