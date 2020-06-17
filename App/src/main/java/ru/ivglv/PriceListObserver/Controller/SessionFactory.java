package ru.ivglv.PriceListObserver.Controller;

import dagger.Subcomponent;
import ru.ivglv.PriceListObserver.Adapter.Dagger.Session;
import ru.ivglv.PriceListObserver.Framework.ImapMail.ImapMailObserverModule;
import ru.ivglv.PriceListObserver.Framework.PostgreSQL.RemoteDataBaseModule;

@Session
@Subcomponent(modules = {RemoteDataBaseModule.class, ImapMailObserverModule.class})
public interface SessionFactory {
    ObserverSession session();
}
