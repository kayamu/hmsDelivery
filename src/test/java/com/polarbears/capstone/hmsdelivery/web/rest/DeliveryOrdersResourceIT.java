package com.polarbears.capstone.hmsdelivery.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsdelivery.IntegrationTest;
import com.polarbears.capstone.hmsdelivery.domain.DeliveryOrders;
import com.polarbears.capstone.hmsdelivery.domain.DeliveryTransactions;
import com.polarbears.capstone.hmsdelivery.repository.DeliveryOrdersRepository;
import com.polarbears.capstone.hmsdelivery.service.DeliveryOrdersService;
import com.polarbears.capstone.hmsdelivery.service.criteria.DeliveryOrdersCriteria;
import com.polarbears.capstone.hmsdelivery.service.dto.DeliveryOrdersDTO;
import com.polarbears.capstone.hmsdelivery.service.mapper.DeliveryOrdersMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeliveryOrdersResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DeliveryOrdersResourceIT {

    private static final Long DEFAULT_DELIVERY_ID = 1L;
    private static final Long UPDATED_DELIVERY_ID = 2L;
    private static final Long SMALLER_DELIVERY_ID = 1L - 1L;

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_ID = 1L;
    private static final Long UPDATED_CONTACT_ID = 2L;
    private static final Long SMALLER_CONTACT_ID = 1L - 1L;

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_ADDRESS_ID = 1L;
    private static final Long UPDATED_CONTACT_ADDRESS_ID = 2L;
    private static final Long SMALLER_CONTACT_ADDRESS_ID = 1L - 1L;

    private static final Long DEFAULT_CONTACT_CART_ID = 1L;
    private static final Long UPDATED_CONTACT_CART_ID = 2L;
    private static final Long SMALLER_CONTACT_CART_ID = 1L - 1L;

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DELIVERY_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REQUEST_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_MENU_ITEM_ID = 1L;
    private static final Long UPDATED_MENU_ITEM_ID = 2L;
    private static final Long SMALLER_MENU_ITEM_ID = 1L - 1L;

    private static final String DEFAULT_MENU_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MENU_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MENU_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MENU_ITEM_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LINE_NUMBER = 1;
    private static final Integer UPDATED_LINE_NUMBER = 2;
    private static final Integer SMALLER_LINE_NUMBER = 1 - 1;

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/delivery-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeliveryOrdersRepository deliveryOrdersRepository;

    @Mock
    private DeliveryOrdersRepository deliveryOrdersRepositoryMock;

    @Autowired
    private DeliveryOrdersMapper deliveryOrdersMapper;

    @Mock
    private DeliveryOrdersService deliveryOrdersServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryOrdersMockMvc;

    private DeliveryOrders deliveryOrders;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryOrders createEntity(EntityManager em) {
        DeliveryOrders deliveryOrders = new DeliveryOrders()
            .deliveryId(DEFAULT_DELIVERY_ID)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .contactId(DEFAULT_CONTACT_ID)
            .contactName(DEFAULT_CONTACT_NAME)
            .contactAddressId(DEFAULT_CONTACT_ADDRESS_ID)
            .contactCartId(DEFAULT_CONTACT_CART_ID)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .requestDate(DEFAULT_REQUEST_DATE)
            .menuItemId(DEFAULT_MENU_ITEM_ID)
            .menuItemName(DEFAULT_MENU_ITEM_NAME)
            .menuItemCode(DEFAULT_MENU_ITEM_CODE)
            .lineNumber(DEFAULT_LINE_NUMBER)
            .detail(DEFAULT_DETAIL)
            .message(DEFAULT_MESSAGE)
            .createdDate(DEFAULT_CREATED_DATE);
        return deliveryOrders;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryOrders createUpdatedEntity(EntityManager em) {
        DeliveryOrders deliveryOrders = new DeliveryOrders()
            .deliveryId(UPDATED_DELIVERY_ID)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .contactId(UPDATED_CONTACT_ID)
            .contactName(UPDATED_CONTACT_NAME)
            .contactAddressId(UPDATED_CONTACT_ADDRESS_ID)
            .contactCartId(UPDATED_CONTACT_CART_ID)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .requestDate(UPDATED_REQUEST_DATE)
            .menuItemId(UPDATED_MENU_ITEM_ID)
            .menuItemName(UPDATED_MENU_ITEM_NAME)
            .menuItemCode(UPDATED_MENU_ITEM_CODE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .detail(UPDATED_DETAIL)
            .message(UPDATED_MESSAGE)
            .createdDate(UPDATED_CREATED_DATE);
        return deliveryOrders;
    }

    @BeforeEach
    public void initTest() {
        deliveryOrders = createEntity(em);
    }

    @Test
    @Transactional
    void createDeliveryOrders() throws Exception {
        int databaseSizeBeforeCreate = deliveryOrdersRepository.findAll().size();
        // Create the DeliveryOrders
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(deliveryOrders);
        restDeliveryOrdersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryOrders testDeliveryOrders = deliveryOrdersList.get(deliveryOrdersList.size() - 1);
        assertThat(testDeliveryOrders.getDeliveryId()).isEqualTo(DEFAULT_DELIVERY_ID);
        assertThat(testDeliveryOrders.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testDeliveryOrders.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testDeliveryOrders.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testDeliveryOrders.getContactAddressId()).isEqualTo(DEFAULT_CONTACT_ADDRESS_ID);
        assertThat(testDeliveryOrders.getContactCartId()).isEqualTo(DEFAULT_CONTACT_CART_ID);
        assertThat(testDeliveryOrders.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testDeliveryOrders.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testDeliveryOrders.getMenuItemId()).isEqualTo(DEFAULT_MENU_ITEM_ID);
        assertThat(testDeliveryOrders.getMenuItemName()).isEqualTo(DEFAULT_MENU_ITEM_NAME);
        assertThat(testDeliveryOrders.getMenuItemCode()).isEqualTo(DEFAULT_MENU_ITEM_CODE);
        assertThat(testDeliveryOrders.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testDeliveryOrders.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testDeliveryOrders.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testDeliveryOrders.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createDeliveryOrdersWithExistingId() throws Exception {
        // Create the DeliveryOrders with an existing ID
        deliveryOrders.setId(1L);
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(deliveryOrders);

        int databaseSizeBeforeCreate = deliveryOrdersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryOrdersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeliveryOrders() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList
        restDeliveryOrdersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryId").value(hasItem(DEFAULT_DELIVERY_ID.intValue())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].contactAddressId").value(hasItem(DEFAULT_CONTACT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactCartId").value(hasItem(DEFAULT_CONTACT_CART_ID.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].menuItemId").value(hasItem(DEFAULT_MENU_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].menuItemName").value(hasItem(DEFAULT_MENU_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].menuItemCode").value(hasItem(DEFAULT_MENU_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDeliveryOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        when(deliveryOrdersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeliveryOrdersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(deliveryOrdersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDeliveryOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(deliveryOrdersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeliveryOrdersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(deliveryOrdersRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDeliveryOrders() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get the deliveryOrders
        restDeliveryOrdersMockMvc
            .perform(get(ENTITY_API_URL_ID, deliveryOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryOrders.getId().intValue()))
            .andExpect(jsonPath("$.deliveryId").value(DEFAULT_DELIVERY_ID.intValue()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.contactAddressId").value(DEFAULT_CONTACT_ADDRESS_ID.intValue()))
            .andExpect(jsonPath("$.contactCartId").value(DEFAULT_CONTACT_CART_ID.intValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.menuItemId").value(DEFAULT_MENU_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.menuItemName").value(DEFAULT_MENU_ITEM_NAME))
            .andExpect(jsonPath("$.menuItemCode").value(DEFAULT_MENU_ITEM_CODE))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getDeliveryOrdersByIdFiltering() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        Long id = deliveryOrders.getId();

        defaultDeliveryOrdersShouldBeFound("id.equals=" + id);
        defaultDeliveryOrdersShouldNotBeFound("id.notEquals=" + id);

        defaultDeliveryOrdersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeliveryOrdersShouldNotBeFound("id.greaterThan=" + id);

        defaultDeliveryOrdersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeliveryOrdersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryId equals to DEFAULT_DELIVERY_ID
        defaultDeliveryOrdersShouldBeFound("deliveryId.equals=" + DEFAULT_DELIVERY_ID);

        // Get all the deliveryOrdersList where deliveryId equals to UPDATED_DELIVERY_ID
        defaultDeliveryOrdersShouldNotBeFound("deliveryId.equals=" + UPDATED_DELIVERY_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryIdIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryId in DEFAULT_DELIVERY_ID or UPDATED_DELIVERY_ID
        defaultDeliveryOrdersShouldBeFound("deliveryId.in=" + DEFAULT_DELIVERY_ID + "," + UPDATED_DELIVERY_ID);

        // Get all the deliveryOrdersList where deliveryId equals to UPDATED_DELIVERY_ID
        defaultDeliveryOrdersShouldNotBeFound("deliveryId.in=" + UPDATED_DELIVERY_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryId is not null
        defaultDeliveryOrdersShouldBeFound("deliveryId.specified=true");

        // Get all the deliveryOrdersList where deliveryId is null
        defaultDeliveryOrdersShouldNotBeFound("deliveryId.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryId is greater than or equal to DEFAULT_DELIVERY_ID
        defaultDeliveryOrdersShouldBeFound("deliveryId.greaterThanOrEqual=" + DEFAULT_DELIVERY_ID);

        // Get all the deliveryOrdersList where deliveryId is greater than or equal to UPDATED_DELIVERY_ID
        defaultDeliveryOrdersShouldNotBeFound("deliveryId.greaterThanOrEqual=" + UPDATED_DELIVERY_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryId is less than or equal to DEFAULT_DELIVERY_ID
        defaultDeliveryOrdersShouldBeFound("deliveryId.lessThanOrEqual=" + DEFAULT_DELIVERY_ID);

        // Get all the deliveryOrdersList where deliveryId is less than or equal to SMALLER_DELIVERY_ID
        defaultDeliveryOrdersShouldNotBeFound("deliveryId.lessThanOrEqual=" + SMALLER_DELIVERY_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryId is less than DEFAULT_DELIVERY_ID
        defaultDeliveryOrdersShouldNotBeFound("deliveryId.lessThan=" + DEFAULT_DELIVERY_ID);

        // Get all the deliveryOrdersList where deliveryId is less than UPDATED_DELIVERY_ID
        defaultDeliveryOrdersShouldBeFound("deliveryId.lessThan=" + UPDATED_DELIVERY_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryId is greater than DEFAULT_DELIVERY_ID
        defaultDeliveryOrdersShouldNotBeFound("deliveryId.greaterThan=" + DEFAULT_DELIVERY_ID);

        // Get all the deliveryOrdersList where deliveryId is greater than SMALLER_DELIVERY_ID
        defaultDeliveryOrdersShouldBeFound("deliveryId.greaterThan=" + SMALLER_DELIVERY_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByInvoiceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where invoiceNumber equals to DEFAULT_INVOICE_NUMBER
        defaultDeliveryOrdersShouldBeFound("invoiceNumber.equals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the deliveryOrdersList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("invoiceNumber.equals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByInvoiceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where invoiceNumber in DEFAULT_INVOICE_NUMBER or UPDATED_INVOICE_NUMBER
        defaultDeliveryOrdersShouldBeFound("invoiceNumber.in=" + DEFAULT_INVOICE_NUMBER + "," + UPDATED_INVOICE_NUMBER);

        // Get all the deliveryOrdersList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("invoiceNumber.in=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByInvoiceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where invoiceNumber is not null
        defaultDeliveryOrdersShouldBeFound("invoiceNumber.specified=true");

        // Get all the deliveryOrdersList where invoiceNumber is null
        defaultDeliveryOrdersShouldNotBeFound("invoiceNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByInvoiceNumberContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where invoiceNumber contains DEFAULT_INVOICE_NUMBER
        defaultDeliveryOrdersShouldBeFound("invoiceNumber.contains=" + DEFAULT_INVOICE_NUMBER);

        // Get all the deliveryOrdersList where invoiceNumber contains UPDATED_INVOICE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("invoiceNumber.contains=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByInvoiceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where invoiceNumber does not contain DEFAULT_INVOICE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("invoiceNumber.doesNotContain=" + DEFAULT_INVOICE_NUMBER);

        // Get all the deliveryOrdersList where invoiceNumber does not contain UPDATED_INVOICE_NUMBER
        defaultDeliveryOrdersShouldBeFound("invoiceNumber.doesNotContain=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactId equals to DEFAULT_CONTACT_ID
        defaultDeliveryOrdersShouldBeFound("contactId.equals=" + DEFAULT_CONTACT_ID);

        // Get all the deliveryOrdersList where contactId equals to UPDATED_CONTACT_ID
        defaultDeliveryOrdersShouldNotBeFound("contactId.equals=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactIdIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactId in DEFAULT_CONTACT_ID or UPDATED_CONTACT_ID
        defaultDeliveryOrdersShouldBeFound("contactId.in=" + DEFAULT_CONTACT_ID + "," + UPDATED_CONTACT_ID);

        // Get all the deliveryOrdersList where contactId equals to UPDATED_CONTACT_ID
        defaultDeliveryOrdersShouldNotBeFound("contactId.in=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactId is not null
        defaultDeliveryOrdersShouldBeFound("contactId.specified=true");

        // Get all the deliveryOrdersList where contactId is null
        defaultDeliveryOrdersShouldNotBeFound("contactId.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactId is greater than or equal to DEFAULT_CONTACT_ID
        defaultDeliveryOrdersShouldBeFound("contactId.greaterThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the deliveryOrdersList where contactId is greater than or equal to UPDATED_CONTACT_ID
        defaultDeliveryOrdersShouldNotBeFound("contactId.greaterThanOrEqual=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactId is less than or equal to DEFAULT_CONTACT_ID
        defaultDeliveryOrdersShouldBeFound("contactId.lessThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the deliveryOrdersList where contactId is less than or equal to SMALLER_CONTACT_ID
        defaultDeliveryOrdersShouldNotBeFound("contactId.lessThanOrEqual=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactIdIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactId is less than DEFAULT_CONTACT_ID
        defaultDeliveryOrdersShouldNotBeFound("contactId.lessThan=" + DEFAULT_CONTACT_ID);

        // Get all the deliveryOrdersList where contactId is less than UPDATED_CONTACT_ID
        defaultDeliveryOrdersShouldBeFound("contactId.lessThan=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactId is greater than DEFAULT_CONTACT_ID
        defaultDeliveryOrdersShouldNotBeFound("contactId.greaterThan=" + DEFAULT_CONTACT_ID);

        // Get all the deliveryOrdersList where contactId is greater than SMALLER_CONTACT_ID
        defaultDeliveryOrdersShouldBeFound("contactId.greaterThan=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactName equals to DEFAULT_CONTACT_NAME
        defaultDeliveryOrdersShouldBeFound("contactName.equals=" + DEFAULT_CONTACT_NAME);

        // Get all the deliveryOrdersList where contactName equals to UPDATED_CONTACT_NAME
        defaultDeliveryOrdersShouldNotBeFound("contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactName in DEFAULT_CONTACT_NAME or UPDATED_CONTACT_NAME
        defaultDeliveryOrdersShouldBeFound("contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME);

        // Get all the deliveryOrdersList where contactName equals to UPDATED_CONTACT_NAME
        defaultDeliveryOrdersShouldNotBeFound("contactName.in=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactName is not null
        defaultDeliveryOrdersShouldBeFound("contactName.specified=true");

        // Get all the deliveryOrdersList where contactName is null
        defaultDeliveryOrdersShouldNotBeFound("contactName.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactNameContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactName contains DEFAULT_CONTACT_NAME
        defaultDeliveryOrdersShouldBeFound("contactName.contains=" + DEFAULT_CONTACT_NAME);

        // Get all the deliveryOrdersList where contactName contains UPDATED_CONTACT_NAME
        defaultDeliveryOrdersShouldNotBeFound("contactName.contains=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactName does not contain DEFAULT_CONTACT_NAME
        defaultDeliveryOrdersShouldNotBeFound("contactName.doesNotContain=" + DEFAULT_CONTACT_NAME);

        // Get all the deliveryOrdersList where contactName does not contain UPDATED_CONTACT_NAME
        defaultDeliveryOrdersShouldBeFound("contactName.doesNotContain=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactAddressIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactAddressId equals to DEFAULT_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldBeFound("contactAddressId.equals=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the deliveryOrdersList where contactAddressId equals to UPDATED_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldNotBeFound("contactAddressId.equals=" + UPDATED_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactAddressIdIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactAddressId in DEFAULT_CONTACT_ADDRESS_ID or UPDATED_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldBeFound("contactAddressId.in=" + DEFAULT_CONTACT_ADDRESS_ID + "," + UPDATED_CONTACT_ADDRESS_ID);

        // Get all the deliveryOrdersList where contactAddressId equals to UPDATED_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldNotBeFound("contactAddressId.in=" + UPDATED_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactAddressIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactAddressId is not null
        defaultDeliveryOrdersShouldBeFound("contactAddressId.specified=true");

        // Get all the deliveryOrdersList where contactAddressId is null
        defaultDeliveryOrdersShouldNotBeFound("contactAddressId.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactAddressIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactAddressId is greater than or equal to DEFAULT_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldBeFound("contactAddressId.greaterThanOrEqual=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the deliveryOrdersList where contactAddressId is greater than or equal to UPDATED_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldNotBeFound("contactAddressId.greaterThanOrEqual=" + UPDATED_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactAddressIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactAddressId is less than or equal to DEFAULT_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldBeFound("contactAddressId.lessThanOrEqual=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the deliveryOrdersList where contactAddressId is less than or equal to SMALLER_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldNotBeFound("contactAddressId.lessThanOrEqual=" + SMALLER_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactAddressIdIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactAddressId is less than DEFAULT_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldNotBeFound("contactAddressId.lessThan=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the deliveryOrdersList where contactAddressId is less than UPDATED_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldBeFound("contactAddressId.lessThan=" + UPDATED_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactAddressIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactAddressId is greater than DEFAULT_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldNotBeFound("contactAddressId.greaterThan=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the deliveryOrdersList where contactAddressId is greater than SMALLER_CONTACT_ADDRESS_ID
        defaultDeliveryOrdersShouldBeFound("contactAddressId.greaterThan=" + SMALLER_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactCartIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactCartId equals to DEFAULT_CONTACT_CART_ID
        defaultDeliveryOrdersShouldBeFound("contactCartId.equals=" + DEFAULT_CONTACT_CART_ID);

        // Get all the deliveryOrdersList where contactCartId equals to UPDATED_CONTACT_CART_ID
        defaultDeliveryOrdersShouldNotBeFound("contactCartId.equals=" + UPDATED_CONTACT_CART_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactCartIdIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactCartId in DEFAULT_CONTACT_CART_ID or UPDATED_CONTACT_CART_ID
        defaultDeliveryOrdersShouldBeFound("contactCartId.in=" + DEFAULT_CONTACT_CART_ID + "," + UPDATED_CONTACT_CART_ID);

        // Get all the deliveryOrdersList where contactCartId equals to UPDATED_CONTACT_CART_ID
        defaultDeliveryOrdersShouldNotBeFound("contactCartId.in=" + UPDATED_CONTACT_CART_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactCartIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactCartId is not null
        defaultDeliveryOrdersShouldBeFound("contactCartId.specified=true");

        // Get all the deliveryOrdersList where contactCartId is null
        defaultDeliveryOrdersShouldNotBeFound("contactCartId.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactCartIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactCartId is greater than or equal to DEFAULT_CONTACT_CART_ID
        defaultDeliveryOrdersShouldBeFound("contactCartId.greaterThanOrEqual=" + DEFAULT_CONTACT_CART_ID);

        // Get all the deliveryOrdersList where contactCartId is greater than or equal to UPDATED_CONTACT_CART_ID
        defaultDeliveryOrdersShouldNotBeFound("contactCartId.greaterThanOrEqual=" + UPDATED_CONTACT_CART_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactCartIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactCartId is less than or equal to DEFAULT_CONTACT_CART_ID
        defaultDeliveryOrdersShouldBeFound("contactCartId.lessThanOrEqual=" + DEFAULT_CONTACT_CART_ID);

        // Get all the deliveryOrdersList where contactCartId is less than or equal to SMALLER_CONTACT_CART_ID
        defaultDeliveryOrdersShouldNotBeFound("contactCartId.lessThanOrEqual=" + SMALLER_CONTACT_CART_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactCartIdIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactCartId is less than DEFAULT_CONTACT_CART_ID
        defaultDeliveryOrdersShouldNotBeFound("contactCartId.lessThan=" + DEFAULT_CONTACT_CART_ID);

        // Get all the deliveryOrdersList where contactCartId is less than UPDATED_CONTACT_CART_ID
        defaultDeliveryOrdersShouldBeFound("contactCartId.lessThan=" + UPDATED_CONTACT_CART_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByContactCartIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where contactCartId is greater than DEFAULT_CONTACT_CART_ID
        defaultDeliveryOrdersShouldNotBeFound("contactCartId.greaterThan=" + DEFAULT_CONTACT_CART_ID);

        // Get all the deliveryOrdersList where contactCartId is greater than SMALLER_CONTACT_CART_ID
        defaultDeliveryOrdersShouldBeFound("contactCartId.greaterThan=" + SMALLER_CONTACT_CART_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultDeliveryOrdersShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the deliveryOrdersList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultDeliveryOrdersShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultDeliveryOrdersShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the deliveryOrdersList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultDeliveryOrdersShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryDate is not null
        defaultDeliveryOrdersShouldBeFound("deliveryDate.specified=true");

        // Get all the deliveryOrdersList where deliveryDate is null
        defaultDeliveryOrdersShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryDate is greater than or equal to DEFAULT_DELIVERY_DATE
        defaultDeliveryOrdersShouldBeFound("deliveryDate.greaterThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the deliveryOrdersList where deliveryDate is greater than or equal to UPDATED_DELIVERY_DATE
        defaultDeliveryOrdersShouldNotBeFound("deliveryDate.greaterThanOrEqual=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryDate is less than or equal to DEFAULT_DELIVERY_DATE
        defaultDeliveryOrdersShouldBeFound("deliveryDate.lessThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the deliveryOrdersList where deliveryDate is less than or equal to SMALLER_DELIVERY_DATE
        defaultDeliveryOrdersShouldNotBeFound("deliveryDate.lessThanOrEqual=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryDate is less than DEFAULT_DELIVERY_DATE
        defaultDeliveryOrdersShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the deliveryOrdersList where deliveryDate is less than UPDATED_DELIVERY_DATE
        defaultDeliveryOrdersShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where deliveryDate is greater than DEFAULT_DELIVERY_DATE
        defaultDeliveryOrdersShouldNotBeFound("deliveryDate.greaterThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the deliveryOrdersList where deliveryDate is greater than SMALLER_DELIVERY_DATE
        defaultDeliveryOrdersShouldBeFound("deliveryDate.greaterThan=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByRequestDateIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where requestDate equals to DEFAULT_REQUEST_DATE
        defaultDeliveryOrdersShouldBeFound("requestDate.equals=" + DEFAULT_REQUEST_DATE);

        // Get all the deliveryOrdersList where requestDate equals to UPDATED_REQUEST_DATE
        defaultDeliveryOrdersShouldNotBeFound("requestDate.equals=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByRequestDateIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where requestDate in DEFAULT_REQUEST_DATE or UPDATED_REQUEST_DATE
        defaultDeliveryOrdersShouldBeFound("requestDate.in=" + DEFAULT_REQUEST_DATE + "," + UPDATED_REQUEST_DATE);

        // Get all the deliveryOrdersList where requestDate equals to UPDATED_REQUEST_DATE
        defaultDeliveryOrdersShouldNotBeFound("requestDate.in=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByRequestDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where requestDate is not null
        defaultDeliveryOrdersShouldBeFound("requestDate.specified=true");

        // Get all the deliveryOrdersList where requestDate is null
        defaultDeliveryOrdersShouldNotBeFound("requestDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByRequestDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where requestDate is greater than or equal to DEFAULT_REQUEST_DATE
        defaultDeliveryOrdersShouldBeFound("requestDate.greaterThanOrEqual=" + DEFAULT_REQUEST_DATE);

        // Get all the deliveryOrdersList where requestDate is greater than or equal to UPDATED_REQUEST_DATE
        defaultDeliveryOrdersShouldNotBeFound("requestDate.greaterThanOrEqual=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByRequestDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where requestDate is less than or equal to DEFAULT_REQUEST_DATE
        defaultDeliveryOrdersShouldBeFound("requestDate.lessThanOrEqual=" + DEFAULT_REQUEST_DATE);

        // Get all the deliveryOrdersList where requestDate is less than or equal to SMALLER_REQUEST_DATE
        defaultDeliveryOrdersShouldNotBeFound("requestDate.lessThanOrEqual=" + SMALLER_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByRequestDateIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where requestDate is less than DEFAULT_REQUEST_DATE
        defaultDeliveryOrdersShouldNotBeFound("requestDate.lessThan=" + DEFAULT_REQUEST_DATE);

        // Get all the deliveryOrdersList where requestDate is less than UPDATED_REQUEST_DATE
        defaultDeliveryOrdersShouldBeFound("requestDate.lessThan=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByRequestDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where requestDate is greater than DEFAULT_REQUEST_DATE
        defaultDeliveryOrdersShouldNotBeFound("requestDate.greaterThan=" + DEFAULT_REQUEST_DATE);

        // Get all the deliveryOrdersList where requestDate is greater than SMALLER_REQUEST_DATE
        defaultDeliveryOrdersShouldBeFound("requestDate.greaterThan=" + SMALLER_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemId equals to DEFAULT_MENU_ITEM_ID
        defaultDeliveryOrdersShouldBeFound("menuItemId.equals=" + DEFAULT_MENU_ITEM_ID);

        // Get all the deliveryOrdersList where menuItemId equals to UPDATED_MENU_ITEM_ID
        defaultDeliveryOrdersShouldNotBeFound("menuItemId.equals=" + UPDATED_MENU_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemId in DEFAULT_MENU_ITEM_ID or UPDATED_MENU_ITEM_ID
        defaultDeliveryOrdersShouldBeFound("menuItemId.in=" + DEFAULT_MENU_ITEM_ID + "," + UPDATED_MENU_ITEM_ID);

        // Get all the deliveryOrdersList where menuItemId equals to UPDATED_MENU_ITEM_ID
        defaultDeliveryOrdersShouldNotBeFound("menuItemId.in=" + UPDATED_MENU_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemId is not null
        defaultDeliveryOrdersShouldBeFound("menuItemId.specified=true");

        // Get all the deliveryOrdersList where menuItemId is null
        defaultDeliveryOrdersShouldNotBeFound("menuItemId.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemId is greater than or equal to DEFAULT_MENU_ITEM_ID
        defaultDeliveryOrdersShouldBeFound("menuItemId.greaterThanOrEqual=" + DEFAULT_MENU_ITEM_ID);

        // Get all the deliveryOrdersList where menuItemId is greater than or equal to UPDATED_MENU_ITEM_ID
        defaultDeliveryOrdersShouldNotBeFound("menuItemId.greaterThanOrEqual=" + UPDATED_MENU_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemId is less than or equal to DEFAULT_MENU_ITEM_ID
        defaultDeliveryOrdersShouldBeFound("menuItemId.lessThanOrEqual=" + DEFAULT_MENU_ITEM_ID);

        // Get all the deliveryOrdersList where menuItemId is less than or equal to SMALLER_MENU_ITEM_ID
        defaultDeliveryOrdersShouldNotBeFound("menuItemId.lessThanOrEqual=" + SMALLER_MENU_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemId is less than DEFAULT_MENU_ITEM_ID
        defaultDeliveryOrdersShouldNotBeFound("menuItemId.lessThan=" + DEFAULT_MENU_ITEM_ID);

        // Get all the deliveryOrdersList where menuItemId is less than UPDATED_MENU_ITEM_ID
        defaultDeliveryOrdersShouldBeFound("menuItemId.lessThan=" + UPDATED_MENU_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemId is greater than DEFAULT_MENU_ITEM_ID
        defaultDeliveryOrdersShouldNotBeFound("menuItemId.greaterThan=" + DEFAULT_MENU_ITEM_ID);

        // Get all the deliveryOrdersList where menuItemId is greater than SMALLER_MENU_ITEM_ID
        defaultDeliveryOrdersShouldBeFound("menuItemId.greaterThan=" + SMALLER_MENU_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemName equals to DEFAULT_MENU_ITEM_NAME
        defaultDeliveryOrdersShouldBeFound("menuItemName.equals=" + DEFAULT_MENU_ITEM_NAME);

        // Get all the deliveryOrdersList where menuItemName equals to UPDATED_MENU_ITEM_NAME
        defaultDeliveryOrdersShouldNotBeFound("menuItemName.equals=" + UPDATED_MENU_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemNameIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemName in DEFAULT_MENU_ITEM_NAME or UPDATED_MENU_ITEM_NAME
        defaultDeliveryOrdersShouldBeFound("menuItemName.in=" + DEFAULT_MENU_ITEM_NAME + "," + UPDATED_MENU_ITEM_NAME);

        // Get all the deliveryOrdersList where menuItemName equals to UPDATED_MENU_ITEM_NAME
        defaultDeliveryOrdersShouldNotBeFound("menuItemName.in=" + UPDATED_MENU_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemName is not null
        defaultDeliveryOrdersShouldBeFound("menuItemName.specified=true");

        // Get all the deliveryOrdersList where menuItemName is null
        defaultDeliveryOrdersShouldNotBeFound("menuItemName.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemNameContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemName contains DEFAULT_MENU_ITEM_NAME
        defaultDeliveryOrdersShouldBeFound("menuItemName.contains=" + DEFAULT_MENU_ITEM_NAME);

        // Get all the deliveryOrdersList where menuItemName contains UPDATED_MENU_ITEM_NAME
        defaultDeliveryOrdersShouldNotBeFound("menuItemName.contains=" + UPDATED_MENU_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemNameNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemName does not contain DEFAULT_MENU_ITEM_NAME
        defaultDeliveryOrdersShouldNotBeFound("menuItemName.doesNotContain=" + DEFAULT_MENU_ITEM_NAME);

        // Get all the deliveryOrdersList where menuItemName does not contain UPDATED_MENU_ITEM_NAME
        defaultDeliveryOrdersShouldBeFound("menuItemName.doesNotContain=" + UPDATED_MENU_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemCode equals to DEFAULT_MENU_ITEM_CODE
        defaultDeliveryOrdersShouldBeFound("menuItemCode.equals=" + DEFAULT_MENU_ITEM_CODE);

        // Get all the deliveryOrdersList where menuItemCode equals to UPDATED_MENU_ITEM_CODE
        defaultDeliveryOrdersShouldNotBeFound("menuItemCode.equals=" + UPDATED_MENU_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemCodeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemCode in DEFAULT_MENU_ITEM_CODE or UPDATED_MENU_ITEM_CODE
        defaultDeliveryOrdersShouldBeFound("menuItemCode.in=" + DEFAULT_MENU_ITEM_CODE + "," + UPDATED_MENU_ITEM_CODE);

        // Get all the deliveryOrdersList where menuItemCode equals to UPDATED_MENU_ITEM_CODE
        defaultDeliveryOrdersShouldNotBeFound("menuItemCode.in=" + UPDATED_MENU_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemCode is not null
        defaultDeliveryOrdersShouldBeFound("menuItemCode.specified=true");

        // Get all the deliveryOrdersList where menuItemCode is null
        defaultDeliveryOrdersShouldNotBeFound("menuItemCode.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemCodeContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemCode contains DEFAULT_MENU_ITEM_CODE
        defaultDeliveryOrdersShouldBeFound("menuItemCode.contains=" + DEFAULT_MENU_ITEM_CODE);

        // Get all the deliveryOrdersList where menuItemCode contains UPDATED_MENU_ITEM_CODE
        defaultDeliveryOrdersShouldNotBeFound("menuItemCode.contains=" + UPDATED_MENU_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMenuItemCodeNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where menuItemCode does not contain DEFAULT_MENU_ITEM_CODE
        defaultDeliveryOrdersShouldNotBeFound("menuItemCode.doesNotContain=" + DEFAULT_MENU_ITEM_CODE);

        // Get all the deliveryOrdersList where menuItemCode does not contain UPDATED_MENU_ITEM_CODE
        defaultDeliveryOrdersShouldBeFound("menuItemCode.doesNotContain=" + UPDATED_MENU_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByLineNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where lineNumber equals to DEFAULT_LINE_NUMBER
        defaultDeliveryOrdersShouldBeFound("lineNumber.equals=" + DEFAULT_LINE_NUMBER);

        // Get all the deliveryOrdersList where lineNumber equals to UPDATED_LINE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("lineNumber.equals=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByLineNumberIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where lineNumber in DEFAULT_LINE_NUMBER or UPDATED_LINE_NUMBER
        defaultDeliveryOrdersShouldBeFound("lineNumber.in=" + DEFAULT_LINE_NUMBER + "," + UPDATED_LINE_NUMBER);

        // Get all the deliveryOrdersList where lineNumber equals to UPDATED_LINE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("lineNumber.in=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByLineNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where lineNumber is not null
        defaultDeliveryOrdersShouldBeFound("lineNumber.specified=true");

        // Get all the deliveryOrdersList where lineNumber is null
        defaultDeliveryOrdersShouldNotBeFound("lineNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByLineNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where lineNumber is greater than or equal to DEFAULT_LINE_NUMBER
        defaultDeliveryOrdersShouldBeFound("lineNumber.greaterThanOrEqual=" + DEFAULT_LINE_NUMBER);

        // Get all the deliveryOrdersList where lineNumber is greater than or equal to UPDATED_LINE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("lineNumber.greaterThanOrEqual=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByLineNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where lineNumber is less than or equal to DEFAULT_LINE_NUMBER
        defaultDeliveryOrdersShouldBeFound("lineNumber.lessThanOrEqual=" + DEFAULT_LINE_NUMBER);

        // Get all the deliveryOrdersList where lineNumber is less than or equal to SMALLER_LINE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("lineNumber.lessThanOrEqual=" + SMALLER_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByLineNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where lineNumber is less than DEFAULT_LINE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("lineNumber.lessThan=" + DEFAULT_LINE_NUMBER);

        // Get all the deliveryOrdersList where lineNumber is less than UPDATED_LINE_NUMBER
        defaultDeliveryOrdersShouldBeFound("lineNumber.lessThan=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByLineNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where lineNumber is greater than DEFAULT_LINE_NUMBER
        defaultDeliveryOrdersShouldNotBeFound("lineNumber.greaterThan=" + DEFAULT_LINE_NUMBER);

        // Get all the deliveryOrdersList where lineNumber is greater than SMALLER_LINE_NUMBER
        defaultDeliveryOrdersShouldBeFound("lineNumber.greaterThan=" + SMALLER_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where detail equals to DEFAULT_DETAIL
        defaultDeliveryOrdersShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the deliveryOrdersList where detail equals to UPDATED_DETAIL
        defaultDeliveryOrdersShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultDeliveryOrdersShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the deliveryOrdersList where detail equals to UPDATED_DETAIL
        defaultDeliveryOrdersShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where detail is not null
        defaultDeliveryOrdersShouldBeFound("detail.specified=true");

        // Get all the deliveryOrdersList where detail is null
        defaultDeliveryOrdersShouldNotBeFound("detail.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDetailContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where detail contains DEFAULT_DETAIL
        defaultDeliveryOrdersShouldBeFound("detail.contains=" + DEFAULT_DETAIL);

        // Get all the deliveryOrdersList where detail contains UPDATED_DETAIL
        defaultDeliveryOrdersShouldNotBeFound("detail.contains=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDetailNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where detail does not contain DEFAULT_DETAIL
        defaultDeliveryOrdersShouldNotBeFound("detail.doesNotContain=" + DEFAULT_DETAIL);

        // Get all the deliveryOrdersList where detail does not contain UPDATED_DETAIL
        defaultDeliveryOrdersShouldBeFound("detail.doesNotContain=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where message equals to DEFAULT_MESSAGE
        defaultDeliveryOrdersShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the deliveryOrdersList where message equals to UPDATED_MESSAGE
        defaultDeliveryOrdersShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultDeliveryOrdersShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the deliveryOrdersList where message equals to UPDATED_MESSAGE
        defaultDeliveryOrdersShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where message is not null
        defaultDeliveryOrdersShouldBeFound("message.specified=true");

        // Get all the deliveryOrdersList where message is null
        defaultDeliveryOrdersShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMessageContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where message contains DEFAULT_MESSAGE
        defaultDeliveryOrdersShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the deliveryOrdersList where message contains UPDATED_MESSAGE
        defaultDeliveryOrdersShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where message does not contain DEFAULT_MESSAGE
        defaultDeliveryOrdersShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the deliveryOrdersList where message does not contain UPDATED_MESSAGE
        defaultDeliveryOrdersShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where createdDate equals to DEFAULT_CREATED_DATE
        defaultDeliveryOrdersShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryOrdersList where createdDate equals to UPDATED_CREATED_DATE
        defaultDeliveryOrdersShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultDeliveryOrdersShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the deliveryOrdersList where createdDate equals to UPDATED_CREATED_DATE
        defaultDeliveryOrdersShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where createdDate is not null
        defaultDeliveryOrdersShouldBeFound("createdDate.specified=true");

        // Get all the deliveryOrdersList where createdDate is null
        defaultDeliveryOrdersShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultDeliveryOrdersShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryOrdersList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultDeliveryOrdersShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultDeliveryOrdersShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryOrdersList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultDeliveryOrdersShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where createdDate is less than DEFAULT_CREATED_DATE
        defaultDeliveryOrdersShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryOrdersList where createdDate is less than UPDATED_CREATED_DATE
        defaultDeliveryOrdersShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        // Get all the deliveryOrdersList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultDeliveryOrdersShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the deliveryOrdersList where createdDate is greater than SMALLER_CREATED_DATE
        defaultDeliveryOrdersShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllDeliveryOrdersByDeliveryTransactionsIsEqualToSomething() throws Exception {
        DeliveryTransactions deliveryTransactions;
        if (TestUtil.findAll(em, DeliveryTransactions.class).isEmpty()) {
            deliveryOrdersRepository.saveAndFlush(deliveryOrders);
            deliveryTransactions = DeliveryTransactionsResourceIT.createEntity(em);
        } else {
            deliveryTransactions = TestUtil.findAll(em, DeliveryTransactions.class).get(0);
        }
        em.persist(deliveryTransactions);
        em.flush();
        deliveryOrders.addDeliveryTransactions(deliveryTransactions);
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);
        Long deliveryTransactionsId = deliveryTransactions.getId();

        // Get all the deliveryOrdersList where deliveryTransactions equals to deliveryTransactionsId
        defaultDeliveryOrdersShouldBeFound("deliveryTransactionsId.equals=" + deliveryTransactionsId);

        // Get all the deliveryOrdersList where deliveryTransactions equals to (deliveryTransactionsId + 1)
        defaultDeliveryOrdersShouldNotBeFound("deliveryTransactionsId.equals=" + (deliveryTransactionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeliveryOrdersShouldBeFound(String filter) throws Exception {
        restDeliveryOrdersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryId").value(hasItem(DEFAULT_DELIVERY_ID.intValue())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].contactAddressId").value(hasItem(DEFAULT_CONTACT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactCartId").value(hasItem(DEFAULT_CONTACT_CART_ID.intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].menuItemId").value(hasItem(DEFAULT_MENU_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].menuItemName").value(hasItem(DEFAULT_MENU_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].menuItemCode").value(hasItem(DEFAULT_MENU_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restDeliveryOrdersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeliveryOrdersShouldNotBeFound(String filter) throws Exception {
        restDeliveryOrdersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeliveryOrdersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDeliveryOrders() throws Exception {
        // Get the deliveryOrders
        restDeliveryOrdersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeliveryOrders() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();

        // Update the deliveryOrders
        DeliveryOrders updatedDeliveryOrders = deliveryOrdersRepository.findById(deliveryOrders.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryOrders are not directly saved in db
        em.detach(updatedDeliveryOrders);
        updatedDeliveryOrders
            .deliveryId(UPDATED_DELIVERY_ID)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .contactId(UPDATED_CONTACT_ID)
            .contactName(UPDATED_CONTACT_NAME)
            .contactAddressId(UPDATED_CONTACT_ADDRESS_ID)
            .contactCartId(UPDATED_CONTACT_CART_ID)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .requestDate(UPDATED_REQUEST_DATE)
            .menuItemId(UPDATED_MENU_ITEM_ID)
            .menuItemName(UPDATED_MENU_ITEM_NAME)
            .menuItemCode(UPDATED_MENU_ITEM_CODE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .detail(UPDATED_DETAIL)
            .message(UPDATED_MESSAGE)
            .createdDate(UPDATED_CREATED_DATE);
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(updatedDeliveryOrders);

        restDeliveryOrdersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryOrdersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
        DeliveryOrders testDeliveryOrders = deliveryOrdersList.get(deliveryOrdersList.size() - 1);
        assertThat(testDeliveryOrders.getDeliveryId()).isEqualTo(UPDATED_DELIVERY_ID);
        assertThat(testDeliveryOrders.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testDeliveryOrders.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testDeliveryOrders.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testDeliveryOrders.getContactAddressId()).isEqualTo(UPDATED_CONTACT_ADDRESS_ID);
        assertThat(testDeliveryOrders.getContactCartId()).isEqualTo(UPDATED_CONTACT_CART_ID);
        assertThat(testDeliveryOrders.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testDeliveryOrders.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testDeliveryOrders.getMenuItemId()).isEqualTo(UPDATED_MENU_ITEM_ID);
        assertThat(testDeliveryOrders.getMenuItemName()).isEqualTo(UPDATED_MENU_ITEM_NAME);
        assertThat(testDeliveryOrders.getMenuItemCode()).isEqualTo(UPDATED_MENU_ITEM_CODE);
        assertThat(testDeliveryOrders.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testDeliveryOrders.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testDeliveryOrders.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDeliveryOrders.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDeliveryOrders() throws Exception {
        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();
        deliveryOrders.setId(count.incrementAndGet());

        // Create the DeliveryOrders
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(deliveryOrders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryOrdersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryOrdersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeliveryOrders() throws Exception {
        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();
        deliveryOrders.setId(count.incrementAndGet());

        // Create the DeliveryOrders
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(deliveryOrders);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryOrdersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeliveryOrders() throws Exception {
        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();
        deliveryOrders.setId(count.incrementAndGet());

        // Create the DeliveryOrders
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(deliveryOrders);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryOrdersMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeliveryOrdersWithPatch() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();

        // Update the deliveryOrders using partial update
        DeliveryOrders partialUpdatedDeliveryOrders = new DeliveryOrders();
        partialUpdatedDeliveryOrders.setId(deliveryOrders.getId());

        partialUpdatedDeliveryOrders
            .deliveryId(UPDATED_DELIVERY_ID)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .contactId(UPDATED_CONTACT_ID)
            .contactCartId(UPDATED_CONTACT_CART_ID)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .menuItemName(UPDATED_MENU_ITEM_NAME)
            .menuItemCode(UPDATED_MENU_ITEM_CODE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .message(UPDATED_MESSAGE);

        restDeliveryOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryOrders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryOrders))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
        DeliveryOrders testDeliveryOrders = deliveryOrdersList.get(deliveryOrdersList.size() - 1);
        assertThat(testDeliveryOrders.getDeliveryId()).isEqualTo(UPDATED_DELIVERY_ID);
        assertThat(testDeliveryOrders.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testDeliveryOrders.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testDeliveryOrders.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testDeliveryOrders.getContactAddressId()).isEqualTo(DEFAULT_CONTACT_ADDRESS_ID);
        assertThat(testDeliveryOrders.getContactCartId()).isEqualTo(UPDATED_CONTACT_CART_ID);
        assertThat(testDeliveryOrders.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testDeliveryOrders.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testDeliveryOrders.getMenuItemId()).isEqualTo(DEFAULT_MENU_ITEM_ID);
        assertThat(testDeliveryOrders.getMenuItemName()).isEqualTo(UPDATED_MENU_ITEM_NAME);
        assertThat(testDeliveryOrders.getMenuItemCode()).isEqualTo(UPDATED_MENU_ITEM_CODE);
        assertThat(testDeliveryOrders.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testDeliveryOrders.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testDeliveryOrders.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDeliveryOrders.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDeliveryOrdersWithPatch() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();

        // Update the deliveryOrders using partial update
        DeliveryOrders partialUpdatedDeliveryOrders = new DeliveryOrders();
        partialUpdatedDeliveryOrders.setId(deliveryOrders.getId());

        partialUpdatedDeliveryOrders
            .deliveryId(UPDATED_DELIVERY_ID)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .contactId(UPDATED_CONTACT_ID)
            .contactName(UPDATED_CONTACT_NAME)
            .contactAddressId(UPDATED_CONTACT_ADDRESS_ID)
            .contactCartId(UPDATED_CONTACT_CART_ID)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .requestDate(UPDATED_REQUEST_DATE)
            .menuItemId(UPDATED_MENU_ITEM_ID)
            .menuItemName(UPDATED_MENU_ITEM_NAME)
            .menuItemCode(UPDATED_MENU_ITEM_CODE)
            .lineNumber(UPDATED_LINE_NUMBER)
            .detail(UPDATED_DETAIL)
            .message(UPDATED_MESSAGE)
            .createdDate(UPDATED_CREATED_DATE);

        restDeliveryOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryOrders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryOrders))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
        DeliveryOrders testDeliveryOrders = deliveryOrdersList.get(deliveryOrdersList.size() - 1);
        assertThat(testDeliveryOrders.getDeliveryId()).isEqualTo(UPDATED_DELIVERY_ID);
        assertThat(testDeliveryOrders.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testDeliveryOrders.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testDeliveryOrders.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testDeliveryOrders.getContactAddressId()).isEqualTo(UPDATED_CONTACT_ADDRESS_ID);
        assertThat(testDeliveryOrders.getContactCartId()).isEqualTo(UPDATED_CONTACT_CART_ID);
        assertThat(testDeliveryOrders.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testDeliveryOrders.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testDeliveryOrders.getMenuItemId()).isEqualTo(UPDATED_MENU_ITEM_ID);
        assertThat(testDeliveryOrders.getMenuItemName()).isEqualTo(UPDATED_MENU_ITEM_NAME);
        assertThat(testDeliveryOrders.getMenuItemCode()).isEqualTo(UPDATED_MENU_ITEM_CODE);
        assertThat(testDeliveryOrders.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testDeliveryOrders.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testDeliveryOrders.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDeliveryOrders.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDeliveryOrders() throws Exception {
        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();
        deliveryOrders.setId(count.incrementAndGet());

        // Create the DeliveryOrders
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(deliveryOrders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deliveryOrdersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeliveryOrders() throws Exception {
        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();
        deliveryOrders.setId(count.incrementAndGet());

        // Create the DeliveryOrders
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(deliveryOrders);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeliveryOrders() throws Exception {
        int databaseSizeBeforeUpdate = deliveryOrdersRepository.findAll().size();
        deliveryOrders.setId(count.incrementAndGet());

        // Create the DeliveryOrders
        DeliveryOrdersDTO deliveryOrdersDTO = deliveryOrdersMapper.toDto(deliveryOrders);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryOrdersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryOrders in the database
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeliveryOrders() throws Exception {
        // Initialize the database
        deliveryOrdersRepository.saveAndFlush(deliveryOrders);

        int databaseSizeBeforeDelete = deliveryOrdersRepository.findAll().size();

        // Delete the deliveryOrders
        restDeliveryOrdersMockMvc
            .perform(delete(ENTITY_API_URL_ID, deliveryOrders.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryOrders> deliveryOrdersList = deliveryOrdersRepository.findAll();
        assertThat(deliveryOrdersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
