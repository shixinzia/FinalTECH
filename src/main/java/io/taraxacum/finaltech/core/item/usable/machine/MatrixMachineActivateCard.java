package io.taraxacum.finaltech.core.item.usable.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class MatrixMachineActivateCard extends AbstractMachineActivateCard implements RecipeItem {
    private final int times = ConfigUtil.getOrDefaultItemSetting(10800, this, "times");
    private final double energy = ConfigUtil.getOrDefaultItemSetting(Integer.MAX_VALUE / 2 + 0.5, this, "energy");

    public MatrixMachineActivateCard(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
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
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 1));
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);
        player.setSaturation(0);
        player.leaveVehicle();
        player.setFlying(false);
        player.setVelocity(new Vector(0, 0, 0));
        GameMode gameMode = player.getGameMode();
        player.setGameMode(GameMode.SURVIVAL);
        player.setInvulnerable(false);
        for (int i = 0; i < 48; i++) {
            player.setHealth(0);
            if (player.isDead()) {
                player.setGameMode(gameMode);
                return true;
            }
            player.damage(player.getMaxHealth());
            if (player.isDead()) {
                player.setGameMode(gameMode);
                return true;
            }
            player.damage(Integer.MAX_VALUE / 48);
            if (player.isDead()) {
                player.setGameMode(gameMode);
                return true;
            }
        }
        player.setGameMode(gameMode);
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
