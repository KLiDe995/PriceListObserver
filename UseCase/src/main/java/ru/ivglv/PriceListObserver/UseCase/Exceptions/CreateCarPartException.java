package ru.ivglv.PriceListObserver.UseCase.Exceptions;

public class CreateCarPartException extends UseCaseException {
    public CreateCarPartException(String message) {
        super("Cannot add car part: " + message);
    }
}
