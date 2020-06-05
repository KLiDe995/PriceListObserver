package ru.ivglv.PriceListObserver.Model.Port;

public interface FieldConverter {
    String convertToSearchString(String searchField);
    float convertToFloat(String floatString);
    int convertToInt(String intString);
}
