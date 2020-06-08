package ru.ivglv.PriceListObserver.Model.Entity;

import java.util.Objects;

public abstract class AbstractCarPart {
    protected String vendor;

    protected String number;

    protected String description;

    public AbstractCarPart(String vendor, String number)
    {
        this.vendor = vendor;
        this.number = number;
    }

    public String getVendor() {
        return vendor;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCarPart)) return false;
        AbstractCarPart that = (AbstractCarPart) o;
        return vendor.equals(that.vendor) &&
                number.equals(that.number) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendor, number, description);
    }
}
