package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.PrepareShipmentRequest;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.PrepareShipmentResponse;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.service.ShipmentService;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FulfillmentCenter;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Item;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PrepareShipmentActivityTest {

    private PrepareShipmentRequest request = PrepareShipmentRequest.builder()
        .withFcCode("fcCode")
        .withItemAsin("itemAsin")
        .withItemDescription("description")
        .withItemLength("10.0")
        .withItemWidth("10.0")
        .withItemHeight("10.0")
        .build();

    @Mock
    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void handleRequest_noAvailableShipmentOption_returnsNull() throws Exception {
        // GIVEN
        PrepareShipmentActivity activity = new PrepareShipmentActivity(shipmentService);
        when(shipmentService.findShipmentOption(any(Item.class), any(FulfillmentCenter.class))).thenReturn(null);

        // WHEN
        String response = activity.handleRequest(request, null);

        // THEN
        assertNull(response);
    }

    @Test
    public void handleRequest_availableShipmentOption_returnsNonEmptyResponse() throws Exception {
        // GIVEN
        // PrepareShipmentActivity activity = new PrepareShipmentActivity(shipmentService, dataConverter);
        PrepareShipmentActivity activity = new PrepareShipmentActivity(shipmentService);
        when(shipmentService.findShipmentOption(any(Item.class), any(FulfillmentCenter.class)))
            .thenReturn(ShipmentOption.builder().build());

        // WHEN
        // PrepareShipmentResponse response = activity.handleRequest(request);
        String response = activity.handleRequest(request, null);


        // THEN
        //assertNotNull(response.getAttributes());
        assertNotNull(response);
    }
}
