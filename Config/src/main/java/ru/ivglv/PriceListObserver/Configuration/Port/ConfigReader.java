package ru.ivglv.PriceListObserver.Configuration.Port;

import java.io.IOException;

public interface ConfigReader {
    public Config read() throws IOException;
}
