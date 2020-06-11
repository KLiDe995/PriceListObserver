package ru.ivglv.PriceListObserver.Model.Entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarPart carPart = (CarPart) o;
        return super.equals(carPart) &&
                Float.compare(carPart.price, price) == 0 &&
                count == carPart.count &&
                searchVendor.equals(carPart.searchVendor) &&
                searchNumber.equals(carPart.searchNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchVendor, searchNumber, price, count);
    }

    @Override
    public String toString() {
        return "CarPart{" +
                "vendor='" + vendor + '\'' +
                ", number='" + number + '\'' +
                ", searchVendor='" + searchVendor + '\'' +
                ", searchNumber='" + searchNumber + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", description='" + description + '\'' +
                '}';
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
