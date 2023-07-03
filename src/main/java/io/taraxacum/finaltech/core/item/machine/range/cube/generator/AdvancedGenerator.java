package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.ConfigUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class AdvancedGenerator extends AbstractCubeElectricGenerator {
    private final int energy = ConfigUtil.getOrDefaultItemSetting(4, this, "energy");
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");

    public AdvancedGenerator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected int getEnergy() {
        return this.energy;
    }

    @Override
    protected int getRange() {
        return this.range;
    }
}
