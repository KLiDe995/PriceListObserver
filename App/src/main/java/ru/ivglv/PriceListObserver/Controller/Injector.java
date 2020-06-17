package ru.ivglv.PriceListObserver.Controller;


import ru.ivglv.PriceListObserver.Configuration.ConfiguratorModule;

public class Injector {
    private static Injector instance;
    private ConsoleControllerFactory consoleControllerFactory;
    private SessionFactory sessionFactory;

    public static Injector getInstance() {
        if(instance == null) instance = new Injector();
        return instance;
    }

    public ConsoleControllerFactory getConsoleControllerFactory() {
        return consoleControllerFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public ConsoleControllerFactory initConsoleController()
    {
        consoleControllerFactory = DaggerConsoleControllerFactory.builder()
                .configuratorModule(new ConfiguratorModule())
                .build();
        return consoleControllerFactory;
    }

    public SessionFactory initSessionComponent()
    {
        if(consoleControllerFactory != null && sessionFactory == null)
            sessionFactory = consoleControllerFactory.plusSessionComponent();
        return sessionFactory;
    }

    public void clearSessionComponent()
    {
        sessionFactory = null;
    }
}
