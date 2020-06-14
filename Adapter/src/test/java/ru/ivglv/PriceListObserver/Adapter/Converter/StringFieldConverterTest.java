package ru.ivglv.PriceListObserver.Adapter.Converter;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StringFieldConverterTest {
    private StringFieldConverter converter;

    @BeforeClass
    public void setUp() throws Exception {
        converter = new StringFieldConverter();
    }

    @Test
    public void testConvertToSearchString() {
        String input1 = "!@#Abcd%^&12 34_*()efgh";
        String input2 = "ABcD-1";
        String input3 = "¿‚ÚÓ·ÓÌˇ-";

        String expected1 = "ABCD1234EFGH";
        String expected2 = "ABCD1";
        String expected3 = "¿¬“Œ¡–ŒÕﬂ";

        String actual1 = converter.convertToSearchString(input1);
        String actual2 = converter.convertToSearchString(input2);
        String actual3 = converter.convertToSearchString(input3);

        Assert.assertEquals(actual1, expected1);
        Assert.assertEquals(actual2, expected2);
        Assert.assertEquals(actual3, expected3);
    }
}