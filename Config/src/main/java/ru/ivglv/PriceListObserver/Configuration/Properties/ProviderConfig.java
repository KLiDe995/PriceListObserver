package ru.ivglv.PriceListObserver.Configuration.Properties;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Configuration.Port.Config;

public final class ProviderConfig implements Config {
    @NotNull
    private String vendorColumnName;
    @NotNull
    private String numberColumnName;
    @NotNull
    private String descrColumnName;
    @NotNull
    private String priceColumnName;
    @NotNull
    private String countColumnName;
    @NotNull
    private Integer maxDescriptionLenght;

    public ProviderConfig(
            @NotNull String vendorColumnName
            , @NotNull String numberColumnName
            , @NotNull String descrColumnName
            , @NotNull String priceColumnName
            , @NotNull String countColumnName
            , @NotNull Integer maxDescriptionLenght) {
        this.vendorColumnName = vendorColumnName;
        this.numberColumnName = numberColumnName;
        this.descrColumnName = descrColumnName;
        this.priceColumnName = priceColumnName;
        this.countColumnName = countColumnName;
        this.maxDescriptionLenght = maxDescriptionLenght;
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

    public Integer getMaxDescriptionLenght() {
        return maxDescriptionLenght;
    }
}
