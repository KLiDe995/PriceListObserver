package ru.ivglv.PriceListObserver.Configuration.Port;

import java.io.IOException;

public abstract class ConfigReader {
    protected String bundleName;

    public ConfigReader(String path) {
        this.bundleName = path;
    }

    public abstract Config read() throws IOException;
}
