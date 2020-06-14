package ru.ivglv.PriceListObserver.Configuration.Helper;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ivglv.PriceListObserver.Configuration.Port.Config;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import java.io.IOException;
import java.util.HashMap;

import static org.testng.Assert.*;

public class ProviderConfigReaderTest {

    @Test
    public void testRead() throws IOException {
        String path = "providers";
        ProviderConfigReader providerConfigReader = new ProviderConfigReader(path);

        HashMap<String, Config> expected = new HashMap<>();
        expected.put("tj-ivan09@yandex.ru", new ProviderConfig(
                "�����"
                , "���������� �����"
                , "��������"
                , "����, ���."
                , "�������"
        ));
        expected.put("test@example.com", new ProviderConfig(
                "����1"
                , "����2"
                , "����3"
                , "����4"
                , "����5"
        ));

        HashMap<String, Config> actual = providerConfigReader.read();

        Assert.assertEquals(actual, expected);
    }
}