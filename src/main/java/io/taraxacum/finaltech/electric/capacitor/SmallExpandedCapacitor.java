package io.taraxacum.finaltech.electric.capacitor;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.abstractItem.machine.AbstractExpandedElectricCapacitor;
import org.bukkit.inventory.ItemStack;

public class SmallExpandedCapacitor extends AbstractExpandedElectricCapacitor {
    public static final int CAPACITY = 131072;
    public static final int STACK = 4;
    public SmallExpandedCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return CAPACITY * 2;
    }

    @Override
    public int getMaxStack() {
        return STACK - 2;
    }
}
