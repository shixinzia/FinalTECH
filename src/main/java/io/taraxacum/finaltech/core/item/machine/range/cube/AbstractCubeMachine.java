package io.taraxacum.finaltech.core.item.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.core.interfaces.CubeMachine;
import io.taraxacum.finaltech.core.item.machine.range.AbstractRangeMachine;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractCubeMachine extends AbstractRangeMachine implements CubeMachine {
    public AbstractCubeMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }
}
