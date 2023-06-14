package io.taraxacum.finaltech.core.inventory.simple;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class EnergyOutputTableInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[0];
    private final int[] inputBorder = new int[] {0};
    private final int[] outputBorder = new int[] {2, 8};
    private final int[] inputSlot = new int[] {1};
    private final int[] outputSlot = new int[] {3, 4, 5, 6, 7};

    public EnergyOutputTableInventory(@Nonnull SlimefunItem slimefunItem) {
        super(slimefunItem);
    }

    @Override
    protected int[] getBorder() {
        return this.border;
    }

    @Override
    protected int[] getInputBorder() {
        return this.inputBorder;
    }

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
