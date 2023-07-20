package io.taraxacum.finaltech.core.item.machine.logic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class LogicSimilarComparator extends AbstractLogicComparator implements RecipeItem {
    public LogicSimilarComparator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected boolean preCompare(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        return !ItemStackUtil.isItemNull(item1) && !ItemStackUtil.isItemNull(item2);
    }

    @Override
    protected boolean compare(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        return ItemStackUtil.isItemSimilar(item1, item2);
    }

    @Nonnull
    @Override
    protected ItemStack resultTrue() {
        return FinalTechItemStacks.LOGIC_TRUE;
    }

    @Nonnull
    @Override
    protected ItemStack resultFalse() {
        return FinalTechItemStacks.LOGIC_FALSE;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
