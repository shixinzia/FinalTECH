package io.taraxacum.finaltech.core.inventory.simple;

import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class SimulateClickMachineInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[] {0, 1, 4, 7, 8};
    private final int[] inputBorder = new int[] {2};
    private final int[] outputBorder = new int[] {6};
    private final int[] inputSlot = new int[] {3};
    private final int[] outputSlot = new int[] {5};

    public SimulateClickMachineInventory(@Nonnull AbstractMachine machine) {
        super(machine);
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
