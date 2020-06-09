package ru.ivglv.PriceListObserver.Configuration.Helper;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;

import java.io.IOException;

import static org.testng.Assert.*;

public class MailConfigReaderTest {

    @Test(enabled = false)
    public void testRead() throws IOException {
        String path = "imap";
        MailConfigReader mailConfigReader = new MailConfigReader(path);

        MailConfig expected = new MailConfig(
                "imap.yandex.ru"
                , "993"
                , "true"
                , "test@yandex.ru"
                , "test"
                , "INBOX"
                , ".csv"
        );

        MailConfig actual = mailConfigReader.read();

        Assert.assertEquals(actual, expected);
    }

}