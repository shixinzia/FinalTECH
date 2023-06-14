package io.taraxacum.finaltech.core.inventory.simple;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import io.taraxacum.finaltech.core.option.Icon;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class EtherMinerInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[] {3, 4, 5, 12, 14, 21, 22, 23};
    private final int[] inputBorder = new int[] {0, 1, 2, 9, 11, 18, 19, 20};
    private final int[] outputBorder = new int[] {6, 7, 8, 15, 17, 24, 25, 26};
    private final int[] inputSlot = new int[] {10};
    private final int[] outputSlot = new int[] {16};

    public final int statusSlot = 13;

    public EtherMinerInventory(@Nonnull SlimefunItem slimefunItem) {
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
    public int getSize() {
        return 27;
    }

    @Override
    protected void initSelf() {
        this.defaultItemStack.put(this.statusSlot, Icon.STATUS_ICON);
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }
}
