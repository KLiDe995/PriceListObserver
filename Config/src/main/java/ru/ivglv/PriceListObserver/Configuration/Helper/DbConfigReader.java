package ru.ivglv.PriceListObserver.Configuration.Helper;

import ru.ivglv.PriceListObserver.Configuration.Port.ConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;

import java.io.IOException;
import java.util.ResourceBundle;

public final class DbConfigReader extends ConfigReader {

    public DbConfigReader(String bundleName) {
        super(bundleName);
    }

    @Override
    public DbConfig read() throws IOException
    {
        ResourceBundle prop = ResourceBundle.getBundle(bundleName,CsControl.Cp1251);

        return new DbConfig(
            prop.getString("db_url")
            , prop.getString("db_user")
            , prop.getString("db_pass")
            , prop.getString("db_table_name")
        );
    }
}
