package io.taraxacum.finaltech.core.inventory.simple;

import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class OrderedDustFactoryStoneInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[] {7, 8, 16, 17, 25, 26, 34, 35, 43, 44, 52, 53};
    private final int[] inputBorder = new int[] {6, 15, 24, 33, 42, 51};
    private final int[] outputBorder = new int[0];
    private final int[] contentSlot = new int[] {0, 1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 14, 18, 19, 20, 21, 22, 23, 27, 28, 29, 30, 31, 32, 36, 37, 38, 39, 40, 41, 45, 46, 47, 48, 49, 50};

    public OrderedDustFactoryStoneInventory(@Nonnull AbstractMachine machine) {
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
    public int[] requestSlots(@Nonnull RequestType requestType) {
        int[] intArray = JavaUtil.generateRandomInts(this.contentSlot.length / 2);
        int[] result = new int[intArray.length];
        for(int i = 0; i < intArray.length; i++) {
            result[i] = this.contentSlot[intArray[i]];
        }
        return result;
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
        return 54;
    }
}
