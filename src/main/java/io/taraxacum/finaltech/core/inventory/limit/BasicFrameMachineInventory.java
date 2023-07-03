package io.taraxacum.finaltech.core.inventory.limit;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import javax.annotation.Nonnull;

public class BasicFrameMachineInventory extends AbstractLimitMachineInventory {
    private final int[] border = new int[] {4, 22, 31, 40, 49};
    private final int[] inputBorder = new int[] {3, 12, 21, 30, 39, 48};
    private final int[] outputBorder = new int[] {5, 14, 23, 32, 41, 50};
    private final int[] inputSlot = new int[] {0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47};
    private final int[] outputSlot = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53};

    public BasicFrameMachineInventory(@Nonnull SlimefunItem slimefunItem) {
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
