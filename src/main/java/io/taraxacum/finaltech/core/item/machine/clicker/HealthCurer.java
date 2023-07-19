package io.taraxacum.finaltech.core.item.machine.clicker;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.clicker.HealthCurerInventory;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HealthCurer extends AbstractClickerMachine implements RecipeItem {
    private final double health = ConfigUtil.getOrDefaultItemSetting(1, this, "health");

    public HealthCurer(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        return new HealthCurerInventory(this);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }

    public double getHealth() {
        return health;
    }
}
