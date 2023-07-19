package io.taraxacum.finaltech.core.inventory.clicker;

import io.taraxacum.finaltech.core.item.machine.clicker.HealthCurer;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class HealthCurerInventory extends AbstractClickerInventory {
    private final int[] border = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private final double health;

    public HealthCurerInventory(@Nonnull HealthCurer healthCurer) {
        super(healthCurer);
        this.health = healthCurer.getHealth();
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
        InventoryUtil.closeInv(inventory);

        double health = player.getHealth();
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute != null) {
            double maxHealth = attribute.getValue();
            double heal = Math.min(this.health, maxHealth - health);
            if (heal > 0) {
                player.setHealth(health + heal);
            }
        }

        // TODO sound
        // TODO particle
    }

    @Override
    public int getSize() {
        return 9;
    }
}
