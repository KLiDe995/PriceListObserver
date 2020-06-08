package ru.ivglv.PriceListObserver.Configuration.Properties;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Configuration.Port.Config;

public final class DbConfig implements Config {
    @NotNull
    private String DB_URL;
    @NotNull
    private String DB_USER;
    @NotNull
    private String PASS;
    @NotNull
    private String TABLE_NAME;

    public DbConfig(
            @NotNull String DB_URL
            , @NotNull String DB_USER
            , @NotNull String PASS
            , @NotNull String TABLE_NAME) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.PASS = PASS;
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getDbUrl() {
        return DB_URL;
    }

    public String getDbUser() {
        return DB_USER;
    }

    public String getPass() {
        return PASS;
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}
