package io.taraxacum.finaltech.core.inventory.limit.lock;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import javax.annotation.Nonnull;

public class BasicMachineInventory extends AbstractLockMachineInventory {
    private final int[] border = new int[] {3, 4, 5, 12, 14, 21, 22, 23, 30, 31, 32, 39, 40, 41, 48, 49, 50};
    private final int[] inputBorder = new int[] {2, 11, 20, 29, 38, 47};
    private final int[] outputBorder = new int[] {6, 15, 24, 33, 42, 51};
    private final int[] inputSlot = new int[] {0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46};
    private final int[] outputSlot = new int[] {7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53};

    public BasicMachineInventory(@Nonnull SlimefunItem slimefunItem) {
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
    public int[] getInputSlot() {
        return this.inputSlot;
    }

    @Override
    public int[] getOutputSlot() {
        return this.outputSlot;
    }

    @Override
    public int getSize() {
        return 54;
    }
}
