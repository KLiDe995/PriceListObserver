package ru.ivglv.PriceListObserver.Adapter.Port;

import io.reactivex.rxjava3.core.Flowable;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.CreateCarPartException;

import java.io.File;
import java.io.IOException;

public interface IncomingFileHandler {
    Flowable<CarPart> handle(File file, String sender) throws CreateCarPartException, IOException;
}
