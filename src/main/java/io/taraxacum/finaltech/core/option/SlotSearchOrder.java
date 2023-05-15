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
 * @since 2.0
 */
public final class SlotSearchOrder {
    public static final String KEY = "sso";
    public static final String KEY_INPUT = "ssoi";
    public static final String KEY_OUTPUT = "ssoo";

    public static final String VALUE_ASCENT = "a";
    public static final String VALUE_DESCEND = "d";
    public static final String VALUE_FIRST_ONLY = "f";
    public static final String VALUE_LAST_ONLY = "l";
    public static final String VALUE_RANDOM = "r";

    public static final ItemStack ASCENT_ICON = new CustomItemStack(Material.BLUE_WOOL, FinalTech.getLanguageString("option", "SLOT_SEARCH_ORDER", "ascent", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_ORDER", "ascent", "lore"));
    public static final ItemStack DESCEND_ICON = new CustomItemStack(Material.ORANGE_WOOL, FinalTech.getLanguageString("option", "SLOT_SEARCH_ORDER", "descend", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_ORDER", "descend", "lore"));
    public static final ItemStack FIRST_ONLY_ICON = new CustomItemStack(Material.BLUE_CARPET, FinalTech.getLanguageString("option", "SLOT_SEARCH_ORDER", "first-only", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_ORDER", "first-only", "lore"));
    public static final ItemStack LAST_ONLY_ICON = new CustomItemStack(Material.ORANGE_CARPET, FinalTech.getLanguageString("option", "SLOT_SEARCH_ORDER", "last-only", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_ORDER", "last-only", "lore"));
    public static final ItemStack RANDOM_ICON = new CustomItemStack(Material.PAPER, FinalTech.getLanguageString("option", "SLOT_SEARCH_ORDER", "random", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_ORDER", "random", "lore"));

    public static final LocationDataIconOption OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
    public static final LocationDataIconOption INPUT_OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
    public static final LocationDataIconOption OUTPUT_OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY_OUTPUT, new LinkedHashMap<>() {{
        this.put(VALUE_ASCENT, ASCENT_ICON);
        this.put(VALUE_DESCEND, DESCEND_ICON);
        this.put(VALUE_FIRST_ONLY, FIRST_ONLY_ICON);
        this.put(VALUE_LAST_ONLY, LAST_ONLY_ICON);
        this.put(VALUE_RANDOM, RANDOM_ICON);
    }});
}
