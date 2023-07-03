package io.taraxacum.finaltech.core.item.machine.template.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class LiquidCardGenerator extends AbstractGeneratorMachine {
    public LiquidCardGenerator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    void registerRandomOutputRecipes() {
        this.registerRecipe(new ItemStack(FinalTechItemStacks.WATER_CARD));
        this.registerRecipe(new ItemStack(FinalTechItemStacks.LAVA_CARD));
        this.registerRecipe(new ItemStack(FinalTechItemStacks.MILK_CARD));
    }
}
