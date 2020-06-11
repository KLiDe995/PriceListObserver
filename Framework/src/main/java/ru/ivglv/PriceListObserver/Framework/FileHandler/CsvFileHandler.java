package ru.ivglv.PriceListObserver.Framework.FileHandler;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Controller.PriceListController;
import ru.ivglv.PriceListObserver.Adapter.Port.IncomingFileHandler;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.CreateCarPartException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class CsvFileHandler implements IncomingFileHandler {
    private final String CSV_COLUMN_DELIMETER = ";";
    @NotNull
    private final HashMap<String, ProviderConfig> providerConfigs;
    @NotNull
    private final PriceListController controller;

    public CsvFileHandler(@NotNull HashMap<String, ProviderConfig> providerConfigs, @NotNull PriceListController controller) {
        this.providerConfigs = providerConfigs;
        this.controller = controller;
    }

    @Override
    public Flowable<CarPart> handle(File file, String sender) throws CreateCarPartException, IOException {
        return handleFileWithProvider(file, providerConfigs.get(sender));
    }

    private Flowable<CarPart> handleFileWithProvider(File file, ProviderConfig config) throws CreateCarPartException, IOException {
        HashMap<String, Integer> columnIndices = getColumnIndices(Files.lines(file.toPath()).findFirst().orElseThrow(), config);

        return Flowable.fromArray(Files.lines(file.toPath()).skip(1).toArray())
                .flatMap(o ->
                        Flowable.just((String) o)
                            .subscribeOn(Schedulers.computation())
                            .map(line -> line.split(CSV_COLUMN_DELIMETER))
                            .doOnError(throwable -> {
                                throw new CreateCarPartException("Cannot split line {" + (String) o + "}"); //TODO: логирование
                            })
                )
                .flatMap(carPartFields ->
                        Flowable.just(carPartFields)
                            .subscribeOn(Schedulers.computation())
                            .map(fields ->
                                controller.createCarPart(controller.createRawCarPart(carPartFields, columnIndices, config)))
                            .doOnError(throwable -> System.out.println(throwable.getMessage())) //TODO: логирование
                )
                .flatMap(carPart ->
                        Flowable.just(carPart)
                            .subscribeOn(Schedulers.computation())
                            .map(printableCarPart -> {
                                        System.out.println(
                                                "Car part with vendor '"
                                                        + printableCarPart.getVendor()
                                                        + "' number '"
                                                        + printableCarPart.getNumber()
                                                        + "' added to database");
                                        return printableCarPart;
                                    })
                            .doOnError(Throwable::printStackTrace) //TODO: логирование
                );
    }

    private HashMap<String, Integer> getColumnIndices(String firstLine, ProviderConfig config) {
        HashMap<String, Integer> result = new HashMap<>();
        String[] columns = firstLine.split(CSV_COLUMN_DELIMETER);
        List<String> columnKeys = config.getAllColumnNames();
        for(String key : columnKeys)
        {
            for(int i = 0; i < columns.length; i++)
                if(columns[i].equalsIgnoreCase(key)) {
                    result.put(key, i);
                    break;
                }
        }
        return result;
    }
}
