package com.polarbears.capstone.hmsdelivery.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders} entity. This class is used
 * in {@link com.polarbears.capstone.hmsdelivery.web.rest.DeliveryOrdersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delivery-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeliveryOrdersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter deliveryId;

    private StringFilter invoiceNumber;

    private LongFilter contactId;

    private StringFilter contactName;

    private LongFilter contactAddressId;

    private LongFilter contactCartId;

    private LocalDateFilter deliveryDate;

    private LocalDateFilter requestDate;

    private LongFilter menuItemId;

    private StringFilter menuItemName;

    private StringFilter menuItemCode;

    private IntegerFilter lineNumber;

    private StringFilter detail;

    private StringFilter message;

    private LocalDateFilter createdDate;

    private LongFilter deliveryTransactionsId;

    private Boolean distinct;

    public DeliveryOrdersCriteria() {}

    public DeliveryOrdersCriteria(DeliveryOrdersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deliveryId = other.deliveryId == null ? null : other.deliveryId.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.contactName = other.contactName == null ? null : other.contactName.copy();
        this.contactAddressId = other.contactAddressId == null ? null : other.contactAddressId.copy();
        this.contactCartId = other.contactCartId == null ? null : other.contactCartId.copy();
        this.deliveryDate = other.deliveryDate == null ? null : other.deliveryDate.copy();
        this.requestDate = other.requestDate == null ? null : other.requestDate.copy();
        this.menuItemId = other.menuItemId == null ? null : other.menuItemId.copy();
        this.menuItemName = other.menuItemName == null ? null : other.menuItemName.copy();
        this.menuItemCode = other.menuItemCode == null ? null : other.menuItemCode.copy();
        this.lineNumber = other.lineNumber == null ? null : other.lineNumber.copy();
        this.detail = other.detail == null ? null : other.detail.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.deliveryTransactionsId = other.deliveryTransactionsId == null ? null : other.deliveryTransactionsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DeliveryOrdersCriteria copy() {
        return new DeliveryOrdersCriteria(this);
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

    public LongFilter getDeliveryId() {
        return deliveryId;
    }

    public LongFilter deliveryId() {
        if (deliveryId == null) {
            deliveryId = new LongFilter();
        }
        return deliveryId;
    }

    public void setDeliveryId(LongFilter deliveryId) {
        this.deliveryId = deliveryId;
    }

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public StringFilter invoiceNumber() {
        if (invoiceNumber == null) {
            invoiceNumber = new StringFilter();
        }
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LongFilter getContactId() {
        return contactId;
    }

    public LongFilter contactId() {
        if (contactId == null) {
            contactId = new LongFilter();
        }
        return contactId;
    }

    public void setContactId(LongFilter contactId) {
        this.contactId = contactId;
    }

    public StringFilter getContactName() {
        return contactName;
    }

    public StringFilter contactName() {
        if (contactName == null) {
            contactName = new StringFilter();
        }
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public LongFilter getContactAddressId() {
        return contactAddressId;
    }

    public LongFilter contactAddressId() {
        if (contactAddressId == null) {
            contactAddressId = new LongFilter();
        }
        return contactAddressId;
    }

    public void setContactAddressId(LongFilter contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

    public LongFilter getContactCartId() {
        return contactCartId;
    }

    public LongFilter contactCartId() {
        if (contactCartId == null) {
            contactCartId = new LongFilter();
        }
        return contactCartId;
    }

    public void setContactCartId(LongFilter contactCartId) {
        this.contactCartId = contactCartId;
    }

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public LocalDateFilter deliveryDate() {
        if (deliveryDate == null) {
            deliveryDate = new LocalDateFilter();
        }
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateFilter getRequestDate() {
        return requestDate;
    }

    public LocalDateFilter requestDate() {
        if (requestDate == null) {
            requestDate = new LocalDateFilter();
        }
        return requestDate;
    }

    public void setRequestDate(LocalDateFilter requestDate) {
        this.requestDate = requestDate;
    }

    public LongFilter getMenuItemId() {
        return menuItemId;
    }

    public LongFilter menuItemId() {
        if (menuItemId == null) {
            menuItemId = new LongFilter();
        }
        return menuItemId;
    }

    public void setMenuItemId(LongFilter menuItemId) {
        this.menuItemId = menuItemId;
    }

    public StringFilter getMenuItemName() {
        return menuItemName;
    }

    public StringFilter menuItemName() {
        if (menuItemName == null) {
            menuItemName = new StringFilter();
        }
        return menuItemName;
    }

    public void setMenuItemName(StringFilter menuItemName) {
        this.menuItemName = menuItemName;
    }

    public StringFilter getMenuItemCode() {
        return menuItemCode;
    }

    public StringFilter menuItemCode() {
        if (menuItemCode == null) {
            menuItemCode = new StringFilter();
        }
        return menuItemCode;
    }

    public void setMenuItemCode(StringFilter menuItemCode) {
        this.menuItemCode = menuItemCode;
    }

    public IntegerFilter getLineNumber() {
        return lineNumber;
    }

    public IntegerFilter lineNumber() {
        if (lineNumber == null) {
            lineNumber = new IntegerFilter();
        }
        return lineNumber;
    }

    public void setLineNumber(IntegerFilter lineNumber) {
        this.lineNumber = lineNumber;
    }

    public StringFilter getDetail() {
        return detail;
    }

    public StringFilter detail() {
        if (detail == null) {
            detail = new StringFilter();
        }
        return detail;
    }

    public void setDetail(StringFilter detail) {
        this.detail = detail;
    }

    public StringFilter getMessage() {
        return message;
    }

    public StringFilter message() {
        if (message == null) {
            message = new StringFilter();
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
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

    public LongFilter getDeliveryTransactionsId() {
        return deliveryTransactionsId;
    }

    public LongFilter deliveryTransactionsId() {
        if (deliveryTransactionsId == null) {
            deliveryTransactionsId = new LongFilter();
        }
        return deliveryTransactionsId;
    }

    public void setDeliveryTransactionsId(LongFilter deliveryTransactionsId) {
        this.deliveryTransactionsId = deliveryTransactionsId;
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
        final DeliveryOrdersCriteria that = (DeliveryOrdersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(deliveryId, that.deliveryId) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(contactAddressId, that.contactAddressId) &&
            Objects.equals(contactCartId, that.contactCartId) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(requestDate, that.requestDate) &&
            Objects.equals(menuItemId, that.menuItemId) &&
            Objects.equals(menuItemName, that.menuItemName) &&
            Objects.equals(menuItemCode, that.menuItemCode) &&
            Objects.equals(lineNumber, that.lineNumber) &&
            Objects.equals(detail, that.detail) &&
            Objects.equals(message, that.message) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(deliveryTransactionsId, that.deliveryTransactionsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            deliveryId,
            invoiceNumber,
            contactId,
            contactName,
            contactAddressId,
            contactCartId,
            deliveryDate,
            requestDate,
            menuItemId,
            menuItemName,
            menuItemCode,
            lineNumber,
            detail,
            message,
            createdDate,
            deliveryTransactionsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryOrdersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (deliveryId != null ? "deliveryId=" + deliveryId + ", " : "") +
            (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (contactName != null ? "contactName=" + contactName + ", " : "") +
            (contactAddressId != null ? "contactAddressId=" + contactAddressId + ", " : "") +
            (contactCartId != null ? "contactCartId=" + contactCartId + ", " : "") +
            (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
            (requestDate != null ? "requestDate=" + requestDate + ", " : "") +
            (menuItemId != null ? "menuItemId=" + menuItemId + ", " : "") +
            (menuItemName != null ? "menuItemName=" + menuItemName + ", " : "") +
            (menuItemCode != null ? "menuItemCode=" + menuItemCode + ", " : "") +
            (lineNumber != null ? "lineNumber=" + lineNumber + ", " : "") +
            (detail != null ? "detail=" + detail + ", " : "") +
            (message != null ? "message=" + message + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (deliveryTransactionsId != null ? "deliveryTransactionsId=" + deliveryTransactionsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
