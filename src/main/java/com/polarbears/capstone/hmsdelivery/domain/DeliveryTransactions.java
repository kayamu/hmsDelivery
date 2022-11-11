package com.polarbears.capstone.hmsdelivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsdelivery.domain.enumeration.DELIVERYTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeliveryTransactions.
 */
@Entity
@Table(name = "delivery_transactions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeliveryTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "status_changed_date")
    private LocalDate statusChangedDate;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DELIVERYTYPES type;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToMany(mappedBy = "deliveryTransactions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deliveryTransactions" }, allowSetters = true)
    private Set<DeliveryOrders> deliveryOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeliveryTransactions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStatusChangedDate() {
        return this.statusChangedDate;
    }

    public DeliveryTransactions statusChangedDate(LocalDate statusChangedDate) {
        this.setStatusChangedDate(statusChangedDate);
        return this;
    }

    public void setStatusChangedDate(LocalDate statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
    }

    public LocalDate getTransactionDate() {
        return this.transactionDate;
    }

    public DeliveryTransactions transactionDate(LocalDate transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public DELIVERYTYPES getType() {
        return this.type;
    }

    public DeliveryTransactions type(DELIVERYTYPES type) {
        this.setType(type);
        return this;
    }

    public void setType(DELIVERYTYPES type) {
        this.type = type;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public DeliveryTransactions createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<DeliveryOrders> getDeliveryOrders() {
        return this.deliveryOrders;
    }

    public void setDeliveryOrders(Set<DeliveryOrders> deliveryOrders) {
        if (this.deliveryOrders != null) {
            this.deliveryOrders.forEach(i -> i.removeDeliveryTransactions(this));
        }
        if (deliveryOrders != null) {
            deliveryOrders.forEach(i -> i.addDeliveryTransactions(this));
        }
        this.deliveryOrders = deliveryOrders;
    }

    public DeliveryTransactions deliveryOrders(Set<DeliveryOrders> deliveryOrders) {
        this.setDeliveryOrders(deliveryOrders);
        return this;
    }

    public DeliveryTransactions addDeliveryOrders(DeliveryOrders deliveryOrders) {
        this.deliveryOrders.add(deliveryOrders);
        deliveryOrders.getDeliveryTransactions().add(this);
        return this;
    }

    public DeliveryTransactions removeDeliveryOrders(DeliveryOrders deliveryOrders) {
        this.deliveryOrders.remove(deliveryOrders);
        deliveryOrders.getDeliveryTransactions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryTransactions)) {
            return false;
        }
        return id != null && id.equals(((DeliveryTransactions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryTransactions{" +
            "id=" + getId() +
            ", statusChangedDate='" + getStatusChangedDate() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
