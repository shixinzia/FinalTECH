package io.taraxacum.finaltech.core.option;

import io.taraxacum.libs.slimefun.dto.LocationDataOption;

import java.util.ArrayList;

/**
 * @author Final_ROOT
 */
public class IgnorePermission {
    public static final String KEY = "ip";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_TRUE = "t";

    public static final LocationDataOption OPTION = new LocationDataOption(LocationDataOption.CARGO_ID, KEY, new ArrayList<>() {{
        this.add(VALUE_FALSE);
        this.add(VALUE_TRUE);
    }});
}
