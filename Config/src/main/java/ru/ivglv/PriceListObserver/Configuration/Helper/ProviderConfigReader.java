package ru.ivglv.PriceListObserver.Configuration.Helper;

import ru.ivglv.PriceListObserver.Configuration.Port.ConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ProviderConfigReader implements ConfigReader {
    private String path;

    public ProviderConfigReader(String path) {
        this.path = path;
    }

    @Override
    public ProviderConfig read() throws IOException
    {
        Properties prop = new Properties();
        prop.load(new FileInputStream(path));
        return new ProviderConfig(
                prop.getProperty("vendorColumnName")
                , prop.getProperty("numberColumnName")
                , prop.getProperty("descrColumnName")
                , prop.getProperty("priceColumnName")
                , prop.getProperty("countColumnName")
                , Integer.parseInt(prop.getProperty("maxDescriptionLenght", "512"))
        );





    }
}
