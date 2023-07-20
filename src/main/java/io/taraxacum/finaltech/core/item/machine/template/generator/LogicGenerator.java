package io.taraxacum.finaltech.core.item.machine.template.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class LogicGenerator extends AbstractGeneratorMachine {
    public LogicGenerator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    void registerRandomOutputRecipes() {
        this.registerRecipe(FinalTechItemStacks.LOGIC_FALSE);
        this.registerRecipe(FinalTechItemStacks.LOGIC_TRUE);
    }
}
