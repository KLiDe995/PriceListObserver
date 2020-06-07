package ru.ivglv.PriceListAdapter.Adapter.Repository;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListAdapter.Adapter.Port.RemoteDataBase;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Port.CarPartRepository;

import java.util.List;
import java.util.Optional;

public final class RemoteDbRepository implements CarPartRepository {

    private final RemoteDataBase db;

    public RemoteDbRepository(@NotNull RemoteDataBase db) {
        this.db = db;
        dbInit();
    }

    private void dbInit() {
        //TODO: make Config
        String DB_URL = "jdbc:postgresql://127.0.0.1:5432/carparts";
        String USER = "appUser";
        String PASS = "12345678";
        db.connect(DB_URL, USER, PASS);
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
