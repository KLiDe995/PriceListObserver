package ru.ivglv.PriceListObserver.Configuration;

import ru.ivglv.PriceListObserver.Configuration.Helper.DbConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Helper.MailConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Helper.ProviderConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import java.io.IOException;

public final class Configurator {
    private DbConfig dbConfig;
    private ProviderConfig providerConfig;
    private MailConfig mailConfig;

    private DbConfigReader dbConfigReader;
    private ProviderConfigReader providerConfigReader;
    private MailConfigReader mailConfigReader;

    public Configurator(String dbConfigName, String providerConfigName, String imapConfigName) {
        dbConfigReader = new DbConfigReader(dbConfigName);
        providerConfigReader = new ProviderConfigReader(providerConfigName);
        mailConfigReader = new MailConfigReader(imapConfigName);
    }

    public void readConfiguration() throws IOException
    {
        dbConfig = dbConfigReader.read();
        providerConfig = providerConfigReader.read();
        mailConfig = mailConfigReader.read();
    }

    public DbConfig getDbConfig()
    {
        return dbConfig;
    }

    public ProviderConfig getProviderConfig()
    {
        return providerConfig;
    }

    public MailConfig getMailConfig() {
        return mailConfig;
    }
}
