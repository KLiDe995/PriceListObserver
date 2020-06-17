module PriceListObserver.App {
    requires PriceListObserver.Adapter;
    requires PriceListObserver.Framework;
    requires PriceListObserver.Config;
    requires io.reactivex.rxjava3;
    requires org.reactivestreams;
    requires java.sql;
    requires javax.inject;
    requires dagger;
    requires org.jetbrains.annotations;
}