package io.taraxacum.finaltech.core.option;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.slimefun.dto.LocationDataLoreOption;
import io.taraxacum.libs.slimefun.dto.LocationDataOption;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Final_ROOT
 */
public final class CargoNumberMode {
    public static final String KEY = "ccm";
    public static final String KEY_INPUT = "ccmi";
    public static final String KEY_OUTPUT = "ccmo";

    public static final String VALUE_UNIVERSAL = "u";
    public static final String VALUE_STANDALONE = "s";

    private static final String UNIVERSAL_LORE = FinalTech.getLanguageString("option", "CARGO_NUMBER_MODE", "universal", "lore1");
    private static final String STANDALONE_LORE = FinalTech.getLanguageString("option", "CARGO_NUMBER_MODE", "standalone", "lore1");

    public static final LocationDataLoreOption OPTION = new LocationDataLoreOption(LocationDataOption.CARGO_ID, KEY, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});

    public static final LocationDataLoreOption INPUT_OPTION = new LocationDataLoreOption(LocationDataOption.CARGO_ID, KEY_INPUT, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});

    public static final LocationDataLoreOption OUTPUT_OPTION = new LocationDataLoreOption(LocationDataOption.CARGO_ID, KEY_OUTPUT, 1, new LinkedHashMap<>() {{
        this.put(VALUE_UNIVERSAL, List.of(UNIVERSAL_LORE));
        this.put(VALUE_STANDALONE, List.of(STANDALONE_LORE));
    }});
}
