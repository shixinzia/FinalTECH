package io.taraxacum.finaltech.core.menu.machine;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.option.Icon;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class DustGeneratorMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 25, 26, 27, 28, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    private static final int[] INPUT_BORDER = new int[] {11, 12, 13, 14, 15, 20, 24, 29, 30, 31, 32, 33};
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {21, 22, 23};
    private static final int[] OUTPUT_SLOT = new int[0];
    public static final int STATUS_SLOT = 4;

    public DustGeneratorMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }

    @Override
    public void init() {
        super.init();
        this.addItem(STATUS_SLOT, Icon.STATUS_ICON);
        this.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }
}
