package ru.ivglv.PriceListObserver.Model.Exceptions;

public class CarPartExistsException extends Exception {
    public CarPartExistsException(String vendor, String number) {
        super("Car part with VENDOR [" + vendor + "] and NUMBER [" + number + "] already exists");
    }
}
