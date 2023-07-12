package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.util.ConfigUtil;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class EnergizedGenerator extends AbstractCubeElectricGenerator {
    private final int energy = ConfigUtil.getOrDefaultItemSetting(64, this, "energy");
    private final int range = ConfigUtil.getOrDefaultItemSetting(4, this, "range");

    public EnergizedGenerator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected int getEnergy() {
        return this.energy;
    }

    @Override
    protected int getRange() {
        return this.range;
    }
}
