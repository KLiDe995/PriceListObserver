package ru.ivglv.PriceListObserver.Configuration.Helper;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;

import java.io.IOException;

import static org.testng.Assert.*;

public class DbConfigReaderTest {

    @Test
    public void testRead() throws IOException {
        String path = "dbconfig";
        DbConfigReader dbConfigReader = new DbConfigReader(path);

        DbConfig expected = new DbConfig(
                "jdbc:postgresql://127.0.0.1:5432/carparts"
                , "appUser"
                , "123456789"
                , "PriceItems"
        );

        DbConfig actual = dbConfigReader.read();

        Assert.assertEquals(expected, actual);
    }
}