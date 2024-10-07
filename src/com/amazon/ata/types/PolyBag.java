package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {

    private BigDecimal volume;

    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }

    public BigDecimal getVolume() {
        return this.volume;
    }

    @Override
    public boolean canFitItem(Item item) {

        BigDecimal itemVolume = item.getWidth().multiply(item.getHeight()).multiply(item.getLength());

        return this.volume.compareTo(itemVolume) > 0;
    }

    @Override
    public BigDecimal getMass() {

        double mass = Math.ceil(Math.sqrt(this.volume.doubleValue()) * 0.6);

        return BigDecimal.valueOf(mass);
    }

    @Override
    public boolean equals(Object o) {
        // Can't be equal to null
        if (o == null) {
            return false;
        }

        // Referentially equal
        if (this == o) {
            return true;
        }

        // Check if it's a different type
        if (getClass() != o.getClass()) {
            return false;
        }

        PolyBag polyBag = (PolyBag) o;
        return getMaterial() == polyBag.getMaterial() && getVolume().equals(polyBag.getVolume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaterial(), getVolume());
    }
}