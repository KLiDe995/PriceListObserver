module PriceListObserver.Framework {
    exports ru.ivglv.PriceListObserver.Framework.FileHandler;
    exports ru.ivglv.PriceListObserver.Framework.ImapMail;
    exports ru.ivglv.PriceListObserver.Framework.PostgreSQL;
    requires PriceListObserver.Adapter;
    requires PriceListObserver.Domain;
    requires PriceListObserver.Config;
    requires PriceListObserver.UseCase;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires postgresql;
    requires mail;
    requires io.reactivex.rxjava3;
    requires org.reactivestreams;
    requires javax.inject;
    requires dagger;
}