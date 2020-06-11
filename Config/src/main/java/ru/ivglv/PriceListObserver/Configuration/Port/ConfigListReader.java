package ru.ivglv.PriceListObserver.Configuration.Port;

import java.io.IOException;
import java.util.HashMap;

public abstract class ConfigListReader {
    protected String bundleName;

    public ConfigListReader(String path) {
        this.bundleName = path;
    }

    public abstract HashMap<String, Config> read() throws IOException;
}
