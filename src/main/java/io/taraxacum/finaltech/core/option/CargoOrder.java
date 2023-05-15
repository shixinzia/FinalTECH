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
public final class CargoOrder {
    public static final String KEY = "bco";

    public static final String VALUE_POSITIVE = "p";
    public static final String VALUE_REVERSE = "r";

    public static final ItemStack POSITIVE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTech.getLanguageString("option", "CARGO_ORDER", "positive", "name"), FinalTech.getLanguageStringArray("option", "CARGO_ORDER", "positive", "lore"));
    public static final ItemStack REVERSE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTech.getLanguageString("option", "CARGO_ORDER", "reverse", "name"), FinalTech.getLanguageStringArray("option", "CARGO_ORDER", "reverse", "lore"));

    public static final LocationDataIconOption OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_POSITIVE, POSITIVE_ICON);
        this.put(VALUE_REVERSE, REVERSE_ICON);
    }});
}
