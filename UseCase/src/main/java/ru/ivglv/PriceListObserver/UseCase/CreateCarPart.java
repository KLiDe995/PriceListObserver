package ru.ivglv.PriceListObserver.UseCase;

import ru.ivglv.PriceListObserver.Model.Port.CarPartRepository;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Entity.RawCarPart;
import ru.ivglv.PriceListObserver.Model.Exceptions.CarPartExistsException;
import ru.ivglv.PriceListObserver.Model.Exceptions.IncorrectFieldException;
import ru.ivglv.PriceListObserver.Model.Port.FieldConverter;
import ru.ivglv.PriceListObserver.Model.Port.StringFieldCutter;
import ru.ivglv.PriceListObserver.UseCase.Validator.CarPartValidator;

public final class CreateCarPart {
    private final CarPartRepository repository;
    private final FieldConverter converter;
    private final StringFieldCutter stringFieldCutter;

    public CreateCarPart(CarPartRepository repository, FieldConverter converter, StringFieldCutter stringFieldCutter) {
        this.repository = repository;
        this.converter = converter;
        this.stringFieldCutter = stringFieldCutter;
    }

    public CarPart create(final RawCarPart rawCarPart) throws IncorrectFieldException, CarPartExistsException {
        CarPartValidator.validate(rawCarPart);

        String searchVendor = converter.convertToSearchString(rawCarPart.getVendor());
        String searchNumber = converter.convertToSearchString(rawCarPart.getNumber());

        if(repository.findBySearchFields(searchVendor, searchNumber).isPresent())
        {
            throw new CarPartExistsException(rawCarPart.getVendor(), rawCarPart.getNumber());
        }
        CarPart newCarPart = new CarPart.Builder(rawCarPart.getVendor(), rawCarPart.getNumber())
                .searchVendor(searchVendor)
                .searchNumber(searchNumber)
                .description(stringFieldCutter.cut(rawCarPart.getDescription(), 512)) //TODO: сделать через конфиг
                .price(Float.parseFloat(rawCarPart.getPrice()))
                .count(Integer.parseInt(rawCarPart.getCount()))
                .build();

        repository.create(newCarPart);
        return newCarPart;
    }
}
