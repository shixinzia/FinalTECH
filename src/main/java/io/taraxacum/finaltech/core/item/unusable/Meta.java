package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.libs.plugin.dto.ItemMetaBuilder;
import io.taraxacum.libs.plugin.dto.ItemStackBuilder;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class Meta extends UnusableSlimefunItem implements SimpleValidItem, RecipeItem {
    private final ItemStackBuilder itemStackBuilder;

    public Meta(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType) {
        super(itemGroup, item, recipeType, new ItemStack[0]);

        this.itemStackBuilder = ItemStackBuilder.fromItemStack(this.getItem());
        ItemMetaBuilder itemMetaBuilder = this.itemStackBuilder.getItemMetaBuilder();
        itemMetaBuilder.setData(FinalTech.getItemService().getIdKey(), FinalTech.getItemService().getIdDataType(), this.getId());
    }

    @Nonnull
    @Override
    public ItemStack getValidItem() {
        return this.itemStackBuilder.build();
    }

    @Override
    public boolean verifyItem(@Nonnull ItemStack itemStack) {
        return this.itemStackBuilder.softCompare(itemStack);
    }

    @Override
    public void registerDefaultRecipes() {

    }
}
