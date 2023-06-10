package io.taraxacum.libs.plugin.dto;

import org.bukkit.inventory.Inventory;

/**
 * @author Final_ROOT
 */
public record InvWithSlots(Inventory inventory, int[] slots) {
    public Inventory getInventory() {
        return inventory;
    }

    public int[] getSlots() {
        return slots;
    }
}
