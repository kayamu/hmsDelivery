package com.polarbears.capstone.hmsdelivery.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsdelivery.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeliveryTransactionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryTransactions.class);
        DeliveryTransactions deliveryTransactions1 = new DeliveryTransactions();
        deliveryTransactions1.setId(1L);
        DeliveryTransactions deliveryTransactions2 = new DeliveryTransactions();
        deliveryTransactions2.setId(deliveryTransactions1.getId());
        assertThat(deliveryTransactions1).isEqualTo(deliveryTransactions2);
        deliveryTransactions2.setId(2L);
        assertThat(deliveryTransactions1).isNotEqualTo(deliveryTransactions2);
        deliveryTransactions1.setId(null);
        assertThat(deliveryTransactions1).isNotEqualTo(deliveryTransactions2);
    }
}
