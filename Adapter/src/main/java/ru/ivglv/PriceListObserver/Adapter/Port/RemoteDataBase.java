package ru.ivglv.PriceListObserver.Adapter.Port;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;

import java.util.List;
import java.util.Optional;

public interface RemoteDataBase {
    void insert(@NotNull CarPart carPart);
    void connect();
    void disconnect();

    Optional<CarPart> selectByIndex(@NotNull String searchVendor, @NotNull String searchNumber);
    List<CarPart> selectAll();
}
