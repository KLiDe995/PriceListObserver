package ru.ivglv.PriceListObserver.Adapter.Controller;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Converter.StringFieldConverter;
import ru.ivglv.PriceListObserver.Adapter.Port.IncomingFileHandler;
import ru.ivglv.PriceListObserver.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Adapter.Repository.RemoteDbRepository;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Entity.RawCarPart;
import ru.ivglv.PriceListObserver.UseCase.CreateCarPart;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.UseCaseException;
import ru.ivglv.PriceListObserver.UseCase.FindCarPart;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.CreateCarPartException;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class PriceListController {
    @NotNull
    private final CreateCarPart carPartCreator;
    @NotNull
    private final FindCarPart carPartFinder;
    @NotNull
    private final IncomingFileHandler fileHandler;

    public PriceListController(@NotNull RemoteDataBase remoteDataBase, @NotNull DbConfig dbConfig, @NotNull IncomingFileHandler fileHandler) {
        RemoteDbRepository repository = new RemoteDbRepository(remoteDataBase);
        StringFieldConverter converter = new StringFieldConverter();
        carPartCreator = new CreateCarPart(repository, converter, dbConfig);
        carPartFinder = new FindCarPart(repository, converter);
        this.fileHandler = fileHandler;
    }

    public CarPart createCarPart(RawCarPart rawCarPart) throws CreateCarPartException {
        try {
            return carPartCreator.create(rawCarPart);
        } catch (UseCaseException ex) {
            throw new CreateCarPartException(ex.getMessage());
        }

    }

    public RawCarPart createRawCarPart(String[] values, HashMap<String, Integer> columnIndices, ProviderConfig config)
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
