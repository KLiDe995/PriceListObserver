package ru.ivglv.PriceListObserver.Model.Entity;

public final class RawCarPart extends AbstractCarPart {

    private String price;

    private String count;

    private RawCarPart(String vendor, String number)
    {
        super(vendor, number);
    }

    public String getPrice() {
        return price;
    }

    public String getCount() {
        return count;
    }

    public static class Builder {
        private RawCarPart newRawCarPart;

        public Builder(String vendor, String number)
        {
            newRawCarPart = new RawCarPart(vendor, number);
        }

        public Builder price(String price)
        {
            newRawCarPart.price = price;
            return this;
        }

        public Builder count(String count)
        {
            newRawCarPart.count = count;
            return this;
        }

        public RawCarPart build()
        {
            return newRawCarPart;
        }
    }
}
