package ru.ivglv.PriceListObserver.Configuration.Helper;

import ru.ivglv.PriceListObserver.Configuration.Port.Config;
import ru.ivglv.PriceListObserver.Configuration.Port.ConfigListReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

public final class ProviderConfigReader extends ConfigListReader {
    private final String PROVIDER_FILE_PREFIX = "provider-";
    private final String PROVIDER_SUFFIX_DELIMETER = "_";

    public ProviderConfigReader(String providerListBundle) {
        super(providerListBundle);
    }

    @Override
    public HashMap<String, Config> read() throws IOException
    {
        HashMap<String, Config> result = new HashMap<>();
        ResourceBundle propList = ResourceBundle.getBundle(bundleName,CsControl.Cp1251);
        propList.getKeys().asIterator().forEachRemaining(key -> {
            result.put(propList.getString(key)
                    , readProviderConfig(PROVIDER_FILE_PREFIX + key.split(PROVIDER_SUFFIX_DELIMETER)[0]));
        });
        return result;
    }

    private ProviderConfig readProviderConfig(String fileName)
    {
        ResourceBundle prop = ResourceBundle.getBundle(fileName,CsControl.Cp1251);
        return new ProviderConfig(
                prop.getString("vendor_column_name")
                , prop.getString("number_column_name")
                , prop.getString("descr_column_name")
                , prop.getString("price_column_name")
                , prop.getString("count_column_name")
        );
    }
}
