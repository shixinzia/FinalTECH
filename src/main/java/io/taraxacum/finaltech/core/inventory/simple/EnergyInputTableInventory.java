package io.taraxacum.finaltech.core.inventory.simple;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class EnergyInputTableInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[0];
    private final int[] inputBorder = new int[] {0, 6};
    private final int[] outputBorder = new int[] {8};
    private final int[] inputSlot = new int[] {1, 2, 3, 4, 5};
    private final int[] outputSlot = new int[] {7};

    public EnergyInputTableInventory(@Nonnull SlimefunItem slimefunItem) {
        super(slimefunItem);
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

    @Nonnull
    @Override
    public int[] getInputSlot() {
        return this.inputSlot;
    }

    @Nonnull
    @Override
    public int[] getOutputSlot() {
        return this.outputSlot;
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }

    @Override
    public int getSize() {
        return 9;
    }
}
