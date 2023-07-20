package io.taraxacum.finaltech.core.item.unusable.module;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class EnergizedQuantityModule extends AbstractQuantityModule implements RecipeItem {
    private final int baseEfficiency = ConfigUtil.getOrDefaultItemSetting(2, this, "base-efficiency");
    private final int randomEfficiency = ConfigUtil.getOrDefaultItemSetting(4, this, "random-efficiency");

    public EnergizedQuantityModule(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public int getEffect(int itemAmount) {
        return itemAmount * this.baseEfficiency + FinalTech.getRandom().nextInt(1 + itemAmount * this.randomEfficiency);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.baseEfficiency),
                String.valueOf(this.randomEfficiency));
    }
}
