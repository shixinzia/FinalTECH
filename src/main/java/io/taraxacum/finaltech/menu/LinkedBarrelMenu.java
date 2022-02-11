package io.taraxacum.finaltech.menu;

import io.taraxacum.finaltech.abstractItem.machine.AbstractMachine;
import io.taraxacum.finaltech.abstractItem.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class LinkedBarrelMenu extends AbstractMachineMenu {
    public static final int[] CONTAIN = new int[]{
            0,  1,  2,  3,  4,  5,  6,  7,  8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    public static final int[] INPUTS = new int[]{
            0,  1,  2,  3,  4,  5,  6,  7,  8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26
    };
    public static final int[] OUTPUTS = new int[]{
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    public LinkedBarrelMenu(@Nonnull String id, @Nonnull String title, AbstractMachine machine) {
        super(id, title, machine);
    }

    @Override
    public int[] getBorder() {
        return new int[0];
    }

    @Override
    public int[] getInputBorder() {
        return new int[0];
    }

    @Override
    public int[] getOutputBorder() {
        return new int[0];
    }

    @Override
    public int[] getInputSlots() {
        return INPUTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUTS;
    }

    @Override
    public void init() {
        super.init();
        this.setSize(54);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
        if(itemTransportFlow == null) {
            return new int[0];
        }
        switch (itemTransportFlow) {
            case INSERT:
                return INPUTS;
            case WITHDRAW:
                return OUTPUTS;
            default:
                return CONTAIN;
        }
    }

    @Override
    protected void updateMenu(BlockMenu menu, Block block) {}
}
