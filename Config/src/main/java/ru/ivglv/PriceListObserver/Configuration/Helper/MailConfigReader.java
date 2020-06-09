package ru.ivglv.PriceListObserver.Configuration.Helper;

import ru.ivglv.PriceListObserver.Configuration.Port.ConfigReader;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public final class MailConfigReader extends ConfigReader {

    public MailConfigReader(String bundleName) {
        super(bundleName);
    }

    @Override
    public MailConfig read() throws IOException
    {
        ResourceBundle prop = ResourceBundle.getBundle(bundleName,CsControl.Cp1251);

        return new MailConfig(
                prop.getString("imap_auth_server")
                , prop.getString("imap_port")
                , prop.getString("imap_ssl_enable")
                , prop.getString("imap_auth_email")
                , prop.getString("imap_auth_password")
                , prop.getString("imap_folder")
                , prop.getString("imap_file_ext")
        );
    }
}
