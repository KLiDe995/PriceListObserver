package ru.ivglv.PriceListObserver.Configuration.Helper;

import ru.ivglv.PriceListObserver.Configuration.Port.ConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.ResourceBundleProvider;

public final class DbConfigReader extends ConfigReader {
    private final Integer DEFAULT_DESCR_LENGHT = 512;

    public DbConfigReader(String bundleName) {
        super(bundleName);
    }

    @Override
    public DbConfig read() throws IOException
    {
        ResourceBundle prop = ResourceBundle.getBundle(bundleName);

        return new DbConfig(
            prop.getString("db_url")
            , prop.getString("db_user")
            , prop.getString("db_pass")
            , prop.getString("db_table_name")
            , parseInt(prop.getString("db_descr_max_size"))
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
