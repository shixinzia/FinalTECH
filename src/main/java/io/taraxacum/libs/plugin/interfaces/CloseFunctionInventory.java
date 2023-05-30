package io.taraxacum.libs.plugin.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * This inventory template may do some function while being closed.
 * @author Final_ROOT
 */
public interface CloseFunctionInventory extends InventoryTemplate {

    void onClose(@Nonnull Player player, @Nonnull Location location, @Nonnull Inventory inventory);
}
