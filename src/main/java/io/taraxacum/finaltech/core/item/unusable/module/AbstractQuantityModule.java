package io.taraxacum.finaltech.core.item.unusable.module;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.core.item.unusable.UnusableSlimefunItem;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractQuantityModule extends UnusableSlimefunItem {
    public AbstractQuantityModule(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    public abstract int getEffect(int itemAmount);
}
