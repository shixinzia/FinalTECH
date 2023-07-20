package io.taraxacum.finaltech.core.inventory.clicker;

import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class EffectCurerInventory extends AbstractClickerInventory {
    private final int[] border = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

    public EffectCurerInventory(@Nonnull AbstractClickerMachine abstractClickerMachine) {
        super(abstractClickerMachine);
    }

    @Nonnull
    @Override
    protected int[] getBorder() {
        return this.border;
    }

    @Override
    protected void initSelf() {

    }

    @Override
    protected void openFunction(@Nonnull Player player, @Nonnull Location location, @Nonnull Inventory inventory) {
        player.closeInventory();

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        // TODO sound
        // TODO particle
    }

    @Override
    public int getSize() {
        return 9;
    }
}
