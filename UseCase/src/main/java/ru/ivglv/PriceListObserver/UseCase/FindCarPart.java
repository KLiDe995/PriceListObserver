package ru.ivglv.PriceListObserver.UseCase;

import ru.ivglv.PriceListObserver.Model.Port.CarPartRepository;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Port.FieldConverter;

import java.util.List;
import java.util.Optional;

public final class FindCarPart {
    private final CarPartRepository repository;
    private final FieldConverter converter;

    public FindCarPart(CarPartRepository repository, FieldConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public Optional<CarPart> findCarPartByVendorAndNumber(String vendor, String number)
    {
        return repository.findBySearchFields(
                converter.convertToSearchString(vendor)
                , converter.convertToSearchString(number)
        );
    }

    public List<CarPart> findAllCarParts()
    {
        return repository.findAllCarParts();
    }
}
