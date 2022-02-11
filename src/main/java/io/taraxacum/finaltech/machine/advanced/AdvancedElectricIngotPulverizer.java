package io.taraxacum.finaltech.machine.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.machine.AbstractAdvanceMachine;
import io.taraxacum.finaltech.util.SlimefunUtil;
import org.bukkit.inventory.ItemStack;

public class AdvancedElectricIngotPulverizer extends AbstractAdvanceMachine {
    public AdvancedElectricIngotPulverizer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        SlimefunUtil.registerRecipeBySlimefunId(this, "ELECTRIC_INGOT_PULVERIZER");
    }
}
