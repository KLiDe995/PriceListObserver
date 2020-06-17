package ru.ivglv.PriceListObserver.Framework.ImapMail;

public class MailException extends RuntimeException {
    public MailException(String message) {
        super("Error in mail framework: " + message);
    }
}
