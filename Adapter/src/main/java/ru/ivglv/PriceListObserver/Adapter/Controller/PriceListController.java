package ru.ivglv.PriceListObserver.Adapter.Controller;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Converter.StringFieldConverter;
import ru.ivglv.PriceListObserver.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Adapter.Repository.RemoteDbRepository;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Entity.RawCarPart;
import ru.ivglv.PriceListObserver.UseCase.CreateCarPart;
import ru.ivglv.PriceListObserver.UseCase.FindCarPart;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class PriceListController {
    @NotNull
    private final CreateCarPart carPartCreator;
    @NotNull
    private final FindCarPart carPartFinder;

    public PriceListController(@NotNull RemoteDataBase remoteDataBase, @NotNull DbConfig dbConfig) {
        RemoteDbRepository repository = new RemoteDbRepository(remoteDataBase);
        StringFieldConverter converter = new StringFieldConverter();
        carPartCreator = new CreateCarPart(repository, converter, dbConfig);
        carPartFinder = new FindCarPart(repository, converter);
    }

    public void createCarPart(String[] carPartFields, HashMap<String, Integer> columnIndices, ProviderConfig config) {
        Flowable.just(carPartFields)
                .map(fields ->
                        carPartCreator.create(createRawCarPart(carPartFields, columnIndices, config)))
                .subscribeOn(Schedulers.computation())
                .subscribe(carPart -> System.out.println(
                                "Car part with vendor '"
                                + carPart.getVendor()
                                + "' number '"
                                + carPart.getNumber()
                                + "' added to database")
                        , throwable -> System.out.println(
                                "Cannot add car part: " + throwable.getMessage())
                );
    }

    private RawCarPart createRawCarPart(String[] values, HashMap<String, Integer> columnIndices, ProviderConfig config)
    {
        return new RawCarPart.Builder(
                    values[columnIndices.get(config.getVendorColumnName())]
                    , values[columnIndices.get(config.getNumberColumnName())]
                )
                .description(values[columnIndices.get(config.getDescrColumnName())])
                .price(values[columnIndices.get(config.getPriceColumnName())])
                .count(values[columnIndices.get(config.getCountColumnName())])
                .build();
    }

    public void findCarPart(String vendor, String number) {
        try {
            Optional<CarPart> foundCarPart = carPartFinder.findCarPartByVendorAndNumber(vendor, number);
            System.out.println(foundCarPart.orElseThrow());
        } catch (NoSuchElementException e) {
            System.out.println("Car part with vendor '" + vendor + "' and number '" + number + "' not found");
        }
    }

    public void getAllCarParts()
    {
        List<CarPart> allParts = carPartFinder.findAllCarParts();
        for (CarPart part: allParts) {
            System.out.println(part);
        }
    }
}
