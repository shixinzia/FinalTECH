package io.taraxacum.finaltech.core.item.machine.range.line;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.core.interfaces.LineMachine;
import io.taraxacum.finaltech.core.item.machine.range.AbstractRangeMachine;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractLineMachine extends AbstractRangeMachine implements LineMachine {
    public AbstractLineMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }
}
