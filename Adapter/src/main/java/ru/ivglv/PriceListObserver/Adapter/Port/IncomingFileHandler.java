package ru.ivglv.PriceListObserver.Adapter.Port;

import java.io.File;
import java.io.IOException;

public interface IncomingFileHandler {
    void handle(File file, String sender) throws IOException;
}
