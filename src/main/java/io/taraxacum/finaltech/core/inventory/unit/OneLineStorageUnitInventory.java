package io.taraxacum.finaltech.core.inventory.unit;

import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class OneLineStorageUnitInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[0];
    private final int[] inputBorder = new int[0];
    private final int[] outputBorder = new int[0];
    private final int[] contentSlot = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

    public OneLineStorageUnitInventory(@Nonnull AbstractMachine machine) {
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

    @Nonnull
    @Override
    public int[] getInputSlot() {
        return this.contentSlot;
    }

    @Nonnull
    @Override
    public int[] getOutputSlot() {
        return this.contentSlot;
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    protected void initSelf() {

    }
}
