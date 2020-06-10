package ru.ivglv.PriceListAdapter.Adapter.Port;

import java.io.File;
import java.io.FileNotFoundException;

public interface IncomingFileHandler {
    void handle(File file, String sender);
}
