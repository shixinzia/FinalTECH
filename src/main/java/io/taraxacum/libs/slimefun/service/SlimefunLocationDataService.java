package io.taraxacum.libs.slimefun.service;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.libs.plugin.interfaces.LocationDataService;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.dto.SlimefunLocationData;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface SlimefunLocationDataService extends LocationDataService {

    @Nonnull
    LocationData getOrCreateEmptyLocationData(@Nonnull Location location, @Nonnull String slimefunItemId);

    @Nullable
    default SlimefunItem getSlimefunItem(@Nonnull Location location) {
        SlimefunLocationData locationData = (SlimefunLocationData) this.getLocationData(location);
        return locationData == null ? null : this.getSlimefunItem(locationData);
    }

    @Nonnull
    SlimefunItem getSlimefunItem(@Nonnull SlimefunLocationData locationData);

    @Nullable
    default BlockMenu getBlockMenu(@Nonnull Location location) {
        SlimefunLocationData locationData = (SlimefunLocationData) this.getLocationData(location);
        return locationData == null ? null : this.getBlockMenu(locationData);
    }

    @Nullable
    BlockMenu getBlockMenu(@Nonnull SlimefunLocationData locationData);
}
