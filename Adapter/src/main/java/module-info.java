module PriceListObserver.Adapter {
    exports ru.ivglv.PriceListObserver.Adapter.Controller;
    exports ru.ivglv.PriceListObserver.Adapter.Converter;
    exports ru.ivglv.PriceListObserver.Adapter.Repository;
    exports ru.ivglv.PriceListObserver.Adapter.Port;
    exports ru.ivglv.PriceListObserver.Adapter.Dagger;
    requires PriceListObserver.Domain;
    requires PriceListObserver.UseCase;
    requires PriceListObserver.Config;
    requires org.jetbrains.annotations;
    requires io.reactivex.rxjava3;
    requires org.reactivestreams;
    requires javax.inject;
    requires dagger;
}