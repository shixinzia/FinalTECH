package io.taraxacum.libs.plugin.interfaces;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public interface LogicInventory extends InventoryTemplate {

    @Nonnull
    int[] requestSlots();

    @Nonnull
    default int[] requestSlots(@Nonnull RequestType requestType) {
        return this.requestSlots();
    }

    @Nonnull
    default int[] requestSlots(@Nonnull RequestType requestType, @Nonnull ItemStack itemStack, @Nonnull Inventory inventory, @Nonnull Location location) {
        return this.requestSlots(requestType);
    }

    void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location);

    public static enum RequestType {
        INPUT,
        OUTPUT,
    }
}
