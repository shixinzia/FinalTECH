package io.taraxacum.finaltech.core.item.machine.clicker;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.clicker.ResearchTableInventory;
import io.taraxacum.finaltech.setup.FinalTechRecipeTypes;
import io.taraxacum.finaltech.setup.FinalTechRecipes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ResearchTable extends AbstractClickerMachine {
    public ResearchTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item, FinalTechRecipeTypes.VANILLA_CRAFT_TABLE, FinalTechRecipes.RESEARCH_TABLE);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this.getAddon().getJavaPlugin(), this.getId()), this.getRecipeOutput());
        recipe.shape("ddd","dsd","ccc");
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('s', new RecipeChoice.ExactChoice(Slimefun.getRegistry().getSlimefunGuide(SlimefunGuideMode.SURVIVAL_MODE).getItem()));
        recipe.setIngredient('c', Material.CRYING_OBSIDIAN);
        this.getAddon().getJavaPlugin().getServer().addRecipe(recipe);
        this.setUseableInWorkbench(true);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        return new ResearchTableInventory(this);
    }
}
