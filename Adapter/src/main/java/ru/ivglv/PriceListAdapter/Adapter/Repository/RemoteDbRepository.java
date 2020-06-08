package ru.ivglv.PriceListAdapter.Adapter.Repository;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListAdapter.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Configuration.Properties.DbConfig;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Port.CarPartRepository;

import java.util.List;
import java.util.Optional;

public final class RemoteDbRepository implements CarPartRepository {

    @NotNull private final RemoteDataBase db;
    @NotNull private final DbConfig dbConfig;

    public RemoteDbRepository(@NotNull RemoteDataBase db,@NotNull DbConfig dbConfig) {
        this.db = db;
        this.dbConfig = dbConfig;
        dbInit();
    }

    private void dbInit() {
        db.connect();
    }

    @Override
    public void create(@NotNull CarPart carPart) {
        db.insert(carPart);
    }

    @Override
    public Optional<CarPart> findBySearchFields(@NotNull String searchVendor, @NotNull String searchNumber) {
        return db.selectByIndex(searchVendor, searchNumber);
    }

    @Override
    public List<CarPart> findAllCarParts() {
        return db.selectAll();
    }
}
