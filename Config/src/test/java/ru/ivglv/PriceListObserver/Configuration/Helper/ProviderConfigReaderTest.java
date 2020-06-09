package ru.ivglv.PriceListObserver.Configuration.Helper;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import java.io.IOException;

import static org.testng.Assert.*;

public class ProviderConfigReaderTest {

    @Test
    public void testRead() throws IOException {
        String path = "provider";
        ProviderConfigReader providerConfigReader = new ProviderConfigReader(path);

        ProviderConfig expected = new ProviderConfig(
                "Бренд"
                , "Каталожный номер"
                , "Описание"
                , "Цена"
                , "Наличие"
                , 512
        );

        ProviderConfig actual = providerConfigReader.read();

        Assert.assertEquals(actual, expected);
    }
}