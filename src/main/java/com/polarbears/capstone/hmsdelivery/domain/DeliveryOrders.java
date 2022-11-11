package com.polarbears.capstone.hmsdelivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeliveryOrders.
 */
@Entity
@Table(name = "delivery_orders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeliveryOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_address_id")
    private Long contactAddressId;

    @Column(name = "contact_cart_id")
    private Long contactCartId;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "menu_item_id")
    private Long menuItemId;

    @Column(name = "menu_item_name")
    private String menuItemName;

    @Column(name = "menu_item_code")
    private String menuItemCode;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "detail")
    private String detail;

    @Size(max = 1024)
    @Column(name = "message", length = 1024)
    private String message;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToMany
    @JoinTable(
        name = "rel_delivery_orders__delivery_transactions",
        joinColumns = @JoinColumn(name = "delivery_orders_id"),
        inverseJoinColumns = @JoinColumn(name = "delivery_transactions_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deliveryOrders" }, allowSetters = true)
    private Set<DeliveryTransactions> deliveryTransactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeliveryOrders id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliveryId() {
        return this.deliveryId;
    }

    public DeliveryOrders deliveryId(Long deliveryId) {
        this.setDeliveryId(deliveryId);
        return this;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public DeliveryOrders invoiceNumber(String invoiceNumber) {
        this.setInvoiceNumber(invoiceNumber);
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getContactId() {
        return this.contactId;
    }

    public DeliveryOrders contactId(Long contactId) {
        this.setContactId(contactId);
        return this;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return this.contactName;
    }

    public DeliveryOrders contactName(String contactName) {
        this.setContactName(contactName);
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Long getContactAddressId() {
        return this.contactAddressId;
    }

    public DeliveryOrders contactAddressId(Long contactAddressId) {
        this.setContactAddressId(contactAddressId);
        return this;
    }

    public void setContactAddressId(Long contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

    public Long getContactCartId() {
        return this.contactCartId;
    }

    public DeliveryOrders contactCartId(Long contactCartId) {
        this.setContactCartId(contactCartId);
        return this;
    }

    public void setContactCartId(Long contactCartId) {
        this.contactCartId = contactCartId;
    }

    public LocalDate getDeliveryDate() {
        return this.deliveryDate;
    }

    public DeliveryOrders deliveryDate(LocalDate deliveryDate) {
        this.setDeliveryDate(deliveryDate);
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public DeliveryOrders requestDate(LocalDate requestDate) {
        this.setRequestDate(requestDate);
        return this;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public Long getMenuItemId() {
        return this.menuItemId;
    }

    public DeliveryOrders menuItemId(Long menuItemId) {
        this.setMenuItemId(menuItemId);
        return this;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return this.menuItemName;
    }

    public DeliveryOrders menuItemName(String menuItemName) {
        this.setMenuItemName(menuItemName);
        return this;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getMenuItemCode() {
        return this.menuItemCode;
    }

    public DeliveryOrders menuItemCode(String menuItemCode) {
        this.setMenuItemCode(menuItemCode);
        return this;
    }

    public void setMenuItemCode(String menuItemCode) {
        this.menuItemCode = menuItemCode;
    }

    public Integer getLineNumber() {
        return this.lineNumber;
    }

    public DeliveryOrders lineNumber(Integer lineNumber) {
        this.setLineNumber(lineNumber);
        return this;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDetail() {
        return this.detail;
    }

    public DeliveryOrders detail(String detail) {
        this.setDetail(detail);
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return this.message;
    }

    public DeliveryOrders message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public DeliveryOrders createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<DeliveryTransactions> getDeliveryTransactions() {
        return this.deliveryTransactions;
    }

    public void setDeliveryTransactions(Set<DeliveryTransactions> deliveryTransactions) {
        this.deliveryTransactions = deliveryTransactions;
    }

    public DeliveryOrders deliveryTransactions(Set<DeliveryTransactions> deliveryTransactions) {
        this.setDeliveryTransactions(deliveryTransactions);
        return this;
    }

    public DeliveryOrders addDeliveryTransactions(DeliveryTransactions deliveryTransactions) {
        this.deliveryTransactions.add(deliveryTransactions);
        deliveryTransactions.getDeliveryOrders().add(this);
        return this;
    }

    public DeliveryOrders removeDeliveryTransactions(DeliveryTransactions deliveryTransactions) {
        this.deliveryTransactions.remove(deliveryTransactions);
        deliveryTransactions.getDeliveryOrders().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryOrders)) {
            return false;
        }
        return id != null && id.equals(((DeliveryOrders) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryOrders{" +
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
            "}";
    }
}
