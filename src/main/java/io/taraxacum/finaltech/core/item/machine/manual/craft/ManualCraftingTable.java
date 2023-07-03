package io.taraxacum.finaltech.core.item.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.inventory.*;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 */
public class ManualCraftingTable extends AbstractManualCraftMachine{
    public ManualCraftingTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {
        Iterator<Recipe> recipeIterator = this.getAddon().getJavaPlugin().getServer().recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe next = recipeIterator.next();
            if (next instanceof ShapedRecipe) {
                Set<Map.Entry<Character, ItemStack>> entries = ((ShapedRecipe) next).getIngredientMap().entrySet();
                List<ItemStack> input = new ArrayList<>(entries.size());
                for (Map.Entry<Character, ItemStack> entry : entries) {
                    input.add(entry.getValue());
                }
                this.registerRecipeInCard(ItemStackUtil.getNoNullItemArray(input), new ItemStack[] {next.getResult()});
            } else if (next instanceof ShapelessRecipe) {
                List<ItemStack> ingredientList = ((ShapelessRecipe) next).getIngredientList();
                this.registerRecipeInCard(ItemStackUtil.getNoNullItemArray(ingredientList), new ItemStack[] {next.getResult()});
            }
        }
    }
}
