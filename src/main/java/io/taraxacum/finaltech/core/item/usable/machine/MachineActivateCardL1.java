package io.taraxacum.finaltech.core.item.usable.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MachineActivateCardL1 extends AbstractMachineActivateCard implements RecipeItem {
    private final int times = ConfigUtil.getOrDefaultItemSetting(1, this, "times");
    private final double energy = ConfigUtil.getOrDefaultItemSetting(16.04, this, "energy");

    public MachineActivateCardL1(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected int times() {
        return times;
    }

    @Override
    protected double energy() {
        return energy;
    }

    @Override
    protected boolean consume() {
        return true;
    }

    @Override
    protected boolean conditionMatch(@Nonnull Player player) {
        return true;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.times()),
                String.valueOf((int)(Math.floor(this.energy()))),
                String.format("%.2f", (this.energy() - Math.floor(this.energy())) * 100));
    }
}
