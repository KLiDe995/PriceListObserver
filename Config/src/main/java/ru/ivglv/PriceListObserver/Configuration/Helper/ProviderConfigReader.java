package ru.ivglv.PriceListObserver.Configuration.Helper;

import ru.ivglv.PriceListObserver.Configuration.Port.ConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import java.io.IOException;
import java.util.ResourceBundle;

public final class ProviderConfigReader extends ConfigReader {
    private final Integer DEFAULT_DESCR_LENGHT = 512;

    public ProviderConfigReader(String bundleName) {
        super(bundleName);
    }

    @Override
    public ProviderConfig read() throws IOException
    {
        ResourceBundle prop = ResourceBundle.getBundle(bundleName,CsControl.Cp1251);

        return new ProviderConfig(
                prop.getString("vendor_column_name")
                , prop.getString("number_column_name")
                , prop.getString("descr_column_name")
                , prop.getString("price_column_name")
                , prop.getString("count_column_name")
                , parseInt(prop.getString("max_description_lenght"))
        );
    }

    private Integer parseInt(String input)
    {
        try {
            return Integer.parseInt(input);
        }
        catch(NumberFormatException ex) {
            return DEFAULT_DESCR_LENGHT;
        }
    }
}
