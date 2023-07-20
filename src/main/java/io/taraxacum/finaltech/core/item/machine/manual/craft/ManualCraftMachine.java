package io.taraxacum.finaltech.core.item.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class ManualCraftMachine extends AbstractManualCraftMachine {
    public ManualCraftMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {

    }
}
