package io.taraxacum.finaltech.core.inventory.unit;

import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.option.Icon;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class StatusL2Inventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[] {0, 1, 2, 3, 5 ,6 ,7, 8, 9, 10, 11, 12, 14, 15, 16, 17};
    private final int[] inputBorder = new int[0];
    private final int[] outputBorder = new int[0];
    private final int[] contentSlot = new int[] {13};

    public final int statusSlot = 4;

    public StatusL2Inventory(@Nonnull AbstractMachine machine) {
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
        return 18;
    }

    @Override
    protected void initSelf() {
        this.defaultItemStack.put(this.statusSlot, Icon.STATUS_ICON);
    }
}
