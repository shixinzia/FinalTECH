package io.taraxacum.finaltech.core.item.machine.template.basic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class BasicLogicFactory extends AbstractBasicMachine {
    public BasicLogicFactory(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(new ItemStack[] {FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.BUG}, new ItemStack[]{FinalTechItemStacks.ENTROPY});
    }
}
