package io.taraxacum.finaltech.core.option;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.slimefun.dto.LocationDataIconOption;
import io.taraxacum.libs.slimefun.dto.LocationDataOption;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 */
public final class CargoFilter {
    public static final String KEY = "cf";

    public static final String VALUE_BLACK = "b";
    public static final String VALUE_WHITE = "w";

    public static final ItemStack FILTER_MODE_BLACK_ICON = new CustomItemStack(Material.BLACK_WOOL, FinalTech.getLanguageString("option", "CARGO_FILTER", "black-filter-mode", "name"), FinalTech.getLanguageStringArray("option", "CARGO_FILTER", "black-filter-mode", "lore"));
    public static final ItemStack FILTER_MODE_WHITE_ICON = new CustomItemStack(Material.WHITE_WOOL, FinalTech.getLanguageString("option", "CARGO_FILTER", "white-filter-mode", "name"), FinalTech.getLanguageStringArray("option", "CARGO_FILTER", "white-filter-mode", "lore"));

    public static final LocationDataIconOption OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_BLACK, FILTER_MODE_BLACK_ICON);
        this.put(VALUE_WHITE, FILTER_MODE_WHITE_ICON);
    }});
}
