package com.polarbears.capstone.hmsdelivery.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsdelivery.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeliveryTransactionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryTransactionsDTO.class);
        DeliveryTransactionsDTO deliveryTransactionsDTO1 = new DeliveryTransactionsDTO();
        deliveryTransactionsDTO1.setId(1L);
        DeliveryTransactionsDTO deliveryTransactionsDTO2 = new DeliveryTransactionsDTO();
        assertThat(deliveryTransactionsDTO1).isNotEqualTo(deliveryTransactionsDTO2);
        deliveryTransactionsDTO2.setId(deliveryTransactionsDTO1.getId());
        assertThat(deliveryTransactionsDTO1).isEqualTo(deliveryTransactionsDTO2);
        deliveryTransactionsDTO2.setId(2L);
        assertThat(deliveryTransactionsDTO1).isNotEqualTo(deliveryTransactionsDTO2);
        deliveryTransactionsDTO1.setId(null);
        assertThat(deliveryTransactionsDTO1).isNotEqualTo(deliveryTransactionsDTO2);
    }
}
