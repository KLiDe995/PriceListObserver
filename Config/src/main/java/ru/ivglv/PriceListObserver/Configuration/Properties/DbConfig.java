package ru.ivglv.PriceListObserver.Configuration.Properties;

import ru.ivglv.PriceListObserver.Configuration.Port.Config;

import java.util.Objects;

public final class DbConfig implements Config {
    private String dbUrl;
    private String dbUser;
    private String dbPass;
    private String dbTableName;

    public DbConfig(
            String dbUrl
            , String dbUser
            , String dbPass
            , String dbTableName) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        this.dbTableName = dbTableName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    public String getTableName() {
        return dbTableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DbConfig)) return false;
        DbConfig dbConfig = (DbConfig) o;
        return dbUrl.equals(dbConfig.dbUrl) &&
                dbUser.equals(dbConfig.dbUser) &&
                dbPass.equals(dbConfig.dbPass) &&
                dbTableName.equals(dbConfig.dbTableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbUrl, dbUser, dbPass, dbTableName);
    }

    @Override
    public String toString() {
        return "DbConfig{" +
                "dbUrl='" + dbUrl + '\'' +
                ", dbUser='" + dbUser + '\'' +
                ", dbPass='" + dbPass + '\'' +
                ", dbTableName='" + dbTableName + '\'' +
                '}';
    }
}
