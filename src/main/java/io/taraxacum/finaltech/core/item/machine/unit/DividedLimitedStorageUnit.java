package io.taraxacum.finaltech.core.item.machine.unit;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.unit.DividedLimitStorageUnitInventory;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class DividedLimitedStorageUnit extends AbstractStorageUnit implements RecipeItem {
    public DividedLimitedStorageUnit(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        return new  DividedLimitStorageUnitInventory(this);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this, String.valueOf(MachineUtil.calMachineSlotSize(this)),
                String.valueOf(this.getInputSlot().length),
                String.valueOf(this.getOutputSlot().length));
    }
}
