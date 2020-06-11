package ru.ivglv.PriceListObserver.Adapter.Repository;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Port.CarPartRepository;

import java.util.List;
import java.util.Optional;

public final class RemoteDbRepository implements CarPartRepository {

    @NotNull private final RemoteDataBase db;

    public RemoteDbRepository(@NotNull RemoteDataBase db) {
        this.db = db;
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
