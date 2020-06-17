package ru.ivglv.PriceListObserver.Controller;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Dagger.Session;
import ru.ivglv.PriceListObserver.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Framework.FileHandler.CsvFileHandler;
import ru.ivglv.PriceListObserver.Framework.ImapMail.ImapMailObserver;
import ru.ivglv.PriceListObserver.Framework.PostgreSQL.PostgreSqlHelper;

import javax.inject.Inject;

@Session
public class ObserverSession {
    @NotNull
    private final PostgreSqlHelper postgreSqlHelper;
    @NotNull
    private final ImapMailObserver imapMailObserver;
    @NotNull
    private final CsvFileHandler csvFileHandler;

    @Inject
    public ObserverSession(@NotNull PostgreSqlHelper postgreSqlHelper, @NotNull ImapMailObserver imapMailObserver, @NotNull CsvFileHandler csvFileHandler) {
        this.postgreSqlHelper = postgreSqlHelper;
        this.imapMailObserver = imapMailObserver;
        this.csvFileHandler = csvFileHandler;
    }

    public PostgreSqlHelper getPostgreSqlHelper() {
        return postgreSqlHelper;
    }

    public ImapMailObserver getImapMailObserver() {
        return imapMailObserver;
    }

    public CsvFileHandler getCsvFileHandler() {
        return csvFileHandler;
    }

    public void openSession() {
        postgreSqlHelper.connect();
        imapMailObserver.openSession();
        imapMailObserver.addMessageListener(csvFileHandler);
    }

    public void closeSession()
    {
        imapMailObserver.closeSession();
        postgreSqlHelper.disconnect();
    }
}
