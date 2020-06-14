package ru.ivglv.PriceListObserver.Controller;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.ivglv.PriceListObserver.Adapter.Controller.PriceListController;
import ru.ivglv.PriceListObserver.Adapter.Port.IncomingFileHandler;
import ru.ivglv.PriceListObserver.Configuration.Configurator;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;
import ru.ivglv.PriceListObserver.Framework.FileHandler.CsvFileHandler;
import ru.ivglv.PriceListObserver.Framework.ImapMail.ImapMailObserver;
import ru.ivglv.PriceListObserver.Framework.PostgreSQL.PostgreSqlHelper;

import java.io.IOException;
import java.util.Set;

public final class ConsoleController {
    private PostgreSqlHelper remoteDb;
    private ImapMailObserver mailObserver;
    private boolean started;

    public ConsoleController() {
    }

    public void handleCommand(String command)
    {
        if(command.equalsIgnoreCase("start")) startObserving();
        if(command.equalsIgnoreCase("stop")) stopObserving();
        if(command.equalsIgnoreCase("exit")) exitProgram();
    }

    private void exitProgram() {
        if(started)
            stopObserving();
        System.exit(0);
    }

    private void startObserving()
    {
        started = true;
        System.out.println("Starting process...");

        Flowable.fromCallable(this::createConfiguration)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .doOnNext(this::initConnections)
                .flatMap(ignored -> Flowable.fromRunnable(mailObserver))
                .subscribe(
                        ignored -> {}
                        , throwable -> {
                            System.out.println("Got some problem: " + throwable.getMessage());
                        }
                );
    }

    private void initConnections(Configurator configurator) throws ClassNotFoundException {
        PostgreSqlHelper postgreSqlHelper = createRemoteDbInstance(configurator);
        PriceListController controller = new PriceListController(postgreSqlHelper, configurator.getDbConfig());
        CsvFileHandler csvFileHandler = new CsvFileHandler(configurator.getProviderConfigs(), controller);

        postgreSqlHelper.connect();
        initImapFramework(configurator.getMailConfig(), configurator.getProviderConfigs().keySet(), csvFileHandler);
    }

    private void stopObserving()
    {
        if(mailObserver != null)
            mailObserver.closeSession();
        if(remoteDb != null)
            remoteDb.disconnect();
        started = false;
    }

    private Configurator createConfiguration() throws IOException {
        Configurator configurator = new Configurator(
                "dbconfig"
                , "providers"
                , "imap"
        );
        configurator.readConfiguration();
        return configurator;
    }

    private PostgreSqlHelper createRemoteDbInstance(Configurator configurator) throws ClassNotFoundException {
        remoteDb = new PostgreSqlHelper(configurator.getDbConfig());
        return remoteDb;
    }

    private void initImapFramework(MailConfig config, Set<String> emails, IncomingFileHandler fileHandler)
    {
        mailObserver = new ImapMailObserver(config, emails);
        mailObserver.openSession();
        mailObserver.addMessageListener(fileHandler);
    }
}
