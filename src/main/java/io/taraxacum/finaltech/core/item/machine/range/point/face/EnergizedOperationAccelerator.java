package io.taraxacum.finaltech.core.item.machine.range.point.face;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.util.ConfigUtil;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 */
public class EnergizedOperationAccelerator extends AbstractOperationAccelerator {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(20000000, this, "capacity");
    private final int baseEfficiency = ConfigUtil.getOrDefaultItemSetting(1, this, "base-efficiency");
    private final int randomEfficiency = ConfigUtil.getOrDefaultItemSetting(1, this, "random-efficiency");

    public EnergizedOperationAccelerator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Nonnull
    @Override
    Set<String> getNotAllowedId() {
        return this.notAllowedId;
    }

    @Override
    int getBaseEfficiency() {
        return this.baseEfficiency;
    }

    @Override
    int getRandomEfficiency() {
        return this.randomEfficiency;
    }
}
