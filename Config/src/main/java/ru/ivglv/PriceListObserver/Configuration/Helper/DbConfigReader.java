package ru.ivglv.PriceListObserver.Configuration.Helper;

import ru.ivglv.PriceListObserver.Configuration.Port.ConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class DbConfigReader implements ConfigReader {
    private String path;

    public DbConfigReader(String path) {
        this.path = path;
    }

    @Override
    public DbConfig read() throws IOException
    {
        Properties prop = new Properties();
        prop.load(new FileInputStream(path));
        return new DbConfig(
            prop.getProperty("DB_URL")
            , prop.getProperty("DB_USER")
            , prop.getProperty("PASS")
            , prop.getProperty("TABLE_NAME")
        );
    }
}
