package ru.ivglv.PriceListObserver.Configuration;

import dagger.Module;
import dagger.Provides;
import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;

import javax.inject.Singleton;
import java.util.HashMap;

@Module
public class ConfiguratorModule {
    @Provides
    @NotNull
    @Singleton
    public Configurator provideConfigurator() {
        return new Configurator(
                "dbconfig"
                , "providers"
                , "imap"
        );
    }

    @Provides
    @NotNull
    public DbConfig provideDbConfig(Configurator configurator) {
        return configurator.getDbConfig();
    }

    @Provides
    @NotNull
    public MailConfig provideMailConfig(Configurator configurator) {
        return configurator.getMailConfig();
    }

    @Provides
    @NotNull
    public HashMap<String, ProviderConfig> provideProviderConfigs(Configurator configurator) {
        return configurator.getProviderConfigs();
    }
}
