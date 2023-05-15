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
public final class BlockSearchOrder {
    public static final String KEY = "bso";

    public static final String VALUE_POSITIVE = "p";
    public static final String VALUE_REVERSE = "re";
    public static final String VALUE_RANDOM = "ra";

    public static final ItemStack POSITIVE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTech.getLanguageString("option", "BLOCK_SEARCH_ORDER", "positive", "name"), FinalTech.getLanguageStringArray("option", "BLOCK_SEARCH_ORDER", "positive", "lore"));
    public static final ItemStack REVERSE_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTech.getLanguageString("option", "BLOCK_SEARCH_ORDER", "reverse", "name"), FinalTech.getLanguageStringArray("option", "BLOCK_SEARCH_ORDER", "reverse", "lore"));
    public static final ItemStack RANDOM_ICON = new CustomItemStack(Material.CRIMSON_DOOR, FinalTech.getLanguageString("option", "BLOCK_SEARCH_ORDER", "random", "name"), FinalTech.getLanguageStringArray("option", "BLOCK_SEARCH_ORDER", "random", "lore"));

    public static final LocationDataIconOption OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_POSITIVE, POSITIVE_ICON);
        this.put(VALUE_REVERSE, REVERSE_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
}
