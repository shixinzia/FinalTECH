package io.taraxacum.finaltech.core.item.machine.template.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class DigitalGenerator extends AbstractGeneratorMachine{
    public DigitalGenerator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    void registerRandomOutputRecipes() {
        this.registerRecipe(FinalTechItemStacks.DIGITAL_ONE);
        this.registerRecipe(FinalTechItemStacks.DIGITAL_TWO);
        this.registerRecipe(FinalTechItemStacks.DIGITAL_THREE);
        this.registerRecipe(FinalTechItemStacks.DIGITAL_FOUR);
    }
}
