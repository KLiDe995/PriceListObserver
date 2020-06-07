package ru.ivglv.PriceListObserver.UseCase;

import ru.ivglv.PriceListObserver.Model.Port.CarPartRepository;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Entity.RawCarPart;
import ru.ivglv.PriceListObserver.Model.Exceptions.CarPartExistsException;
import ru.ivglv.PriceListObserver.Model.Exceptions.IncorrectFieldException;
import ru.ivglv.PriceListObserver.Model.Port.FieldConverter;
import ru.ivglv.PriceListObserver.UseCase.Validator.CarPartValidator;

public final class CreateCarPart {
    private final CarPartRepository repository;
    private final FieldConverter converter;

    public CreateCarPart(CarPartRepository repository, FieldConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public CarPart create(final RawCarPart rawCarPart) throws IncorrectFieldException, CarPartExistsException {
        CarPartValidator.validate(rawCarPart);

        String searchVendor = converter.convertToSearchString(rawCarPart.getVendor());
        String searchNumber = converter.convertToSearchString(rawCarPart.getNumber());
        String description = rawCarPart.getDescription().length() <= 512    //TODO: сделать через конфиг
                ? rawCarPart.getDescription()
                : rawCarPart.getDescription().substring(0, 512);

        if(repository.findBySearchFields(searchVendor, searchNumber).isPresent())
        {
            throw new CarPartExistsException(rawCarPart.getVendor(), rawCarPart.getNumber());
        }
        CarPart newCarPart = new CarPart.Builder(rawCarPart.getVendor(), rawCarPart.getNumber())
                .searchVendor(searchVendor)
                .searchNumber(searchNumber)
                .description(description)
                .price(Float.parseFloat(rawCarPart.getPrice()))
                .count(Integer.parseInt(rawCarPart.getCount()))
                .build();

        repository.create(newCarPart);
        return newCarPart;
    }
}
