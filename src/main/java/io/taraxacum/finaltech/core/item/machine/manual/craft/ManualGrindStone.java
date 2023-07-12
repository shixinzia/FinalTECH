package io.taraxacum.finaltech.core.item.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.util.RecipeUtil;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class ManualGrindStone extends AbstractManualCraftMachine {
    public ManualGrindStone(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {
//        RecipeUtil.registerRecipeByRecipeType(this, RecipeType.GRIND_STONE);
        RecipeUtil.registerRecipeBySlimefunId(this, SlimefunItems.GRIND_STONE.getItemId());
    }
}
