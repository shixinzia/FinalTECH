package io.taraxacum.finaltech.core.interfaces;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A Slimefun item that will update its menu to show some info.
 * @author Final_ROOT
 * @since 2.0
 */
public interface MenuUpdater {

    default void updateInv(@Nonnull Inventory inventory, int slot, @Nonnull SlimefunItem slimefunItem, @Nonnull String... text) {
        ItemStack item = inventory.getItem(slot);
        if(!ItemStackUtil.isItemNull(item)) {
            ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), slimefunItem, text));
        }
    }

    default void updateInv(@Nonnull Inventory inventory, @Nonnull Location location, int slot, @Nonnull SlimefunItem slimefunItem, @Nonnull String... text) {
        ItemStack item = inventory.getItem(slot);
        if(!ItemStackUtil.isItemNull(item)) {
            ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), slimefunItem, text));
        }
    }
}
