package com.amazon.ata.cost;

import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;

public class WeightedCostStrategy implements CostStrategy{

    private CostStrategy carbonCost = new CarbonCostStrategy();
    private CostStrategy monetaryCost = new MonetaryCostStrategy();

    public WeightedCostStrategy(MonetaryCostStrategy monetaryCostStrategy, CarbonCostStrategy carbonCostStrategy) {
        this.carbonCost = carbonCostStrategy;
        this.monetaryCost = monetaryCostStrategy;
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        ShipmentCost carbonShipmentCost = carbonCost.getCost(shipmentOption);
        ShipmentCost monetaryShipmentCost = monetaryCost.getCost(shipmentOption);

        BigDecimal blendedCost = carbonShipmentCost.getCost().multiply(BigDecimal.valueOf(0.2))
                .add(monetaryShipmentCost.getCost().multiply(BigDecimal.valueOf(0.8)));

        return new ShipmentCost(shipmentOption, blendedCost);
    }
}
