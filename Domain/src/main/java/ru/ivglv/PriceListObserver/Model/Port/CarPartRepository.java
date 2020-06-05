package ru.ivglv.PriceListObserver.Model.Port;

import ru.ivglv.PriceListObserver.Model.Entity.CarPart;

import java.util.List;
import java.util.Optional;

public interface CarPartRepository {
    void create(CarPart carPart);
    Optional<CarPart> findBySearchFields(String searchVendor, String searchNumber);
    List<CarPart> findAllCarParts();
}
