package io.taraxacum.finaltech.core.item.usable.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MachineActivateCardL2 extends AbstractMachineActivateCard implements RecipeItem {
    private final int times = ConfigUtil.getOrDefaultItemSetting(16, this, "times");
    private final double energy = ConfigUtil.getOrDefaultItemSetting(1024.16, this, "energy");

    public MachineActivateCardL2(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
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
        if (player.getHealth() > 1 && player.getTotalExperience() > 1) {
            player.setHealth(player.getHealth() - 1);
            player.setTotalExperience(player.getTotalExperience() - 1);
            return true;
        }
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.times()),
                String.valueOf((int)(Math.floor(this.energy()))),
                String.format("%.2f", (this.energy() - Math.floor(this.energy())) * 100));
    }
}
