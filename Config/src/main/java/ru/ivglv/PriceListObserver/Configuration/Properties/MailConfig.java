package ru.ivglv.PriceListObserver.Configuration.Properties;

import ru.ivglv.PriceListObserver.Configuration.Port.Config;

import java.util.Objects;

public final class MailConfig implements Config {
    private String imapAuthServer;
    private String imapPort;
    private String imapSslEnable;
    private String imapAuthEmail;
    private String imapAuthPassword;
    private String imapFolder;
    private String imapFileExt;

    public MailConfig (String imapAuthServer
            , String imapPort
            , String imapSslEnable
            , String imapAuthEmail
            , String imapAuthPassword
            , String imapFolder
            , String imapFileExt) {
        this.imapAuthServer = imapAuthServer;
        this.imapPort = imapPort;
        this.imapSslEnable = imapSslEnable;
        this.imapAuthEmail = imapAuthEmail;
        this.imapAuthPassword = imapAuthPassword;
        this.imapFolder = imapFolder;
        this.imapFileExt = imapFileExt;
    }

    public String getImapAuthServer() {
        return imapAuthServer;
    }

    public String getImapPort() {
        return imapPort;
    }

    public String getImapSslEnable() {
        return imapSslEnable;
    }

    public String getImapAuthEmail() {
        return imapAuthEmail;
    }

    public String getImapAuthPassword() {
        return imapAuthPassword;
    }

    public String getImapFolder() {
        return imapFolder;
    }

    public String getImapFileExt() {
        return imapFileExt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MailConfig)) return false;
        MailConfig that = (MailConfig) o;
        return imapAuthServer.equals(that.imapAuthServer) &&
                imapPort.equals(that.imapPort) &&
                imapSslEnable.equals(that.imapSslEnable) &&
                imapAuthEmail.equals(that.imapAuthEmail) &&
                imapAuthPassword.equals(that.imapAuthPassword) &&
                imapFolder.equals(that.imapFolder) &&
                imapFileExt.equals(that.imapFileExt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imapAuthServer, imapPort, imapSslEnable, imapAuthEmail, imapAuthPassword, imapFolder, imapFileExt);
    }
}
