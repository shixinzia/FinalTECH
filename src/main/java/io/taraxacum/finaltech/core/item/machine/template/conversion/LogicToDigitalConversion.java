package io.taraxacum.finaltech.core.item.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class LogicToDigitalConversion extends AbstractConversionMachine {
    public LogicToDigitalConversion(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.DIGITAL_ZERO);
        this.registerRecipe(FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.DIGITAL_ONE);
    }
}
