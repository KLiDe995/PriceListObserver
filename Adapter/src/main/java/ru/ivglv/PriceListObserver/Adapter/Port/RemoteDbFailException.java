package ru.ivglv.PriceListObserver.Adapter.Port;

public class RemoteDbFailException extends RuntimeException {
    public RemoteDbFailException(String message) {
        super("DB error: " + message);
    }
}
