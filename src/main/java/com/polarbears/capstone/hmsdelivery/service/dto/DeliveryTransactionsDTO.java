package com.polarbears.capstone.hmsdelivery.service.dto;

import com.polarbears.capstone.hmsdelivery.domain.enumeration.DELIVERYTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeliveryTransactionsDTO implements Serializable {

    private Long id;

    private LocalDate statusChangedDate;

    private LocalDate transactionDate;

    private DELIVERYTYPES type;

    private LocalDate createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStatusChangedDate() {
        return statusChangedDate;
    }

    public void setStatusChangedDate(LocalDate statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public DELIVERYTYPES getType() {
        return type;
    }

    public void setType(DELIVERYTYPES type) {
        this.type = type;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryTransactionsDTO)) {
            return false;
        }

        DeliveryTransactionsDTO deliveryTransactionsDTO = (DeliveryTransactionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deliveryTransactionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryTransactionsDTO{" +
            "id=" + getId() +
            ", statusChangedDate='" + getStatusChangedDate() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
