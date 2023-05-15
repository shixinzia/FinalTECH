package io.taraxacum.libs.slimefun.dto;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.Location;

import javax.annotation.Nonnull;

public class LocationDatabaseData extends SlimefunLocationData {
    private final SlimefunBlockData slimefunBlockData;

    public LocationDatabaseData(@Nonnull Location location, @Nonnull SlimefunBlockData slimefunBlockData) {
        super(location, slimefunBlockData.getSfId(), SlimefunItem.getById(slimefunBlockData.getSfId()));
        this.slimefunBlockData = slimefunBlockData;
    }

    public SlimefunBlockData getSlimefunBlockData() {
        return slimefunBlockData;
    }
}
