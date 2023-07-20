package io.taraxacum.finaltech.core.item.machine.tower;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractTower extends AbstractMachine {
    public AbstractTower(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }
}
