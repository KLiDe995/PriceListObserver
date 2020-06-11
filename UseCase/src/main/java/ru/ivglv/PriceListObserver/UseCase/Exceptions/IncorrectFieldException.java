package ru.ivglv.PriceListObserver.UseCase.Exceptions;

public class IncorrectFieldException extends UseCaseException {
    public IncorrectFieldException(String badField, String vendor, String number) {
        super("Incorrect value in field "
                + badField
                + " in car part VENDOR ["
                + vendor
                + "] NUMBER ["
                + number
                + "]");
    }
}
