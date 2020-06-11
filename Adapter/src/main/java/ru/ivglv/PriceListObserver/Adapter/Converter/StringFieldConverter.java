package ru.ivglv.PriceListObserver.Adapter.Converter;

import ru.ivglv.PriceListObserver.Model.Port.FieldConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringFieldConverter implements FieldConverter {
    @Override
    public String convertToSearchString(String rawString) {
        Pattern pattern = Pattern.compile("[\\W_]");
        Matcher matcher = pattern.matcher(rawString);
        return matcher.replaceAll("").toUpperCase();
    }
}
