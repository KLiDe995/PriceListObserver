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
        String description = getCorrectDescription(rawCarPart.getDescription());
        Float price = getCorrectPrice(rawCarPart.getPrice());
        Integer count = getCorrectCount(rawCarPart.getCount());

        if(repository.findBySearchFields(searchVendor, searchNumber).isPresent())
        {
            throw new CarPartExistsException(rawCarPart.getVendor(), rawCarPart.getNumber());
        }
        CarPart newCarPart = new CarPart.Builder(
                    rawCarPart.getVendor().replace("'", "\"")
                    , rawCarPart.getNumber().replace("'", "\"")
        )
                .searchVendor(searchVendor)
                .searchNumber(searchNumber)
                .description(description.replace("'", "\""))
                .price(price)
                .count(count)
                .build();

        repository.create(newCarPart);
        return newCarPart;
    }

    private String getCorrectDescription(String description) {
         return description.length() <= maxDescrLenghtConfig.getMaxDescriptionLenght()
                ? description
                : description.substring(0, maxDescrLenghtConfig.getMaxDescriptionLenght());
    }

    private Float getCorrectPrice(String priceString)
    {
        return Float.parseFloat(priceString.replace(',','.'));
    }

    private Integer getCorrectCount(String countString)
    {
        String checkedCountString = countString;
        if(checkedCountString.contains("-"))
            checkedCountString = checkedCountString.split("-")[1];
        return Integer.parseInt(checkedCountString.replace("<", "").replace(">", ""));
    }

}
