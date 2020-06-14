package ru.ivglv.PriceListObserver.Framework.PostgreSQL;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;

import java.util.List;

public class PostgreSqlHelperTest {
    private PostgreSqlHelper postgreSqlHelper;
    CarPart testCarPart1;
    CarPart testCarPart2;
    CarPart testCarPart3;

    @BeforeClass
    public void setUp() throws ClassNotFoundException {
        postgreSqlHelper = new PostgreSqlHelper(new DbConfig(
                "jdbc:postgresql://127.0.0.1:5432/carparts"
                , "appUser"
                , "123456789"
                , "PriceItems"
                , 512));
        testCarPart1 = new CarPart.Builder("Test1Vendor", "Test1Number")
                .searchVendor("Test1searchVendor")
                .searchNumber("Test1searchNumber")
                .description("Test1Descr")
                .price(111.11f)
                .count(1)
                .build();
        testCarPart2 = new CarPart.Builder("Test2Vendor", "Test2Number")
                .searchVendor("Test2searchVendor")
                .searchNumber("Test2searchNumber")
                .description("Test2Descr")
                .price(2222.22f)
                .count(2)
                .build();
        testCarPart3 = new CarPart.Builder("Test3Vendor", "Test3Number")
                .searchVendor("Test3searchVendor")
                .searchNumber("Test3searchNumber")
                .description("Test3Descr")
                .price(333333333.333f)
                .count(3333)
                .build();
    }

    @AfterClass(enabled = false)
    public void tearDown() {
        postgreSqlHelper.disconnect();
    }

    @Test(enabled = true)
    public void testCheckClass() {
        try {
            postgreSqlHelper.checkClass();
        }
        catch (Exception ex)
        {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test(enabled = false)
    public void testConnect() {
        postgreSqlHelper.connect();
        Assert.assertTrue(postgreSqlHelper.connected());
    }

    @Test(enabled = false)
    public void testInsert() {
        postgreSqlHelper.connect();
        postgreSqlHelper.insert(testCarPart1);
        CarPart actual = postgreSqlHelper.selectByIndex(testCarPart1.getSearchVendor(), testCarPart1.getSearchNumber()).orElseThrow();
        postgreSqlHelper.deleteAll();
        postgreSqlHelper.disconnect();

        Assert.assertTrue(testCarPart1.equals(actual));
    }

    @Test(enabled = false)
    public void testSelectByIndex() {
        postgreSqlHelper.connect();
        postgreSqlHelper.insert(testCarPart1);
        postgreSqlHelper.insert(testCarPart2);
        postgreSqlHelper.insert(testCarPart3);
        CarPart actual = postgreSqlHelper.selectByIndex(testCarPart2.getSearchVendor(), testCarPart2.getSearchNumber()).orElseThrow();
        postgreSqlHelper.deleteAll();
        postgreSqlHelper.disconnect();
        Assert.assertTrue(testCarPart2.equals(actual));
    }

    @Test(enabled = false)
    public void testSelectAll() {
        postgreSqlHelper.connect();
        postgreSqlHelper.insert(testCarPart1);
        postgreSqlHelper.insert(testCarPart2);
        postgreSqlHelper.insert(testCarPart3);
        List<CarPart> expected = List.of(
                testCarPart1
                , testCarPart2
                , testCarPart3
        );
        List<CarPart> actual = postgreSqlHelper.selectAll();
        postgreSqlHelper.deleteAll();
        postgreSqlHelper.disconnect();
        Assert.assertEquals(expected, actual);
    }
}