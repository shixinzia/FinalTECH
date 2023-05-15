package io.taraxacum.finaltech.core.option;

import io.taraxacum.libs.slimefun.dto.LocationDataOption;

import java.util.List;

/**
 * @author Final_ROOT
 */
public class RouteShow {
    public static final String KEY = "rs";

    public static final String VALUE_TRUE = "t";
    public static final String VALUE_FALSE = "f";

    public static final LocationDataOption OPTION = new LocationDataOption(LocationDataOption.CARGO_ID, KEY, List.of(VALUE_FALSE, VALUE_TRUE));
}
