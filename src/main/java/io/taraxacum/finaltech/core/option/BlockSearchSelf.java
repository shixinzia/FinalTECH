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
public final class BlockSearchSelf {
    public static final String KEY = "bss";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_START = "b";
    public static final String VALUE_END = "l";

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.MINECART, FinalTech.getLanguageString("option", "BLOCK_SEARCH_SELF", "false", "name"), FinalTech.getLanguageStringArray("option", "BLOCK_SEARCH_SELF", "false", "lore"));
    public static final ItemStack START_ICON = new CustomItemStack(Material.CHEST_MINECART, FinalTech.getLanguageString("option", "BLOCK_SEARCH_SELF", "start", "name"), FinalTech.getLanguageStringArray("option", "BLOCK_SEARCH_SELF", "start", "lore"));
    public static final ItemStack END_ICON = new CustomItemStack(Material.CHEST_MINECART, FinalTech.getLanguageString("option", "BLOCK_SEARCH_SELF", "end", "name"), FinalTech.getLanguageStringArray("option", "BLOCK_SEARCH_SELF", "end", "lore"));

    public static final LocationDataIconOption OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FALSE, FALSE_ICON);
        this.put(VALUE_START, START_ICON);
        this.put(VALUE_END, END_ICON);
    }});
}
