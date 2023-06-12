package io.taraxacum.finaltech.core.option;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.slimefun.dto.LocationDataIconOption;
import io.taraxacum.libs.slimefun.dto.LocationDataOption;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

public class EnableOption {
    public static final String KEY = "e";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_TRUE = "t";

    public static final ItemStack FALSE_ICON = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTech.getLanguageString("option", "ENABLE", "false", "name"), FinalTech.getLanguageStringArray("option", "ENABLE", "false", "lore"));
    public static final ItemStack TRUE_ICON = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, FinalTech.getLanguageString("option", "ENABLE", "true", "name"), FinalTech.getLanguageStringArray("option", "ENABLE", "true", "lore"));

    public static final LocationDataIconOption OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_FALSE, FALSE_ICON);
        this.put(VALUE_TRUE, TRUE_ICON);
    }});
}
