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
public final class CargoMode {
    public static final String KEY = "cm";

    public static final String VALUE_INPUT_MAIN = "im";
    public static final String VALUE_OUTPUT_MAIN = "om";
    public static final String VALUE_STRONG_SYMMETRY = "ss";
    public static final String VALUE_WEAK_SYMMETRY = "ws";

    private static final ItemStack INPUT_MAIN_ICON = new CustomItemStack(Material.WATER_BUCKET, FinalTech.getLanguageString("option", "CARGO_MODE", "input-main", "name"), FinalTech.getLanguageStringArray("option", "CARGO_MODE", "input-main", "lore"));
    private static final ItemStack OUTPUT_MAIN_ICON = new CustomItemStack(Material.LAVA_BUCKET, FinalTech.getLanguageString("option", "CARGO_MODE", "output-main", "name"), FinalTech.getLanguageStringArray("option", "CARGO_MODE", "output-main", "lore"));
    private static final ItemStack STRONG_SYMMETRY_ICON = new CustomItemStack(Material.MILK_BUCKET, FinalTech.getLanguageString("option", "CARGO_MODE", "strong-symmetry", "name"), FinalTech.getLanguageStringArray("option", "CARGO_MODE", "strong-symmetry", "lore"));
    private static final ItemStack WEAK_SYMMETRY_ICON = new CustomItemStack(Material.MILK_BUCKET, FinalTech.getLanguageString("option", "CARGO_MODE", "weak-symmetry", "name"), FinalTech.getLanguageStringArray("option", "CARGO_MODE", "weak-symmetry", "lore"));

    public static final LocationDataIconOption OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_STRONG_SYMMETRY, STRONG_SYMMETRY_ICON);
        this.put(VALUE_WEAK_SYMMETRY, WEAK_SYMMETRY_ICON);
        this.put(VALUE_INPUT_MAIN, INPUT_MAIN_ICON);
        this.put(VALUE_OUTPUT_MAIN, OUTPUT_MAIN_ICON);
    }});
}
