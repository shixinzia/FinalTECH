package io.taraxacum.libs.slimefun.service.impl;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.dto.LocationBlockStorageData;
import io.taraxacum.libs.slimefun.dto.SlimefunLocationData;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockInfoConfig;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Final_ROOT
 */
public class BlockStorageDataService implements SlimefunLocationDataService {
    private final Map<Location, Long> locationMap = new HashMap<>();

    // 100ms
    private final long interval = 100;

    @Nullable
    @Override
    public String getLocationData(@Nonnull Location location, @Nonnull String key) {
        LocationBlockStorageData locationData = this.getLocationData(location);
        return locationData == null ? null : locationData.getConfig().getString(key);
    }

    @Nullable
    @Override
    public String getLocationData(@Nonnull LocationData locationData, @Nonnull String key) {
        return ((LocationBlockStorageData) locationData).getConfig().getString(key);
    }

    @Nonnull
    @Override
    public Set<String> getKeys(@Nonnull Location location) {
        LocationBlockStorageData locationData = this.getLocationData(location);
        if(locationData != null) {
            Config config = locationData.getConfig();
            return config.getString("id") == null ? new HashSet<>() : config.getKeys();
        } else {
            return new HashSet<>();
        }
    }

    @Nonnull
    @Override
    public Set<String> getKeys(@Nonnull LocationData locationData) {
        Config config = ((LocationBlockStorageData) locationData).getConfig();
        return config.getString("id") == null ? new HashSet<>() : config.getKeys();
    }

    @Override
    public void setLocationData(@Nonnull Location location, @Nonnull String key, @Nullable String value) {
        LocationBlockStorageData locationData = this.getLocationData(location);
        if(locationData != null) {
            if(value != null) {
                Long lastChangeTime = this.locationMap.computeIfAbsent(location, l -> 0L);
                long nowTime = System.currentTimeMillis();
                if(nowTime - this.interval > lastChangeTime) {
                    BlockStorage.addBlockInfo(location, key, value);
                    this.locationMap.put(location, nowTime);
                } else {
                    locationData.getConfig().setValue(key, value);
                }
            } else {
                locationData.getConfig().setValue(key, null);
            }
        }
    }

    @Override
    public void setLocationData(@Nonnull LocationData locationData, @Nonnull String key, @Nullable String value) {
        if(value != null) {
            Long lastChangeTime = this.locationMap.computeIfAbsent(locationData.getLocation(), l -> 0L);
            long nowTime = System.currentTimeMillis();
            if(nowTime - this.interval > lastChangeTime) {
                BlockStorage.addBlockInfo(locationData.getLocation(), key, value);
                this.locationMap.put(locationData.getLocation(), nowTime);
            } else {
                ((LocationBlockStorageData) locationData).getConfig().setValue(key, value);
            }
        } else {
            ((LocationBlockStorageData) locationData).getConfig().setValue(key, null);
        }
    }

    @Override
    public void setLocationData(@Nonnull LocationData locationData) {
        LocationBlockStorageData locationBlockStorageData = (LocationBlockStorageData) locationData;
        BlockStorage.addBlockInfo(locationData.getLocation(), "id", locationBlockStorageData.getId());
        Config config = BlockStorage.getLocationInfo(locationData.getLocation());
        for(String key : locationBlockStorageData.getConfig().getKeys()) {
            config.setValue(key, locationBlockStorageData.getConfig().getString(key));
        }
    }

    @Nullable
    @Override
    public Inventory getInventory(@Nonnull Location location) {
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        return blockMenu == null ? null : blockMenu.toInventory();
    }

    @Nullable
    @Override
    public Inventory getInventory(@Nonnull LocationData locationData) {
        BlockMenu blockMenu = BlockStorage.getInventory(locationData.getLocation());
        return blockMenu == null ? null : blockMenu.toInventory();
    }

    @Nullable
    @Override
    public LocationBlockStorageData getLocationData(@Nonnull Location location) {
        Config config = BlockStorage.getLocationInfo(location);
        String id = config.getString("id");
        if(id == null) {
            return null;
        }
        SlimefunItem slimefunItem = SlimefunItem.getById(id);
        if(slimefunItem == null) {
            return null;
        }
        return new LocationBlockStorageData(location, config, id, slimefunItem);
    }

    /**
     * You should not use this.
     * Use {@link #getOrCreateEmptyLocationData(Location, String)} instead.
     * @throws NullPointerException if there is no location data.
     */
    @Nonnull
    @Override
    public LocationBlockStorageData getOrCreateEmptyLocationData(@Nonnull Location location) {
        LocationBlockStorageData locationData = this.getLocationData(location);
        if(locationData == null) {
            throw new NullPointerException("Can not create empty location data. May be you should use getOrCreateEmptyLocationData(Location, String)}.");
        }
        return locationData;
    }

    /**
     * @throws NullPointerException if the id is lack, or can not find slimefun item with the id.
     */
    @Nonnull
    @Override
    public LocationBlockStorageData createLocationData(@Nonnull Location location, @Nonnull Map<String, Object> map) {
        Map<String, String> configMap = new HashMap<>(map.size());
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            configMap.put(entry.getKey(), entry.getValue().toString());
        }
        BlockInfoConfig config = new BlockInfoConfig(configMap);
        String id = config.getString("id");
        if(id == null) {
            throw new NullPointerException("Can not create location data. The id is needed.");
        }
        SlimefunItem slimefunItem = SlimefunItem.getById(id);
        if(slimefunItem == null) {
            throw new NullPointerException("Can not create location data. Can not find slimefun item with id '" + id + "'.");
        }
        return new LocationBlockStorageData(location, config, id, slimefunItem);
    }

    @Override
    public void clearLocationData(@Nonnull Location location) {
        BlockStorage.clearBlockInfo(location);
    }

    @Nonnull
    @Override
    public LocationBlockStorageData getOrCreateEmptyLocationData(@Nonnull Location location, @Nonnull String slimefunItemId) {
        LocationBlockStorageData locationData = this.getLocationData(location);
        if(locationData == null) {
            SlimefunItem slimefunItem = SlimefunItem.getById(slimefunItemId);
            if(slimefunItem == null) {
                throw new NullPointerException("Can not create location data. Can not find slimefun item with id '" + slimefunItemId + "'.");
            }
            BlockStorage.addBlockInfo(location, "id", slimefunItemId, true);
            Config config = BlockStorage.getLocationInfo(location);
            locationData = new LocationBlockStorageData(location, config, slimefunItemId, slimefunItem);
        }
        return locationData;
    }

    @Nullable
    @Override
    public SlimefunItem getSlimefunItem(@Nonnull Location location) {
        Config config = BlockStorage.getLocationInfo(location);
        String id = config.getString("id");
        return SlimefunItem.getById(id);
    }

    @Nonnull
    @Override
    public SlimefunItem getSlimefunItem(@Nonnull SlimefunLocationData locationData) {
        return locationData.getSlimefunItem();
    }

    @Nullable
    @Override
    public BlockMenu getBlockMenu(@Nonnull Location location) {
        return SlimefunLocationDataService.super.getBlockMenu(location);
    }

    @Nullable
    @Override
    public BlockMenu getBlockMenu(@Nonnull SlimefunLocationData locationData) {
        return BlockStorage.getInventory(locationData.getLocation());
    }
}
