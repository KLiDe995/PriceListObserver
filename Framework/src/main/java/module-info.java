module PriceListObserver.Framework {
    requires PriceListObserver.Adapter;
    requires PriceListObserver.Domain;
    requires PriceListObserver.Config;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires mail;
}