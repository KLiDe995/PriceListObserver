package ru.ivglv.PriceListAdapter.Adapter.Converter;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.ivglv.PriceListObserver.Model.Exceptions.IncorrectFieldException;

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

        String expected1 = "ABCD1234EFGH";
        String expected2 = "ABCD1";

        String actual1 = converter.convertToSearchString(input1);
        String actual2 = converter.convertToSearchString(input2);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }
}