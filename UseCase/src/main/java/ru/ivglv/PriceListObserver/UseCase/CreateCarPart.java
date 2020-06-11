package ru.ivglv.PriceListObserver.UseCase;

import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Model.Port.CarPartRepository;
import ru.ivglv.PriceListObserver.Model.Entity.CarPart;
import ru.ivglv.PriceListObserver.Model.Entity.RawCarPart;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.CarPartExistsException;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.IncorrectFieldException;
import ru.ivglv.PriceListObserver.Model.Port.FieldConverter;
import ru.ivglv.PriceListObserver.UseCase.Exceptions.UseCaseException;
import ru.ivglv.PriceListObserver.UseCase.Port.MaxDescrLenghtConfig;
import ru.ivglv.PriceListObserver.UseCase.Validator.CarPartValidator;

public final class CreateCarPart {
    @NotNull
    private final CarPartRepository repository;
    @NotNull
    private final FieldConverter converter;
    @NotNull
    private final MaxDescrLenghtConfig maxDescrLenghtConfig;

    public CreateCarPart(@NotNull CarPartRepository repository, @NotNull FieldConverter converter, @NotNull MaxDescrLenghtConfig maxDescrLenghtConfig) {
        this.repository = repository;
        this.converter = converter;
        this.maxDescrLenghtConfig = maxDescrLenghtConfig;
    }

    public CarPart create(final RawCarPart rawCarPart) throws UseCaseException {
        CarPartValidator.validate(rawCarPart);

        String searchVendor = converter.convertToSearchString(rawCarPart.getVendor());
        String searchNumber = converter.convertToSearchString(rawCarPart.getNumber());
        String description = rawCarPart.getDescription().length() <= maxDescrLenghtConfig.getMaxDescriptionLenght()
                ? rawCarPart.getDescription()
                : rawCarPart.getDescription().substring(0, maxDescrLenghtConfig.getMaxDescriptionLenght());

        if(repository.findBySearchFields(searchVendor, searchNumber).isPresent())
        {
            throw new CarPartExistsException(rawCarPart.getVendor(), rawCarPart.getNumber());
        }
        CarPart newCarPart = new CarPart.Builder(rawCarPart.getVendor(), rawCarPart.getNumber())
                .searchVendor(searchVendor)
                .searchNumber(searchNumber)
                .description(description)
                .price(Float.parseFloat(rawCarPart.getPrice().replace(',','.')))
                .count(Integer.parseInt(rawCarPart.getCount()))
                .build();

        repository.create(newCarPart);
        return newCarPart;
    }
}
