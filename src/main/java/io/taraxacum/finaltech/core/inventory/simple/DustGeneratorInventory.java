package io.taraxacum.finaltech.core.inventory.simple;

import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.option.Icon;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class DustGeneratorInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[] {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 25, 26, 27, 28, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    private final int[] inputBorder = new int[] {11, 12, 13, 14, 15, 20, 24, 29, 30, 31, 32, 33};
    private final int[] outputBorder = new int[0];
    private final int[] inputSlot = new int[] {21, 22, 23};
    private final int[] outputSlot = new int[0];

    public final int statusSlot = 4;

    public DustGeneratorInventory(@Nonnull AbstractMachine machine) {
        super(machine);
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
        return 45;
    }

    @Override
    protected void initSelf() {
        this.defaultItemStack.put(this.statusSlot, Icon.STATUS_ICON);
    }
}
