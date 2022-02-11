package io.taraxacum.finaltech.menu;

import io.taraxacum.finaltech.abstractItem.machine.AbstractMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractStandardMachineMenu;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class UnOrderedDustFactoryMenu extends AbstractStandardMachineMenu {
    private static final int[] BORDER = new int[] {3, 4, 5, 12,     14, 21, 22, 23, 30, 31, 32};
    private static final int[] INPUT_BORDER = new int[] {0, 1, 2, 11, 20, 27, 28, 29};
    private static final int[] OUTPUT_BORDER = new int[] {6, 7, 8, 15, 24, 33, 34, 35};
    private static final int[] INPUT_SLOTS = new int[] {9, 10, 18, 19};
    private static final int[] OUTPUT_SLOTS = new int[] {16, 17, 25, 26};

    public UnOrderedDustFactoryMenu(@Nonnull String id, @Nonnull String title, @Nonnull AbstractMachine abstractMachine) {
        super(id, title, abstractMachine);
    }

    @Override
    public int[] getBorder() {
        return BORDER;
    }

    @Override
    public int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    public int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }
}
