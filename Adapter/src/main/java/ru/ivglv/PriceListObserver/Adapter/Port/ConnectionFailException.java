package ru.ivglv.PriceListObserver.Adapter.Port;

public class ConnectionFailException extends RuntimeException {
    public ConnectionFailException(String message) {
        super("Connection to remote DB failed: " + message);
    }
}
