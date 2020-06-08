module PriceListObserver.Adapter {
    //exports ru.ivglv.PriceListAdapter.Adapter.Controller;
    exports ru.ivglv.PriceListAdapter.Adapter.Converter;
    exports ru.ivglv.PriceListAdapter.Adapter.Repository;
    exports ru.ivglv.PriceListAdapter.Adapter.Port;
    requires PriceListObserver.Domain;
    requires PriceListObserver.Config;
    requires org.jetbrains.annotations;
}