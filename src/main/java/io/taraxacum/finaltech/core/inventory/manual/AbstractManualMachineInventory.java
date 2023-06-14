package io.taraxacum.finaltech.core.inventory.manual;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public abstract class AbstractManualMachineInventory extends AbstractMachineInventory {

    protected AbstractManualMachineInventory(@Nonnull SlimefunItem slimefunItem) {
        super(slimefunItem);
    }

    public abstract void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location);
}
