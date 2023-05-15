package io.taraxacum.finaltech.core.option;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.slimefun.dto.LocationDataLoreOption;
import io.taraxacum.libs.slimefun.dto.LocationDataOption;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;

/**
 * @author Final_ROOT
 */
public final class CargoNumber {
    public static final String KEY = "cb";
    public static final String KEY_INPUT = "cbi";
    public static final String KEY_OUTPUT = "cbo";

    public static final ItemStack CARGO_NUMBER_ICON = new CustomItemStack(Material.TARGET, FinalTech.getLanguageString("option", "CARGO_NUMBER", "icon", "name"), FinalTech.getLanguageStringArray("option", "CARGO_NUMBER", "icon", "lore"));
    public static final ItemStack CARGO_NUMBER_ADD_ICON = new CustomItemStack(Material.GREEN_CONCRETE, FinalTech.getLanguageString("option", "CARGO_NUMBER", "add-icon", "name"), FinalTech.getLanguageStringArray("option", "CARGO_NUMBER", "add-icon", "lore"));
    public static final ItemStack CARGO_NUMBER_SUB_ICON = new CustomItemStack(Material.RED_CONCRETE, FinalTech.getLanguageString("option", "CARGO_NUMBER", "sub-icon", "name"), FinalTech.getLanguageStringArray("option", "CARGO_NUMBER", "sub-icon", "lore"));

    public static final LocationDataLoreOption OPTION = new LocationDataLoreOption(LocationDataOption.CARGO_ID, KEY , 0, new LinkedHashMap<>() {{
        this.put("0", FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("option", "CARGO_NUMBER", "icon", "lore0")));
        for (int i = 1; i <= ConstantTableUtil.ITEM_MAX_STACK * 9; i++) {
            this.put(String.valueOf(i), FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("option", "CARGO_NUMBER", "icon", "lore"), String.valueOf(i)));
        }
    }}) {

        @Nonnull
        @Override
        public String defaultValue() {
            return String.valueOf(ConstantTableUtil.ITEM_MAX_STACK);
        }

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickType clickType) {
            if (!clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.nextOrDefaultValue(value);
            } else if (clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.offsetOrDefaultValue(value, ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return this.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickType clickType) {
            if (!clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.previousOrDefaultValue(value);
            } else if (clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.offsetOrDefaultValue(value, -ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return this.offsetOrDefaultValue(value, -8);
            }
        }
    };
    public static final LocationDataLoreOption INPUT_OPTION = new LocationDataLoreOption(LocationDataOption.CARGO_ID, KEY_INPUT, 0, new LinkedHashMap<>() {{
        this.put("0", FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("option", "CARGO_NUMBER", "icon", "lore0")));
        for (int i = 1; i <= ConstantTableUtil.ITEM_MAX_STACK * 9; i++) {
            this.put(String.valueOf(i), FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("option", "CARGO_NUMBER", "icon", "lore"), String.valueOf(i)));
        }
    }}) {

        @Nonnull
        @Override
        public String defaultValue() {
            return String.valueOf(ConstantTableUtil.ITEM_MAX_STACK);
        }

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickType clickType) {
            if (!clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.nextOrDefaultValue(value);
            } else if (clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.offsetOrDefaultValue(value, ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return this.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickType clickType) {
            if (!clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.previousOrDefaultValue(value);
            } else if (clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.offsetOrDefaultValue(value, -ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return this.offsetOrDefaultValue(value, -8);
            }
        }
    };
    public static final LocationDataLoreOption OUTPUT_OPTION = new LocationDataLoreOption(LocationDataOption.CARGO_ID, KEY_OUTPUT, 0, new LinkedHashMap<>() {{
        this.put("0", FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("option", "CARGO_NUMBER", "icon", "lore0")));
        for (int i = 1; i <= ConstantTableUtil.ITEM_MAX_STACK * 9; i++) {
            this.put(String.valueOf(i), FinalTech.getLanguageManager().replaceStringList(FinalTech.getLanguageStringList("option", "CARGO_NUMBER", "icon", "lore"), String.valueOf(i)));
        }
    }}) {

        @Nonnull
        @Override
        public String defaultValue() {
            return String.valueOf(ConstantTableUtil.ITEM_MAX_STACK);
        }

        @Nonnull
        @Override
        public String clickNextValue(@Nullable String value, @Nonnull ClickType clickType) {
            if (!clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.nextOrDefaultValue(value);
            } else if (clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.offsetOrDefaultValue(value, ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return this.offsetOrDefaultValue(value, 8);
            }
        }

        @Nonnull
        @Override
        public String clickPreviousValue(@Nullable String value, @Nonnull ClickType clickType) {
            if (!clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.previousOrDefaultValue(value);
            } else if (clickType.isShiftClick() && !clickType.isRightClick()) {
                return this.offsetOrDefaultValue(value, -ConstantTableUtil.ITEM_MAX_STACK);
            } else {
                return this.offsetOrDefaultValue(value, -8);
            }
        }
    };
}
