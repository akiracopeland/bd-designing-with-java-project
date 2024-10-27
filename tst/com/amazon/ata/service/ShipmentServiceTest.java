package com.amazon.ata.service;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ShipmentServiceTest {

    private Item smallItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1))
            .withWidth(BigDecimal.valueOf(1))
            .withLength(BigDecimal.valueOf(1))
            .withAsin("abcde")
            .build();

    private Item largeItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1000))
            .withWidth(BigDecimal.valueOf(1000))
            .withLength(BigDecimal.valueOf(1000))
            .withAsin("12345")
            .build();

    private FulfillmentCenter existentFC = new FulfillmentCenter("ABE2");
    private FulfillmentCenter nonExistentFC = new FulfillmentCenter("NonExistentFC");

    @Mock
    private PackagingDAO packagingDAO;

    @Mock
    private CostStrategy costStrategy;

    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {

        initMocks(this);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        List<ShipmentOption> result = new ArrayList<>();

        Packaging box = new Box(Material.CORRUGATE, BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(20));

        ShipmentOption expectedOption = ShipmentOption.builder()
                .withItem(smallItem)
                .withFulfillmentCenter(existentFC)
                .withPackaging(box)
                .build();

        result.add(expectedOption);

        ShipmentCost shipmentCost = new ShipmentCost(expectedOption, BigDecimal.valueOf(0.8));

        when(packagingDAO.findShipmentOptions(smallItem, existentFC)).thenReturn(result);

        when(costStrategy.getCost(expectedOption)).thenReturn(shipmentCost);

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN

        Packaging box = new Box(Material.CORRUGATE, BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(20));

        ShipmentOption expectedOption = ShipmentOption.builder()
                .withItem(largeItem)
                .withFulfillmentCenter(existentFC)
                .withPackaging(box)
                .build();

        ShipmentCost shipmentCost = new ShipmentCost(expectedOption, BigDecimal.valueOf(0.8));

        when(packagingDAO.findShipmentOptions(largeItem, existentFC)).thenThrow(NoPackagingFitsItemException.class);

        when(costStrategy.getCost(expectedOption)).thenReturn(shipmentCost);

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        Packaging box = new Box(Material.CORRUGATE, BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(20));

        ShipmentOption expectedOption = ShipmentOption.builder()
                .withItem(smallItem)
                .withFulfillmentCenter(nonExistentFC)
                .withPackaging(box)
                .build();

        ShipmentCost shipmentCost = new ShipmentCost(expectedOption, BigDecimal.valueOf(0.8));

        when(packagingDAO.findShipmentOptions(smallItem, nonExistentFC)).thenThrow(UnknownFulfillmentCenterException.class);

        when(costStrategy.getCost(expectedOption)).thenReturn(shipmentCost);

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertNull(shipmentOption);
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCannotFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        Packaging box = new Box(Material.CORRUGATE, BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(20));

        ShipmentOption expectedOption = ShipmentOption.builder()
                .withItem(largeItem)
                .withFulfillmentCenter(nonExistentFC)
                .withPackaging(box)
                .build();

        ShipmentCost shipmentCost = new ShipmentCost(expectedOption, BigDecimal.valueOf(0.8));

        when(packagingDAO.findShipmentOptions(largeItem, nonExistentFC)).thenThrow(UnknownFulfillmentCenterException.class);

        when(costStrategy.getCost(expectedOption)).thenReturn(shipmentCost);

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertNull(shipmentOption);
    }
}