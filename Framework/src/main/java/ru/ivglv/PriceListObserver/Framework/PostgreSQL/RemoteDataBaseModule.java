package ru.ivglv.PriceListObserver.Framework.PostgreSQL;

import dagger.Module;
import dagger.Provides;
import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Adapter.Dagger.Session;

@Module
public class RemoteDataBaseModule {
    @Provides
    @Session
    @NotNull
    public RemoteDataBase provideRemoteDataBase(PostgreSqlHelper postgreSqlHelper)
    {
        return postgreSqlHelper;
    }
}
