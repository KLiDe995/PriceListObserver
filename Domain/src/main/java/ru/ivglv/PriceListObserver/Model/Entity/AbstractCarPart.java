package ru.ivglv.PriceListObserver.Model.Entity;

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
}
