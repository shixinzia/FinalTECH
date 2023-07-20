package io.taraxacum.finaltech.core.item.machine.electric.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.util.ConfigUtil;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class LargeExpandedCapacitor extends AbstractExpandedElectricCapacitor {
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(8192, this, "capacity");
    private final int stack = ConfigUtil.getOrDefaultItemSetting(8192, this, "max-stack");

    public LargeExpandedCapacitor(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public int getCapacity() {
        return this.capacity * 2;
    }

    @Override
    public int getMaxStack() {
        return this.stack - 2;
    }
}
