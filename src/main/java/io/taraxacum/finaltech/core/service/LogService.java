package io.taraxacum.finaltech.core.service;

import io.taraxacum.finaltech.core.enums.LogSourceType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Log service is for recording and tracing the amount change of items.
 * It will be used to analysed whether an item duplication bug is exploited.
 * @author Final_ROOT
 * @since 2.5
 */
public interface LogService {

    /**
     * @param itemId the id of item to be generated
     * @param sourceId who generated the item. Could be one player or slimefun item.
     * @param player the player that make the item to be generated.
     * @param location where the item is generated
     * @param plugin who do this?
     */
    void addItem(@Nonnull String itemId, @Nonnull String sourceId, @Nonnull LogSourceType logSourceType, @Nullable Player player, @Nonnull Location location, @Nonnull Plugin plugin);

    /**
     * @param itemId the id of item to be consumed
     * @param sourceId who consumed the item. Could be one player or slimefun item.
     * @param player the player that make the item to be consumed.
     * @param location where the item is consumed
     * @param plugin who do this?
     */
    void subItem(@Nonnull String itemId, @Nonnull String sourceId, @Nonnull LogSourceType logSourceType, @Nullable Player player, @Nonnull Location location, @Nonnull Plugin plugin);

    /**
     * @param itemId the id of item
     * @return how many amount of this item is available
     */
    String getItemAmount(@Nonnull String itemId);

    /**
     * @param itemId the id of item
     * @param beConsumedAmount how many amount of this item will be consumed
     * @return whether there is enough amount of item to be consumed
     */
    boolean verifyEnoughAmount(@Nonnull String itemId, @Nonnull String beConsumedAmount);

    /**
     * @param itemId the id of item
     * @param beConsumedAmount how many amount of this item will be consumed
     * @return whether there is enough amount of item to be consumed
     */
    boolean verifyEnoughAmount(@Nonnull String itemId, int beConsumedAmount);
}
