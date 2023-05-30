package io.taraxacum.libs.plugin.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public interface InventoryTemplate {
    Consumer<InventoryClickEvent> CANCEL_CLICK_CONSUMER = inventoryClickEvent -> inventoryClickEvent.setCancelled(true);

    @Nonnull
    String getId();

    @Nonnull
    String getName();

    int getSize();

    @Nonnull
    Map<Integer, ItemStack> getDefaultItemStacks();

    @Nullable
    Consumer<InventoryClickEvent> onClick(@Nonnull Location location, int slot);

    boolean canOpen(@Nonnull Player player, @Nonnull Location location);

    default InventoryTemplate init() {
        return this;
    }

    default InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }
}
