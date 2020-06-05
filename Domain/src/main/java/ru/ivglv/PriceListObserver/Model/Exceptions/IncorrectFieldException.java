package ru.ivglv.PriceListObserver.Model.Exceptions;

public class IncorrectFieldException extends Exception {
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
