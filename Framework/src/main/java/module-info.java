module PriceListObserver.Framework {
    requires PriceListObserver.Adapter;
    requires PriceListObserver.Domain;
    requires PriceListObserver.Config;
    requires PriceListObserver.UseCase;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires mail;
    requires io.reactivex.rxjava3;
    requires org.reactivestreams;
}