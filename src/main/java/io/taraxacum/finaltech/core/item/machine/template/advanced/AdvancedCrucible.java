package io.taraxacum.finaltech.core.item.machine.template.advanced;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.util.RecipeUtil;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class AdvancedCrucible extends AbstractAdvanceMachine {
    public AdvancedCrucible(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerRecipeBySlimefunId(this, SlimefunItems.ELECTRIFIED_CRUCIBLE.getItemId());
    }
}
