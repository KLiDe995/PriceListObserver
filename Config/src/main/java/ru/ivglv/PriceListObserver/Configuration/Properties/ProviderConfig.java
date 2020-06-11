package ru.ivglv.PriceListObserver.Configuration.Properties;

import ru.ivglv.PriceListObserver.Configuration.Port.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ProviderConfig implements Config {
    private String vendorColumnName;
    private String numberColumnName;
    private String descrColumnName;
    private String priceColumnName;
    private String countColumnName;

    public ProviderConfig(
            String vendorColumnName
            , String numberColumnName
            , String descrColumnName
            , String priceColumnName
            , String countColumnName) {
        this.vendorColumnName = vendorColumnName;
        this.numberColumnName = numberColumnName;
        this.descrColumnName = descrColumnName;
        this.priceColumnName = priceColumnName;
        this.countColumnName = countColumnName;
    }

    public String getVendorColumnName() {
        return vendorColumnName;
    }

    public String getNumberColumnName() {
        return numberColumnName;
    }

    public String getDescrColumnName() {
        return descrColumnName;
    }

    public String getPriceColumnName() {
        return priceColumnName;
    }

    public String getCountColumnName() {
        return countColumnName;
    }

    public List<String> getAllColumnNames() {
        List<String> result = new ArrayList<>();
        result.add(getVendorColumnName());
        result.add(getNumberColumnName());
        result.add(getDescrColumnName());
        result.add(getPriceColumnName());
        result.add(getCountColumnName());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProviderConfig)) return false;
        ProviderConfig that = (ProviderConfig) o;
        return vendorColumnName.equals(that.vendorColumnName) &&
                numberColumnName.equals(that.numberColumnName) &&
                descrColumnName.equals(that.descrColumnName) &&
                priceColumnName.equals(that.priceColumnName) &&
                countColumnName.equals(that.countColumnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorColumnName, numberColumnName, descrColumnName, priceColumnName, countColumnName);
    }

    @Override
    public String toString() {
        return "ProviderConfig{" +
                "vendorColumnName='" + vendorColumnName + '\'' +
                ", numberColumnName='" + numberColumnName + '\'' +
                ", descrColumnName='" + descrColumnName + '\'' +
                ", priceColumnName='" + priceColumnName + '\'' +
                ", countColumnName='" + countColumnName + '\'' +
                '}';
    }
}
