package ru.ivglv.PriceListObserver.UseCase.Validator;

import ru.ivglv.PriceListObserver.Model.Entity.RawCarPart;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.IncorrectFieldException;

public final class CarPartValidator {
    private static String vendor;
    private static String number;

    public static void validate(RawCarPart rawCarPart) throws IncorrectFieldException {
        vendor = rawCarPart.getVendor();
        number = rawCarPart.getNumber();
        validatePrice(rawCarPart.getPrice());
        validateCount(rawCarPart.getCount());
    }

    private static void validatePrice(String price) throws IncorrectFieldException {
        try {
            Float.parseFloat(price.replace(',','.'));
        }
        catch (NumberFormatException ex)
        {
            throw new IncorrectFieldException("Price", vendor, number);
        }
    }

    private static void validateCount(String count) throws IncorrectFieldException {
        try {
            String checkedCountString = count;
            if(checkedCountString.contains("-"))
                checkedCountString = checkedCountString.split("-")[1];
            Integer.parseInt(checkedCountString.replace("<", "").replace(">", ""));
        }
        catch (NumberFormatException ex)
        {
            throw new IncorrectFieldException("Count", vendor, number);
        }
        catch (IndexOutOfBoundsException ex)
        {
            throw new IncorrectFieldException("Count", vendor, number);
        }
    }

}
