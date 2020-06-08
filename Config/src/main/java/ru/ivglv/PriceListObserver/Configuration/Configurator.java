package ru.ivglv.PriceListObserver.Configuration;

import ru.ivglv.PriceListObserver.Configuration.Helper.DbConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Helper.ProviderConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import java.io.IOException;

public final class Configurator {
    private DbConfig dbConfig;
    private ProviderConfig providerConfig;

    private DbConfigReader dbConfigReader;
    private ProviderConfigReader providerConfigReader;

    public Configurator(String dbConfigPath, String providerConfigPath, String imapConfigPath) {
        dbConfigReader = new DbConfigReader(dbConfigPath);
        providerConfigReader = new ProviderConfigReader(providerConfigPath);
    }

    public void readConfiguration() throws IOException
    {
        dbConfigReader.read();
        providerConfigReader.read();
    }

    public DbConfig getDbConfig()
    {
        return dbConfig;
    }

    public ProviderConfig getProviderConfig()
    {
        return providerConfig;
    }
}
