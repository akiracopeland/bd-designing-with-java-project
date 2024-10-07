package com.amazon.ata.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolyBagTest {
    private Material packagingMaterial = Material.LAMINATED_PLASTIC;
    private BigDecimal packagingVolume = BigDecimal.valueOf(1000);


    private Packaging packaging;

    @BeforeEach
    public void setUp() {
        packaging = new PolyBag(packagingMaterial, packagingVolume);
    }

    @Test
    public void canFitItem_itemSameSizeAsBox_doesNotFit() {
        // GIVEN
        BigDecimal valueOfTen = BigDecimal.valueOf(10);
        Item item = Item.builder()
                .withLength(valueOfTen)
                .withWidth(valueOfTen)
                .withHeight(valueOfTen)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item the same size as the package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSmallerThanBox_doesFit() {
        // GIVEN
        BigDecimal valueOfTen = BigDecimal.valueOf(10);
        Item item = Item.builder()
                .withLength(valueOfTen)
                .withWidth(valueOfTen)
                .withHeight(valueOfTen.subtract(BigDecimal.ONE))
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertTrue(canFit, "Item smaller than the package should fit in the package.");
    }

    @Test
    public void getMass_calculatesMass_returnsCorrectMass() {
        // GIVEN
        packaging = new PolyBag(packagingMaterial, packagingVolume);

        // WHEN
        BigDecimal mass = packaging.getMass();

        // THEN
        assertEquals(BigDecimal.valueOf(19.0), mass,
                "Item smaller than the box should fit in the package.");
    }

    @Test
    public void equals_sameObject_isTrue() {
        // GIVEN
        Packaging packaging = new PolyBag(packagingMaterial, packagingVolume);

        // WHEN
        boolean result = packaging.equals(packaging);

        // THEN
        assertTrue(result, "An object should be equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Packaging packaging = new PolyBag(packagingMaterial, packagingVolume);

        // WHEN
        boolean isEqual = packaging.equals(null);

        // THEN
        assertFalse(isEqual, "A Packaging should not be equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Packaging packaging = new PolyBag(packagingMaterial, packagingVolume);
        Object other = "String type!";

        // WHEN
        boolean isEqual = packaging.equals(other);

        // THEN
        assertFalse(isEqual, "A Packaging should not be equal to an object of a different type.");
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        // GIVEN
        Packaging packaging = new PolyBag(packagingMaterial, packagingVolume);
        Object other = new PolyBag(packagingMaterial, packagingVolume);

        // WHEN
        boolean isEqual = packaging.equals(other);

        // THEN
        assertTrue(isEqual, "Packaging with the same attributes should be equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        Packaging packaging = new PolyBag(packagingMaterial, packagingVolume);
        Packaging other = new PolyBag(packagingMaterial, packagingVolume);

        // WHEN + THEN
        assertEquals(packaging.hashCode(), other.hashCode(), "Equal objects should have equal hashCodes");
    }
}
