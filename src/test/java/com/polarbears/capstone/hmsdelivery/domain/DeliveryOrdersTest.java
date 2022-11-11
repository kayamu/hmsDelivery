package com.polarbears.capstone.hmsdelivery.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsdelivery.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeliveryOrdersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryOrders.class);
        DeliveryOrders deliveryOrders1 = new DeliveryOrders();
        deliveryOrders1.setId(1L);
        DeliveryOrders deliveryOrders2 = new DeliveryOrders();
        deliveryOrders2.setId(deliveryOrders1.getId());
        assertThat(deliveryOrders1).isEqualTo(deliveryOrders2);
        deliveryOrders2.setId(2L);
        assertThat(deliveryOrders1).isNotEqualTo(deliveryOrders2);
        deliveryOrders1.setId(null);
        assertThat(deliveryOrders1).isNotEqualTo(deliveryOrders2);
    }
}
