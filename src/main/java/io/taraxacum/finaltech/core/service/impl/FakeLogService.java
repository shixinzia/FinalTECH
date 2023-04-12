package io.taraxacum.finaltech.core.service.impl;

import io.taraxacum.finaltech.core.enums.LogSourceType;
import io.taraxacum.finaltech.core.service.LogService;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * It will do nothing.
 * @author Final_ROOT
 * @since 2.5
 */
public class FakeLogService implements LogService {
    @Override
    public void addItem(@Nonnull String itemId, @Nonnull String sourceId, @Nonnull LogSourceType logSourceType, @Nullable Player player, @Nonnull Location location, @Nonnull Plugin plugin) {

    }

    @Override
    public void subItem(@Nonnull String itemId, @Nonnull String sourceId, @Nonnull LogSourceType logSourceType, @Nullable Player player, @Nonnull Location location, @Nonnull Plugin plugin) {

    }

    @Override
    public String getItemAmount(@Nonnull String itemId) {
        return "0";
    }

    @Override
    public boolean verifyEnoughAmount(@Nonnull String itemId, @Nonnull String beConsumedAmount) {
        return true;
    }

    @Override
    public boolean verifyEnoughAmount(@Nonnull String itemId, int beConsumedAmount) {
        return true;
    }
}
