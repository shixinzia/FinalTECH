package io.taraxacum.finaltech.core.inventory.clicker;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class ResearchTableInventory extends AbstractClickerInventory {
    private final int[] BORDER = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

    public ResearchTableInventory(@Nonnull AbstractClickerMachine abstractClickerMachine) {
        super(abstractClickerMachine);
    }

    @Nonnull
    @Override
    protected int[] getBorder() {
        return this.BORDER;
    }

    @Override
    protected void initSelf() {

    }

    @Override
    protected void openFunction(@Nonnull Player player, @Nonnull Location location, @Nonnull Inventory inventory) {
        InventoryUtil.closeInv(inventory);

        if (player.isSneaking()) {
            FinalTech.getSimpleInventoryHistoryService().openHome(player);
        } else {
            FinalTech.getSimpleInventoryHistoryService().openLast(player);
        }
    }

    @Override
    public int getSize() {
        return 9;
    }
}
