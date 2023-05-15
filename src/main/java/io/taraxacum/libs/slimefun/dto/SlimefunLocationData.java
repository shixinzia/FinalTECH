package io.taraxacum.libs.slimefun.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.libs.plugin.dto.LocationData;
import org.bukkit.Location;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class SlimefunLocationData extends LocationData {
    protected final String id;

    protected final SlimefunItem slimefunItem;

    public SlimefunLocationData(@Nonnull Location location, @Nonnull String id, @Nonnull SlimefunItem slimefunItem) {
        super(location);
        this.id = id;
        this.slimefunItem = slimefunItem;
    }

    public String getId() {
        return id;
    }

    public SlimefunItem getSlimefunItem() {
        return slimefunItem;
    }
}
