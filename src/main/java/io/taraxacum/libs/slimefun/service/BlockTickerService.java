package io.taraxacum.libs.slimefun.service;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.taraxacum.libs.plugin.dto.LocationData;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Final_ROOT
 */
public abstract class BlockTickerService {
    public static final BiConsumer<Runnable, LocationData> DEFAULT_TICKER = (runnable, locationData) -> runnable.run();

    abstract public void run(@Nonnull BlockTicker blockTicker, @Nonnull LocationData locationData) throws Throwable;

    abstract public BlockTicker warp(@Nonnull BlockTicker blockTicker, @Nonnull BiConsumer<Runnable, LocationData> ticker, @Nonnull Function<LocationData, Boolean>[] beforeTicks, @Nonnull Consumer<LocationData>[] afterTicks, @Nonnull Runnable[] extraUniqueTicks);

    abstract public int getGeneratedOutput(@Nonnull EnergyNetProvider energyNetProvider, @Nonnull Location location) throws Throwable;

    abstract public boolean willExplode(@Nonnull EnergyNetProvider energyNetProvider, @Nonnull Location location) throws Throwable;
}
