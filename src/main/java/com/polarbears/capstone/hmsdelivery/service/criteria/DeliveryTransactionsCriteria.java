package com.polarbears.capstone.hmsdelivery.service.criteria;

import com.polarbears.capstone.hmsdelivery.domain.enumeration.DELIVERYTYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions} entity. This class is used
 * in {@link com.polarbears.capstone.hmsdelivery.web.rest.DeliveryTransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delivery-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeliveryTransactionsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DELIVERYTYPES
     */
    public static class DELIVERYTYPESFilter extends Filter<DELIVERYTYPES> {

        public DELIVERYTYPESFilter() {}

        public DELIVERYTYPESFilter(DELIVERYTYPESFilter filter) {
            super(filter);
        }

        @Override
        public DELIVERYTYPESFilter copy() {
            return new DELIVERYTYPESFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter statusChangedDate;

    private LocalDateFilter transactionDate;

    private DELIVERYTYPESFilter type;

    private LocalDateFilter createdDate;

    private LongFilter deliveryOrdersId;

    private Boolean distinct;

    public DeliveryTransactionsCriteria() {}

    public DeliveryTransactionsCriteria(DeliveryTransactionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.statusChangedDate = other.statusChangedDate == null ? null : other.statusChangedDate.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.deliveryOrdersId = other.deliveryOrdersId == null ? null : other.deliveryOrdersId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DeliveryTransactionsCriteria copy() {
        return new DeliveryTransactionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getStatusChangedDate() {
        return statusChangedDate;
    }

    public LocalDateFilter statusChangedDate() {
        if (statusChangedDate == null) {
            statusChangedDate = new LocalDateFilter();
        }
        return statusChangedDate;
    }

    public void setStatusChangedDate(LocalDateFilter statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
    }

    public LocalDateFilter getTransactionDate() {
        return transactionDate;
    }

    public LocalDateFilter transactionDate() {
        if (transactionDate == null) {
            transactionDate = new LocalDateFilter();
        }
        return transactionDate;
    }

    public void setTransactionDate(LocalDateFilter transactionDate) {
        this.transactionDate = transactionDate;
    }

    public DELIVERYTYPESFilter getType() {
        return type;
    }

    public DELIVERYTYPESFilter type() {
        if (type == null) {
            type = new DELIVERYTYPESFilter();
        }
        return type;
    }

    public void setType(DELIVERYTYPESFilter type) {
        this.type = type;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public LocalDateFilter createdDate() {
        if (createdDate == null) {
            createdDate = new LocalDateFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getDeliveryOrdersId() {
        return deliveryOrdersId;
    }

    public LongFilter deliveryOrdersId() {
        if (deliveryOrdersId == null) {
            deliveryOrdersId = new LongFilter();
        }
        return deliveryOrdersId;
    }

    public void setDeliveryOrdersId(LongFilter deliveryOrdersId) {
        this.deliveryOrdersId = deliveryOrdersId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeliveryTransactionsCriteria that = (DeliveryTransactionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(statusChangedDate, that.statusChangedDate) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(type, that.type) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(deliveryOrdersId, that.deliveryOrdersId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusChangedDate, transactionDate, type, createdDate, deliveryOrdersId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryTransactionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (statusChangedDate != null ? "statusChangedDate=" + statusChangedDate + ", " : "") +
            (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (deliveryOrdersId != null ? "deliveryOrdersId=" + deliveryOrdersId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
