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
public final class SlotSearchSize {
    public static final String KEY = "sss";
    public static final String KEY_INPUT = "sssi";
    public static final String KEY_OUTPUT = "ssso";

    public static final String VALUE_INPUTS_ONLY = "io";
    public static final String VALUE_OUTPUTS_ONLY = "oo";
    public static final String VALUE_INPUTS_AND_OUTPUTS = "iao";
    public static final String VALUE_OUTPUTS_AND_INPUTS = "oai";

    public static final ItemStack INPUTS_ONLY_ICON = new CustomItemStack(Material.SOUL_TORCH, FinalTech.getLanguageString("option", "SLOT_SEARCH_SIZE", "inputs-only", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_SIZE", "inputs-only", "lore"));
    public static final ItemStack OUTPUTS_ONLY_ICON = new CustomItemStack(Material.TORCH, FinalTech.getLanguageString("option", "SLOT_SEARCH_SIZE", "outputs-only", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_SIZE", "outputs-only", "lore"));
    public static final ItemStack INPUTS_AND_OUTPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, FinalTech.getLanguageString("option", "SLOT_SEARCH_SIZE", "inputs-and-outputs", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_SIZE", "inputs-and-outputs", "lore"));
    public static final ItemStack OUTPUTS_AND_INPUTS_ICON = new CustomItemStack(Material.REDSTONE_TORCH, FinalTech.getLanguageString("option", "SLOT_SEARCH_SIZE", "outputs-and-inputs", "name"), FinalTech.getLanguageStringArray("option", "SLOT_SEARCH_SIZE", "outputs-and-inputs", "lore"));

    public static final LocationDataIconOption OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY, new LinkedHashMap<>() {{
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
    }});
    public static final LocationDataIconOption INPUT_OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY_INPUT, new LinkedHashMap<>() {{
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
    }});
    public static final LocationDataIconOption OUTPUT_OPTION = new LocationDataIconOption(LocationDataOption.CARGO_ID, KEY_OUTPUT, new LinkedHashMap<>() {{
        this.put(VALUE_INPUTS_ONLY, INPUTS_ONLY_ICON);
        this.put(VALUE_OUTPUTS_ONLY, OUTPUTS_ONLY_ICON);
        this.put(VALUE_INPUTS_AND_OUTPUTS, INPUTS_AND_OUTPUTS_ICON);
        this.put(VALUE_OUTPUTS_AND_INPUTS, OUTPUTS_AND_INPUTS_ICON);
    }});
}
