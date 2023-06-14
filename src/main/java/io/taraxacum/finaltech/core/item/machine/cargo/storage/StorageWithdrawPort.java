package io.taraxacum.finaltech.core.item.machine.cargo.storage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.unit.AllOutputStorageUnitInventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class StorageWithdrawPort extends StorageInteractPort {
    public StorageWithdrawPort(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        return new AllOutputStorageUnitInventory(this);
    }
}
