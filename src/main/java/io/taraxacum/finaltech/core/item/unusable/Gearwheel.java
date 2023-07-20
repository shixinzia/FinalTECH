package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechRecipes;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class Gearwheel extends UnusableSlimefunItem implements RecipeItem {
    public Gearwheel(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull ItemStack output) {
        super(itemGroup, item, RecipeType.ENHANCED_CRAFTING_TABLE, FinalTechRecipes.GEARWHEEL, output);
    }

    @Override
    public void registerDefaultRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this.getAddon().getJavaPlugin(), this.getId()), this.getRecipeOutput());
        recipe.shape("ggg","ddd","aaa");
        recipe.setIngredient('g', Material.POLISHED_GRANITE);
        recipe.setIngredient('d', Material.POLISHED_DIORITE);
        recipe.setIngredient('a', Material.POLISHED_ANDESITE);
        this.getAddon().getJavaPlugin().getServer().addRecipe(recipe);
        this.setUseableInWorkbench(true);

        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
