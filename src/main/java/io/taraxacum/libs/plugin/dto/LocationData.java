package io.taraxacum.libs.plugin.dto;

import org.bukkit.Location;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class LocationData {
    protected Location location;

    public LocationData(@Nonnull Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void cloneLocation() {
        this.location = this.location.clone();
    }
}
