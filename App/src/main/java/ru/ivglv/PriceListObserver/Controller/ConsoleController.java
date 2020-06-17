package ru.ivglv.PriceListObserver.Controller;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Configuration.Configurator;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ConsoleController {
    @NotNull
    private final Configurator configurator;
    private ObserverSession session;

    private boolean started;

    @Inject
    public ConsoleController(Configurator configurator) {
        this.configurator = configurator;
        //this.session = sessionComponent;
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

    private void startObserving() {
        started = true;
        System.out.println("Starting process...");

        Flowable.fromCallable(configurator::readConfiguration)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .doOnNext(ignored -> {
                    SessionFactory sessionFactory = Injector.getInstance().initSessionComponent();
                    session = sessionFactory.session();
                    session.openSession();
                })
                .flatMap(ignored -> Flowable.fromRunnable(session.getImapMailObserver()))
                .subscribe(
                        ignored -> {}
                        , throwable -> {
                            System.out.println("Got some problem: " + throwable.getMessage());
                        }
                        , () -> {
                            session.closeSession();
                            Injector.getInstance().clearSessionComponent();
                        }
                );
    }

    private void stopObserving()
    {
        if(session != null) session.closeSession();
        started = false;
    }
}
