package ru.ivglv.PriceListObserver.Framework.FileHandler;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Controller.PriceListController;
import ru.ivglv.PriceListObserver.Adapter.Port.IncomingFileHandler;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;
import ru.ivglv.PriceListObserver.Adapter.Dagger.Session;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Session
public final class CsvFileHandler implements IncomingFileHandler {
    private final String CSV_COLUMN_DELIMETER = ";";
    @NotNull
    private final HashMap<String, ProviderConfig> providerConfigs;
    @NotNull
    private final PriceListController controller;

    @Inject
    public CsvFileHandler(@NotNull HashMap<String, ProviderConfig> providerConfigs, @NotNull PriceListController controller) {
        this.providerConfigs = providerConfigs;
        this.controller = controller;
    }

    @Override
    public void handle(File file, String sender) throws IOException {
        handleFileWithProvider(file, providerConfigs.get(convertToEmail(sender)));
    }

    private String convertToEmail(String sender)
    {
        return sender.split(" ")[1].replace("<", "").replace(">", "");
    }

    private void handleFileWithProvider(File file, ProviderConfig config) throws IOException {
        HashMap<String, Integer> columnIndices = getColumnIndices(Files.lines(file.toPath()).findFirst().orElseThrow(), config);

        Flowable.fromArray(Files.lines(file.toPath()).skip(1).toArray())
                .subscribeOn(Schedulers.computation())
                .flatMap(o -> splitCsvLineFlow((String) o))
                .doOnNext(carPartFields -> controller.createCarPart(carPartFields, columnIndices, config))
                .subscribe(ignored -> {}
                        , throwable -> System.out.println(throwable.getMessage())
                        , () -> System.out.println("File processing complete"));
    }

    private Flowable<String[]> splitCsvLineFlow(String line)
    {
        return Flowable.just(line)
                .subscribeOn(Schedulers.computation())
                .map(this::splitCsvLine);
    }

    public String[] splitCsvLine(String line)
    {
        if(line.contains("\""))
        {
            HashMap<Integer, String> hashMap = new HashMap<>();
            Pattern pattern = Pattern.compile(";\".+?\";");
            Matcher matcher = pattern.matcher(line);
            String mappedString = line;
            while(matcher.find())
            {
                String found = line.substring(matcher.start() + 1, matcher.end() - 1);
                hashMap.put(found.hashCode(), found);
                mappedString = mappedString.replace(found, String.valueOf(found.hashCode()));
            }
            String[] splittedLine = mappedString.split(CSV_COLUMN_DELIMETER);
            for(int i = 0; i < splittedLine.length; i++)
                try {
                    Integer key = Integer.parseInt(splittedLine[i]);
                    if(hashMap.containsKey(key))
                        splittedLine[i] = hashMap.get(key).replace("\"", "");
                } catch (NumberFormatException ignored) {}
            return splittedLine;
        }
        else
            return line.split(CSV_COLUMN_DELIMETER);
    }

    private HashMap<String, Integer> getColumnIndices(String firstLine, @NotNull ProviderConfig config) {
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
