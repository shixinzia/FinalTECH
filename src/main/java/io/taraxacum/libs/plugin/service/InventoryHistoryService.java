package io.taraxacum.libs.plugin.service;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public interface InventoryHistoryService {

    void openLast(@Nonnull Player player);

    void openHome(@Nonnull Player player);

    /**
     * Call {@link #canBeAddToLast(Object)} before calling this
     */
    void addToLast(@Nonnull Player player, @Nonnull Object inventoryImpl);

    void removeLast(@Nonnull Player player);

    void removeThenOpenLast(@Nonnull Player player);

    boolean canBeAddToLast(@Nonnull Object inventoryImpl);

    default boolean tryAddToLast(@Nonnull Player player, @Nonnull Object inventoryImpl) {
        if (this.canBeAddToLast(inventoryImpl)) {
            this.addToLast(player, inventoryImpl);
            return true;
        }
        return false;
    }

    default boolean tryReplaceLast(@Nonnull Player player, @Nonnull Object inventoryImpl) {
        if (this.canBeAddToLast(inventoryImpl)) {
            this.removeLast(player);
            this.addToLast(player, inventoryImpl);
            return true;
        }
        return false;
    }
}