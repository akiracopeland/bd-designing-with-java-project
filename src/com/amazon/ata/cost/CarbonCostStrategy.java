package com.amazon.ata.cost;

import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;

public class CarbonCostStrategy implements CostStrategy {


    public CarbonCostStrategy() {}

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal carbonCost = packaging.getMass().multiply(packaging.getSustainabilityIndex());

        return new ShipmentCost(shipmentOption, carbonCost);
    }
}
