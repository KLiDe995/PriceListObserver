package ru.ivglv.PriceListObserver.Controller;

import dagger.Component;
import ru.ivglv.PriceListObserver.Configuration.ConfiguratorModule;

import javax.inject.Singleton;


@Singleton
@Component(modules = {ConfiguratorModule.class})
public interface ConsoleControllerFactory {
    ConsoleController controller();
    SessionFactory plusSessionComponent();
}
