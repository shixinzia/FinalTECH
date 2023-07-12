package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.libs.plugin.dto.ItemMetaBuilder;
import io.taraxacum.libs.plugin.dto.ItemStackBuilder;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class Bit extends UnusableSlimefunItem implements SimpleValidItem, RecipeItem {
    private final ItemStackBuilder itemStackBuilder;

    public Bit(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType) {
        super(itemGroup, item, recipeType, new ItemStack[0]);

        this.itemStackBuilder = ItemStackBuilder.fromItemStack(this.getItem());
        ItemMetaBuilder itemMetaBuilder = this.itemStackBuilder.getItemMetaBuilder();
        itemMetaBuilder.setData(FinalTech.getItemService().getIdKey(), FinalTech.getItemService().getIdDataType(), this.getId());

        String validKey = FinalTech.getConfigManager().getOrDefault(String.valueOf(FinalTech.getRandom().nextDouble(FinalTech.getSeed())), "valid-key", this.getId());
        itemMetaBuilder.setData(new NamespacedKey(FinalTech.getInstance(), this.getId()), PersistentDataType.STRING, validKey);
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
        this.registerDescriptiveRecipe(FinalTechItemStacks.INFO_FACTORY);
    }
}
