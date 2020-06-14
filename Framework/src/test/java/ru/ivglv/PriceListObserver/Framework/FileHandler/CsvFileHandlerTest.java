package ru.ivglv.PriceListObserver.Framework.FileHandler;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import ru.ivglv.PriceListObserver.Adapter.Controller.PriceListController;
import ru.ivglv.PriceListObserver.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Configuration.Configurator;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Configuration.Properties.ProviderConfig;
import ru.ivglv.PriceListObserver.Framework.PostgreSQL.PostgreSqlHelper;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

import static org.testng.Assert.*;

public class CsvFileHandlerTest {
    CsvFileHandler csvFileHandler;
    PostgreSqlHelper remoteDb;

    @BeforeClass
    public void setUp() throws ClassNotFoundException {
        HashMap<String, ProviderConfig> configs = new HashMap<>();
        configs.put("tj-ivan09@yandex.ru", new ProviderConfig(
                "Бренд"
                , "Каталожный номер"
                , "Описание"
                , "Цена, руб."
                , "Наличие"
        ));
        DbConfig dbConfig = new DbConfig(
                "jdbc:postgresql://127.0.0.1:5432/carparts"
                , "appUser"
                , "123456789"
                , "PriceItems"
                , 512
        );
        remoteDb = new PostgreSqlHelper(dbConfig);
        //remoteDb.connect();
        PriceListController priceListController = new PriceListController(remoteDb, dbConfig);
        csvFileHandler = new CsvFileHandler(configs, priceListController);
    }

    @AfterClass
    public void tearDown() {
        //remoteDb.disconnect();
    }

    /*@Test(enabled = false)
    public void testHandle() throws IOException, InterruptedException {
        FileWriter fw = new FileWriter("temp.csv", StandardCharsets.UTF_8);
        fw.write("Номенклатура;Бренд;Артикул;Описание;Вес/Объем;Кратность отгрузки;Цена, руб.;Базовая цена, руб;Наличие;Срок поставки, дн.;Каталожный номер;OEМ Номер;Применимость;Вендор-код\n");
        fw.write("NSIN0018472693;555;SA1712L;Рычаг подвески | перед лев |;1,68;1;1343,68;2123,14;2;0;SA-1712L;\"SA-1712R;51376;0501-050;0501-051;0505-DEM;0524-DEM;D201-34-350A;HCA-3814L;\";MAZDA Demio DW3W 00- , KIA AVELLA 94- LOW L;SA1712L\n");
        fw.write("NSIN0018472694;555;SA1712R;Рычаг подвески | перед прав |;1,68;1;1343,68;2123,14;2;0;SA-1712R;\"SA-1712L;51377;D201-34-300E;HCA-3814R;D20134300A;D201-34-300B;D201-34-300C;\";MAZDA Demio DW3W 00- , KIA AVELLA 94- LOW R;SA1712R\n");
        fw.close();
        File file = new File("temp.csv");
        List<CarPart> actual = new ArrayList<>();
        csvFileHandler.handleFileWithProvider(file, new ProviderConfig(
                "Бренд"
                , "Каталожный номер"
                , "Описание"
                , "Цена, руб."
                , "Наличие"))
                .flatMap(carPart ->
                        Flowable.just(carPart)
                                .subscribeOn(Schedulers.computation())
                                .map(carPart1 -> actual.add(carPart))
                )
                .blockingSubscribe(bool -> {
                    System.out.println("Success");
                }
                , throwable -> {}
                , () -> {
                    System.out.println("Added " + actual.size() + " car parts");
                    remoteDb.deleteAll();
                 });
    }*/

    @Test
    public void testSplitCsvLine() {
        String input1 = "NSIN0018472693;555;SA1712L;Рычаг подвески | перед лев |;1,68;1;1343,68;2123,14;2;0;SA-1712L;\"SA-1712R;51376;0501-050;0501-051;0505-DEM;0524-DEM;D201-34-350A;HCA-3814L;\";MAZDA Demio DW3W 00- , KIA AVELLA 94- LOW L;SA1712L";
        String[] expected1 = {
                "NSIN0018472693"
                , "555"
                , "SA1712L"
                , "Рычаг подвески | перед лев |"
                , "1,68"
                , "1"
                , "1343,68"
                , "2123,14"
                , "2"
                , "0"
                , "SA-1712L"
                , "SA-1712R;51376;0501-050;0501-051;0505-DEM;0524-DEM;D201-34-350A;HCA-3814L;"
                , "MAZDA Demio DW3W 00- , KIA AVELLA 94- LOW L"
                , "SA1712L"
        };
        String input2 = "NSII0011259201;Osram;64198SB;\"Лампа накаливания OFF-ROAD Super Brigh R2\" 12В 60/55Вт\"\"\"ампа накалива\";0,01;1;165,53;233,06;8;0;64198SB;;;64198SB";
        String[] expected2 = {
                "NSII0011259201"
                , "Osram"
                , "64198SB"
                , "Лампа накаливания OFF-ROAD Super Brigh R2 12В 60/55Втампа накалива"
                , "0,01"
                , "1"
                , "165,53"
                , "233,06"
                , "8"
                , "0"
                , "64198SB"
                , ""
                , ""
                , "64198SB"
        };

        String input3 = "NSII0011259201;Osram;64198SB;\"Лампа накаливания OFF-ROAD Super Brigh R2\" 12В 60/55Вт\"\"\"ампа накалива\";0,01;1;165,53;233,06;8;0;\"64198SB\";;;64198SB";
        String[] expected3 = {
                "NSII0011259201"
                , "Osram"
                , "64198SB"
                , "Лампа накаливания OFF-ROAD Super Brigh R2 12В 60/55Втампа накалива"
                , "0,01"
                , "1"
                , "165,53"
                , "233,06"
                , "8"
                , "0"
                , "64198SB"
                , ""
                , ""
                , "64198SB"
        };

        String[] actual1 = csvFileHandler.splitCsvLine(input1);
        String[] actual2 = csvFileHandler.splitCsvLine(input2);
        String[] actual3 = csvFileHandler.splitCsvLine(input3);

        Assert.assertEquals(actual1, expected1);
        Assert.assertEquals(actual2, expected2);
        Assert.assertEquals(actual3, expected3);
    }
}