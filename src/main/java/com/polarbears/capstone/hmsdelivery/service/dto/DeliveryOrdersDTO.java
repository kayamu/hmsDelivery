package com.polarbears.capstone.hmsdelivery.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeliveryOrdersDTO implements Serializable {

    private Long id;

    private Long deliveryId;

    private String invoiceNumber;

    private Long contactId;

    private String contactName;

    private Long contactAddressId;

    private Long contactCartId;

    private LocalDate deliveryDate;

    private LocalDate requestDate;

    private Long menuItemId;

    private String menuItemName;

    private String menuItemCode;

    private Integer lineNumber;

    private String detail;

    @Size(max = 1024)
    private String message;

    private LocalDate createdDate;

    private Set<DeliveryTransactionsDTO> deliveryTransactions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Long getContactAddressId() {
        return contactAddressId;
    }

    public void setContactAddressId(Long contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

    public Long getContactCartId() {
        return contactCartId;
    }

    public void setContactCartId(Long contactCartId) {
        this.contactCartId = contactCartId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getMenuItemCode() {
        return menuItemCode;
    }

    public void setMenuItemCode(String menuItemCode) {
        this.menuItemCode = menuItemCode;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<DeliveryTransactionsDTO> getDeliveryTransactions() {
        return deliveryTransactions;
    }

    public void setDeliveryTransactions(Set<DeliveryTransactionsDTO> deliveryTransactions) {
        this.deliveryTransactions = deliveryTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryOrdersDTO)) {
            return false;
        }

        DeliveryOrdersDTO deliveryOrdersDTO = (DeliveryOrdersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deliveryOrdersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryOrdersDTO{" +
            "id=" + getId() +
            ", deliveryId=" + getDeliveryId() +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", contactId=" + getContactId() +
            ", contactName='" + getContactName() + "'" +
            ", contactAddressId=" + getContactAddressId() +
            ", contactCartId=" + getContactCartId() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", menuItemId=" + getMenuItemId() +
            ", menuItemName='" + getMenuItemName() + "'" +
            ", menuItemCode='" + getMenuItemCode() + "'" +
            ", lineNumber=" + getLineNumber() +
            ", detail='" + getDetail() + "'" +
            ", message='" + getMessage() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deliveryTransactions=" + getDeliveryTransactions() +
            "}";
    }
}
