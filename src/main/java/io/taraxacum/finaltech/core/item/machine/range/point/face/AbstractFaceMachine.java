package io.taraxacum.finaltech.core.item.machine.range.point.face;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.core.item.machine.range.point.AbstractPointMachine;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractFaceMachine extends AbstractPointMachine {
    public AbstractFaceMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Nonnull
    @Override
    public final Location getTargetLocation(@Nonnull Location location, int range) {
        Block block = location.getBlock();
        return block.getRelative(this.getBlockFace()).getLocation();
    }

    @Nonnull
    protected abstract BlockFace getBlockFace();
}
