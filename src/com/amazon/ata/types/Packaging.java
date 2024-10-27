package com.amazon.ata.types;

import java.math.BigDecimal;

/**
 * Represents a packaging option.
 *
 * This packaging supports standard boxes, having a length, width, and height.
 * Items can fit in the packaging so long as their dimensions are all smaller than
 * the packaging's dimensions.
 */
public class Packaging {

    private Material material;
    /**
     * The material this packaging is made of.
     */
    Packaging(Material material) {
        this.material = material;
    }

    /**
     * Returns whether the given item will fit in this packaging.
     *
     * @param item the item to test fit for
     * @return whether the item will fit in this packaging
     */
    //DO NOT CALL this superclass method
    public boolean canFitItem(Item item) {
        return false;
    }

    /**
     * Returns the mass of the packaging in grams. The packaging weighs 1 gram per square centimeter.
     * @return the mass of the packaging
     */
    //DO NOT CALL this superclass method
    public BigDecimal getMass() {
        return BigDecimal.valueOf(0);
    }

    public Material getMaterial() {
        return material;
    }

    public BigDecimal getSustainabilityIndex() {
        return BigDecimal.valueOf(0);
    }

}
