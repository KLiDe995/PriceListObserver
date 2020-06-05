package ru.ivglv.PriceListObserver.Ui;

import io.reactivex.rxjava3.core.Flowable;

public class MainWindow {
    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::println);
    }
}
