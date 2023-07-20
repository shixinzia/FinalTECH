package io.taraxacum.finaltech.core.inventory.unit;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class VoidInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[] {0, 1, 2, 3, 4, 5, 6 ,7 ,8};

    private final int[] inputBorder = new int[0];

    private final int[] outputBorder = new int[0];

    public VoidInventory(@Nonnull SlimefunItem slimefunItem) {
        super(slimefunItem);

    }

    @Nonnull
    @Override
    protected int[] getInputSlot() {
        return new int[0];
    }

    @Nonnull
    @Override
    protected int[] getOutputSlot() {
        return new int[0];
    }

    @Nonnull
    @Override
    protected int[] getBorder() {
        return this.border;
    }

    @Nonnull
    @Override
    protected int[] getInputBorder() {
        return this.inputBorder;
    }

    @Nonnull
    @Override
    protected int[] getOutputBorder() {
        return this.outputBorder;
    }

    @Override
    protected void initSelf() {

    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }
}
