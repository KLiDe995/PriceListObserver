package ru.ivglv.PriceListObserver.Configuration;

import ru.ivglv.PriceListObserver.Configuration.Helper.DbConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Helper.MailConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Helper.ProviderConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Port.Config;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import java.io.IOException;
import java.util.HashMap;

public final class Configurator {
    private DbConfig dbConfig;
    private HashMap<String, ProviderConfig> providerConfigs;
    private MailConfig mailConfig;

    private DbConfigReader dbConfigReader;
    private ProviderConfigReader providerConfigReader;
    private MailConfigReader mailConfigReader;

    public Configurator(String dbConfigName, String providerConfigName, String imapConfigName) {
        dbConfigReader = new DbConfigReader(dbConfigName);
        providerConfigReader = new ProviderConfigReader(providerConfigName);
        mailConfigReader = new MailConfigReader(imapConfigName);
    }

    public Configurator readConfiguration() throws IOException
    {
        dbConfig = dbConfigReader.read();
        providerConfigs = convertHashTypeToProvider(providerConfigReader.read());
        mailConfig = mailConfigReader.read();
        return this;
    }

    private HashMap<String, ProviderConfig> convertHashTypeToProvider(HashMap<String, Config> input)
    {
        HashMap<String, ProviderConfig> result = new HashMap<>();
        for (String key : input.keySet()) {
            result.put(key, (ProviderConfig) input.get(key));
        }
        return result;
    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public HashMap<String, ProviderConfig> getProviderConfigs()
    {
        return providerConfigs;
    }

    public MailConfig getMailConfig() {
        return mailConfig;
    }
}
