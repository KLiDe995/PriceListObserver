package ru.ivglv.PriceListObserver.UseCase.Exceptions;

public class CarPartExistsException extends UseCaseException {
    public CarPartExistsException(String vendor, String number) {
        super("Car part with VENDOR [" + vendor + "] and NUMBER [" + number + "] already exists");
    }
}
