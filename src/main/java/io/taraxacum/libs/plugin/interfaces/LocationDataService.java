package io.taraxacum.libs.plugin.interfaces;

import io.taraxacum.libs.plugin.dto.LocationData;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This service will handle the data related to the location.
 * It is similar to the Slimefun {@link me.mrCookieSlime.Slimefun.api.BlockStorage}.
 * And may be used without Slimefun in the future.
 * @author Final_ROOT
 */
public interface LocationDataService {

    /**
     * It will {@link #getLocationData(Location)} if location data is null.
     */
    @Nullable
    default String getLocationData(@Nonnull Location location, @Nonnull String key) {
        LocationData locationData = this.getLocationData(location);
        return locationData == null ? null : this.getLocationData(locationData, key);
    }

    @Nullable
    String getLocationData(@Nonnull LocationData locationData, @Nonnull String key);

    /**
     * @return empty set instead of null
     */
    @Nonnull
    default Set<String> getKeys(@Nonnull Location location) {
        LocationData locationData = this.getLocationData(location);
        return locationData == null ? new HashSet<>() : this.getKeys(locationData);
    }

    @Nonnull
    Set<String> getKeys(@Nonnull LocationData locationData);

    /**
     * It will {@link #getLocationData(Location)} if location data is null.
     * If location data is still null, it will just return.
     * @param value input null will remove the key.
     */
    default void setLocationData(@Nonnull Location location, @Nonnull String key, @Nullable String value) {
        LocationData locationData = this.getLocationData(location);
        if(locationData != null) {
            this.setLocationData(locationData, key, value);
        }
    }

    void setLocationData(@Nonnull LocationData locationData, @Nonnull String key, @Nullable String value);

    /**
     * It will set or replace the location data.
     */
    void setLocationData(@Nonnull LocationData locationData);

    @Nullable
    Inventory getInventory(@Nonnull LocationData locationData);

    @Nullable
    default Inventory getInventory(@Nonnull Location location) {
        LocationData locationData = this.getLocationData(location);
        return locationData == null ? null : this.getInventory(locationData);
    }

    @Nullable
    LocationData getLocationData(@Nonnull Location location);

    @Nonnull
    LocationData getOrCreateEmptyLocationData(@Nonnull Location location);

    @Nonnull
    LocationData createLocationData(@Nonnull Location location, @Nonnull Map<String, Object> map);

    void clearLocationData(@Nonnull Location location);
}
