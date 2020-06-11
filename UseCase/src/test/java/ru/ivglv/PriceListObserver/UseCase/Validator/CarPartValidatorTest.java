package ru.ivglv.PriceListObserver.UseCase.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.ivglv.PriceListObserver.Model.Entity.RawCarPart;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.IncorrectFieldException;

public class CarPartValidatorTest {

    @Test(expectedExceptions = IncorrectFieldException.class)
    public void testValidate_IfBadPrice() throws IncorrectFieldException{
        RawCarPart rawCarPart = new RawCarPart.Builder("TestVendor", "TestNumber")
                .price("Bad_field")
                .build();

        CarPartValidator.validate(rawCarPart);
    }

    @Test(expectedExceptions = IncorrectFieldException.class)
    public void testValidate_IfBadCount() throws IncorrectFieldException{
        RawCarPart rawCarPart = new RawCarPart.Builder("TestVendor", "TestNumber")
                .price("123.45")
                .count("Bad_field")
                .build();

        CarPartValidator.validate(rawCarPart);
    }

    @Test
    public void testValidate() {
        RawCarPart rawCarPart = new RawCarPart.Builder("TestVendor", "TestNumber")
                .price("123.69")
                .count("159")
                .build();

        try {
            CarPartValidator.validate(rawCarPart);
        }
        catch (IncorrectFieldException ex)
        {
            Assert.fail();
        }
        Assert.assertTrue(true);

    }
}