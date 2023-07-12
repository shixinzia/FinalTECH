package io.taraxacum.finaltech.core.item.machine.template.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.util.RecipeUtil;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class AdvancedOreGrinder extends AbstractAdvanceMachine {
    public AdvancedOreGrinder(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerRecipeBySlimefunId(this, SlimefunItems.ELECTRIC_ORE_GRINDER.getItemId());
    }
}
