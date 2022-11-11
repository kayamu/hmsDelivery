package com.polarbears.capstone.hmsdelivery.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsdelivery.IntegrationTest;
import com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders;
import com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions;
import com.polarbears.capstone.hmsdelivery.domain.enumeration.DELIVERYTYPES;
import com.polarbears.capstone.hmsdelivery.repository.DeliveryTransactionsRepository;
import com.polarbears.capstone.hmsdelivery.service.criteria.DeliveryTransactionsCriteria;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryTransactionsDTO;
import com.polarbears.capstone.hmsdelivery.service.mapper.DeliveryTransactionsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeliveryTransactionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeliveryTransactionsResourceIT {

    private static final LocalDate DEFAULT_STATUS_CHANGED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STATUS_CHANGED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STATUS_CHANGED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TRANSACTION_DATE = LocalDate.ofEpochDay(-1L);

    private static final DELIVERYTYPES DEFAULT_TYPE = DELIVERYTYPES.PREPARING;
    private static final DELIVERYTYPES UPDATED_TYPE = DELIVERYTYPES.ONTHEWAY;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/delivery-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeliveryTransactionsRepository deliveryTransactionsRepository;

    @Autowired
    private DeliveryTransactionsMapper deliveryTransactionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryTransactionsMockMvc;

    private DeliveryTransactions deliveryTransactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryTransactions createEntity(EntityManager em) {
        DeliveryTransactions deliveryTransactions = new DeliveryTransactions()
            .statusChangedDate(DEFAULT_STATUS_CHANGED_DATE)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .type(DEFAULT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE);
        return deliveryTransactions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryTransactions createUpdatedEntity(EntityManager em) {
        DeliveryTransactions deliveryTransactions = new DeliveryTransactions()
            .statusChangedDate(UPDATED_STATUS_CHANGED_DATE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .type(UPDATED_TYPE)
            .createdDate(UPDATED_CREATED_DATE);
        return deliveryTransactions;
    }

    @BeforeEach
    public void initTest() {
        deliveryTransactions = createEntity(em);
    }

    @Test
    @Transactional
    void createDeliveryTransactions() throws Exception {
        int databaseSizeBeforeCreate = deliveryTransactionsRepository.findAll().size();
        // Create the DeliveryTransactions
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(deliveryTransactions);
        restDeliveryTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryTransactions testDeliveryTransactions = deliveryTransactionsList.get(deliveryTransactionsList.size() - 1);
        assertThat(testDeliveryTransactions.getStatusChangedDate()).isEqualTo(DEFAULT_STATUS_CHANGED_DATE);
        assertThat(testDeliveryTransactions.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testDeliveryTransactions.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeliveryTransactions.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createDeliveryTransactionsWithExistingId() throws Exception {
        // Create the DeliveryTransactions with an existing ID
        deliveryTransactions.setId(1L);
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(deliveryTransactions);

        int databaseSizeBeforeCreate = deliveryTransactionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactions() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList
        restDeliveryTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusChangedDate").value(hasItem(DEFAULT_STATUS_CHANGED_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getDeliveryTransactions() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get the deliveryTransactions
        restDeliveryTransactionsMockMvc
            .perform(get(ENTITY_API_URL_ID, deliveryTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryTransactions.getId().intValue()))
            .andExpect(jsonPath("$.statusChangedDate").value(DEFAULT_STATUS_CHANGED_DATE.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getDeliveryTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        Long id = deliveryTransactions.getId();

        defaultDeliveryTransactionsShouldBeFound("id.equals=" + id);
        defaultDeliveryTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultDeliveryTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeliveryTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultDeliveryTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeliveryTransactionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByStatusChangedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where statusChangedDate equals to DEFAULT_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldBeFound("statusChangedDate.equals=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the deliveryTransactionsList where statusChangedDate equals to UPDATED_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("statusChangedDate.equals=" + UPDATED_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByStatusChangedDateIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where statusChangedDate in DEFAULT_STATUS_CHANGED_DATE or UPDATED_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldBeFound("statusChangedDate.in=" + DEFAULT_STATUS_CHANGED_DATE + "," + UPDATED_STATUS_CHANGED_DATE);

        // Get all the deliveryTransactionsList where statusChangedDate equals to UPDATED_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("statusChangedDate.in=" + UPDATED_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByStatusChangedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where statusChangedDate is not null
        defaultDeliveryTransactionsShouldBeFound("statusChangedDate.specified=true");

        // Get all the deliveryTransactionsList where statusChangedDate is null
        defaultDeliveryTransactionsShouldNotBeFound("statusChangedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByStatusChangedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where statusChangedDate is greater than or equal to DEFAULT_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldBeFound("statusChangedDate.greaterThanOrEqual=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the deliveryTransactionsList where statusChangedDate is greater than or equal to UPDATED_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("statusChangedDate.greaterThanOrEqual=" + UPDATED_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByStatusChangedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where statusChangedDate is less than or equal to DEFAULT_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldBeFound("statusChangedDate.lessThanOrEqual=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the deliveryTransactionsList where statusChangedDate is less than or equal to SMALLER_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("statusChangedDate.lessThanOrEqual=" + SMALLER_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByStatusChangedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where statusChangedDate is less than DEFAULT_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("statusChangedDate.lessThan=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the deliveryTransactionsList where statusChangedDate is less than UPDATED_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldBeFound("statusChangedDate.lessThan=" + UPDATED_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByStatusChangedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where statusChangedDate is greater than DEFAULT_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("statusChangedDate.greaterThan=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the deliveryTransactionsList where statusChangedDate is greater than SMALLER_STATUS_CHANGED_DATE
        defaultDeliveryTransactionsShouldBeFound("statusChangedDate.greaterThan=" + SMALLER_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the deliveryTransactionsList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the deliveryTransactionsList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where transactionDate is not null
        defaultDeliveryTransactionsShouldBeFound("transactionDate.specified=true");

        // Get all the deliveryTransactionsList where transactionDate is null
        defaultDeliveryTransactionsShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where transactionDate is greater than or equal to DEFAULT_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldBeFound("transactionDate.greaterThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the deliveryTransactionsList where transactionDate is greater than or equal to UPDATED_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldNotBeFound("transactionDate.greaterThanOrEqual=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTransactionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where transactionDate is less than or equal to DEFAULT_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldBeFound("transactionDate.lessThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the deliveryTransactionsList where transactionDate is less than or equal to SMALLER_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldNotBeFound("transactionDate.lessThanOrEqual=" + SMALLER_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where transactionDate is less than DEFAULT_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldNotBeFound("transactionDate.lessThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the deliveryTransactionsList where transactionDate is less than UPDATED_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldBeFound("transactionDate.lessThan=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTransactionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where transactionDate is greater than DEFAULT_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldNotBeFound("transactionDate.greaterThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the deliveryTransactionsList where transactionDate is greater than SMALLER_TRANSACTION_DATE
        defaultDeliveryTransactionsShouldBeFound("transactionDate.greaterThan=" + SMALLER_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where type equals to DEFAULT_TYPE
        defaultDeliveryTransactionsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the deliveryTransactionsList where type equals to UPDATED_TYPE
        defaultDeliveryTransactionsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDeliveryTransactionsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the deliveryTransactionsList where type equals to UPDATED_TYPE
        defaultDeliveryTransactionsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where type is not null
        defaultDeliveryTransactionsShouldBeFound("type.specified=true");

        // Get all the deliveryTransactionsList where type is null
        defaultDeliveryTransactionsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultDeliveryTransactionsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryTransactionsList where createdDate equals to UPDATED_CREATED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultDeliveryTransactionsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the deliveryTransactionsList where createdDate equals to UPDATED_CREATED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where createdDate is not null
        defaultDeliveryTransactionsShouldBeFound("createdDate.specified=true");

        // Get all the deliveryTransactionsList where createdDate is null
        defaultDeliveryTransactionsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultDeliveryTransactionsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryTransactionsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultDeliveryTransactionsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryTransactionsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryTransactionsList where createdDate is less than UPDATED_CREATED_DATE
        defaultDeliveryTransactionsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        // Get all the deliveryTransactionsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultDeliveryTransactionsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryTransactionsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultDeliveryTransactionsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryTransactionsByDeliveryOrdersIsEqualToSomething() throws Exception {
        DeliveryOrders deliveryOrders;
        if (TestUtil.findAll(em, DeliveryOrders.class).isEmpty()) {
            deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);
            deliveryOrders = DeliveryOrdersResourceIT.createEntity(em);
        } else {
            deliveryOrders = TestUtil.findAll(em, DeliveryOrders.class).get(0);
        }
        em.persist(deliveryOrders);
        em.flush();
        deliveryTransactions.addDeliveryOrders(deliveryOrders);
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);
        Long deliveryOrdersId = deliveryOrders.getId();

        // Get all the deliveryTransactionsList where deliveryOrders equals to deliveryOrdersId
        defaultDeliveryTransactionsShouldBeFound("deliveryOrdersId.equals=" + deliveryOrdersId);

        // Get all the deliveryTransactionsList where deliveryOrders equals to (deliveryOrdersId + 1)
        defaultDeliveryTransactionsShouldNotBeFound("deliveryOrdersId.equals=" + (deliveryOrdersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeliveryTransactionsShouldBeFound(String filter) throws Exception {
        restDeliveryTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusChangedDate").value(hasItem(DEFAULT_STATUS_CHANGED_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restDeliveryTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeliveryTransactionsShouldNotBeFound(String filter) throws Exception {
        restDeliveryTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeliveryTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDeliveryTransactions() throws Exception {
        // Get the deliveryTransactions
        restDeliveryTransactionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeliveryTransactions() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();

        // Update the deliveryTransactions
        DeliveryTransactions updatedDeliveryTransactions = deliveryTransactionsRepository.findById(deliveryTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryTransactions are not directly saved in db
        em.detach(updatedDeliveryTransactions);
        updatedDeliveryTransactions
            .statusChangedDate(UPDATED_STATUS_CHANGED_DATE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .type(UPDATED_TYPE)
            .createdDate(UPDATED_CREATED_DATE);
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(updatedDeliveryTransactions);

        restDeliveryTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryTransactionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryTransactions testDeliveryTransactions = deliveryTransactionsList.get(deliveryTransactionsList.size() - 1);
        assertThat(testDeliveryTransactions.getStatusChangedDate()).isEqualTo(UPDATED_STATUS_CHANGED_DATE);
        assertThat(testDeliveryTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testDeliveryTransactions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeliveryTransactions.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDeliveryTransactions() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();
        deliveryTransactions.setId(count.incrementAndGet());

        // Create the DeliveryTransactions
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(deliveryTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryTransactionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeliveryTransactions() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();
        deliveryTransactions.setId(count.incrementAndGet());

        // Create the DeliveryTransactions
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(deliveryTransactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeliveryTransactions() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();
        deliveryTransactions.setId(count.incrementAndGet());

        // Create the DeliveryTransactions
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(deliveryTransactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeliveryTransactionsWithPatch() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();

        // Update the deliveryTransactions using partial update
        DeliveryTransactions partialUpdatedDeliveryTransactions = new DeliveryTransactions();
        partialUpdatedDeliveryTransactions.setId(deliveryTransactions.getId());

        partialUpdatedDeliveryTransactions.transactionDate(UPDATED_TRANSACTION_DATE);

        restDeliveryTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryTransactions))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryTransactions testDeliveryTransactions = deliveryTransactionsList.get(deliveryTransactionsList.size() - 1);
        assertThat(testDeliveryTransactions.getStatusChangedDate()).isEqualTo(DEFAULT_STATUS_CHANGED_DATE);
        assertThat(testDeliveryTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testDeliveryTransactions.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeliveryTransactions.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDeliveryTransactionsWithPatch() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();

        // Update the deliveryTransactions using partial update
        DeliveryTransactions partialUpdatedDeliveryTransactions = new DeliveryTransactions();
        partialUpdatedDeliveryTransactions.setId(deliveryTransactions.getId());

        partialUpdatedDeliveryTransactions
            .statusChangedDate(UPDATED_STATUS_CHANGED_DATE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .type(UPDATED_TYPE)
            .createdDate(UPDATED_CREATED_DATE);

        restDeliveryTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryTransactions))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryTransactions testDeliveryTransactions = deliveryTransactionsList.get(deliveryTransactionsList.size() - 1);
        assertThat(testDeliveryTransactions.getStatusChangedDate()).isEqualTo(UPDATED_STATUS_CHANGED_DATE);
        assertThat(testDeliveryTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testDeliveryTransactions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeliveryTransactions.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDeliveryTransactions() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();
        deliveryTransactions.setId(count.incrementAndGet());

        // Create the DeliveryTransactions
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(deliveryTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deliveryTransactionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeliveryTransactions() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();
        deliveryTransactions.setId(count.incrementAndGet());

        // Create the DeliveryTransactions
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(deliveryTransactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeliveryTransactions() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTransactionsRepository.findAll().size();
        deliveryTransactions.setId(count.incrementAndGet());

        // Create the DeliveryTransactions
        DeliveryTransactionsDTO deliveryTransactionsDTO = deliveryTransactionsMapper.toDto(deliveryTransactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryTransactionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryTransactions in the database
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeliveryTransactions() throws Exception {
        // Initialize the database
        deliveryTransactionsRepository.saveAndFlush(deliveryTransactions);

        int databaseSizeBeforeDelete = deliveryTransactionsRepository.findAll().size();

        // Delete the deliveryTransactions
        restDeliveryTransactionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, deliveryTransactions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryTransactions> deliveryTransactionsList = deliveryTransactionsRepository.findAll();
        assertThat(deliveryTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
