package com.polarbears.capstone.hmsdelivery.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsdelivery.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeliveryOrdersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryOrdersDTO.class);
        DeliveryOrdersDTO deliveryOrdersDTO1 = new DeliveryOrdersDTO();
        deliveryOrdersDTO1.setId(1L);
        DeliveryOrdersDTO deliveryOrdersDTO2 = new DeliveryOrdersDTO();
        assertThat(deliveryOrdersDTO1).isNotEqualTo(deliveryOrdersDTO2);
        deliveryOrdersDTO2.setId(deliveryOrdersDTO1.getId());
        assertThat(deliveryOrdersDTO1).isEqualTo(deliveryOrdersDTO2);
        deliveryOrdersDTO2.setId(2L);
        assertThat(deliveryOrdersDTO1).isNotEqualTo(deliveryOrdersDTO2);
        deliveryOrdersDTO1.setId(null);
        assertThat(deliveryOrdersDTO1).isNotEqualTo(deliveryOrdersDTO2);
    }
}
