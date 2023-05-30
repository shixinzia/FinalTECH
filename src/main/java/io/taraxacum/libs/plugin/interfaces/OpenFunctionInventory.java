package io.taraxacum.libs.plugin.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public interface OpenFunctionInventory extends InventoryTemplate {

    void onOpen(@Nonnull Player player, @Nonnull Location location, @Nonnull Inventory inventory);
}
