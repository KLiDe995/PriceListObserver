package ru.ivglv.PriceListObserver.Model.Entity;

public final class CarPart extends AbstractCarPart{

    private String searchVendor;

    private String searchNumber;

    private float price;

    private int count;

    private CarPart(String vendor, String number)
    {
        super(vendor, number);
    }

    public String getSearchVendor() {
        return searchVendor;
    }

    public String getSearchNumber() {
        return searchNumber;
    }

    public float getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public static class Builder
    {
        private CarPart newCarPart;

        public Builder(String vendor, String number) {
            newCarPart = new CarPart(vendor, number);
        }

        public Builder searchVendor(String searchVendor) {
            newCarPart.searchVendor = searchVendor;
            return this;
        }

        public Builder searchNumber(String searchNumber) {
            newCarPart.searchNumber = searchNumber;
            return this;
        }

        public Builder description(String description) {
            newCarPart.description = description;
            return this;
        }

        public Builder price(float price) {
            newCarPart.price = price;
            return this;
        }

        public Builder count(int count) {
            newCarPart.count = count;
            return this;
        }

        public CarPart build()
        {
            return newCarPart;
        }
    }
}
