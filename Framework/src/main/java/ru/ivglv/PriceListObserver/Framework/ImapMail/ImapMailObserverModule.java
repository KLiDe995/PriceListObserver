package ru.ivglv.PriceListObserver.Framework.ImapMail;

import dagger.Module;
import dagger.Provides;
import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Configuration.Configurator;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;
import ru.ivglv.PriceListObserver.Adapter.Dagger.Session;

@Module
public class ImapMailObserverModule {
    @Provides
    @Session
    @NotNull
    public ImapMailObserver provideImapMailObserver(MailConfig mailConfig, Configurator configurator) {
        return new ImapMailObserver(mailConfig, configurator.getProviderConfigs().keySet());
    }
}
