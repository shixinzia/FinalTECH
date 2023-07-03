package io.taraxacum.finaltech.core.item.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class ManualAncientAltar extends AbstractManualCraftMachine {
    public ManualAncientAltar(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerRecipeByRecipeType(this, RecipeType.ANCIENT_ALTAR);
    }
}
