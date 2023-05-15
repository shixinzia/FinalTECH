package io.taraxacum.finaltech.core.menu.cargo;

import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.option.*;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.util.ChestMenuUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LocationTransferMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 41, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[] OUTPUT_SLOT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[][] LINE_SLOTS = new int[][] {
            {0, 1, 2, 3, 4, 5, 6, 7, 8},
            {9, 10, 11, 12, 13, 14, 15, 16, 17},
            {18, 19, 20, 21, 22, 23, 24, 25, 26}
    };

    private static final int CARGO_MODE_SLOT = 42;
    private static final int CARGO_ORDER_SLOT = 43;

    private static final int LINE1_SLOT = 51;
    private static final int LINE2_SLOT = 52;
    private static final int LINE3_SLOT = 53;

    public static final int LOCATION_RECORDER_SLOT = 40;

    public LocationTransferMenu(@Nonnull AbstractMachine machine) {
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
    public void init() {
        super.init();

        this.addItem(CARGO_MODE_SLOT, CargoMode.OPTION.defaultIcon());
        this.addItem(CARGO_ORDER_SLOT, CargoOrder.OPTION.defaultIcon());

        this.addItem(LINE1_SLOT, SlotSearchLine.L1_ICON);
        this.addItem(LINE2_SLOT, SlotSearchLine.L2_ICON);
        this.addItem(LINE3_SLOT, SlotSearchLine.L3_ICON);
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Location location = block.getLocation();
        blockMenu.addMenuOpeningHandler(p -> FinalTech.getLocationDataService().setLocationData(location, ConstantTableUtil.CONFIG_UUID, p.getUniqueId().toString()));

        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, ChestMenuUtil.warpByConsumer(CargoMode.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(CARGO_ORDER_SLOT, ChestMenuUtil.warpByConsumer(CargoOrder.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));

        blockMenu.addMenuClickHandler(LINE1_SLOT, ChestMenuUtil.warpByConsumer(SlotSearchLine.L1_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(LINE2_SLOT, ChestMenuUtil.warpByConsumer(SlotSearchLine.L2_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(LINE3_SLOT, ChestMenuUtil.warpByConsumer(SlotSearchLine.L3_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, @Nullable ItemTransportFlow flow, ItemStack item) {
        if(menu instanceof BlockMenu blockMenu) {
            LocationData locationData = FinalTech.getLocationDataService().getLocationData(blockMenu.getLocation());
            if(locationData != null) {
                int[] result = new int[0];
                int[] lines;
                if(flow == ItemTransportFlow.INSERT) {
                    lines = SlotSearchLine.getLines(FinalTech.getLocationDataService(), locationData, SlotSearchLine.VALUE_INPUT, SlotSearchLine.VALUE_INPUT_AND_OUTPUT);
                } else if(flow == ItemTransportFlow.WITHDRAW) {
                    lines = SlotSearchLine.getLines(FinalTech.getLocationDataService(), locationData, SlotSearchLine.VALUE_OUTPUT, SlotSearchLine.VALUE_INPUT_AND_OUTPUT);
                } else {
                    return result;
                }
                if(lines.length > 0) {
                    result = LINE_SLOTS[lines[0]];
                    for(int i = 1; i < lines.length; i++) {
                        result = JavaUtil.merge(result, LINE_SLOTS[lines[i]]);
                    }
                }
                return result;
            }
        }
        return super.getSlotsAccessedByItemTransport(menu, flow, item);
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        CargoMode.OPTION.checkAndUpdateIcon(inventory, CARGO_MODE_SLOT, FinalTech.getLocationDataService(), location);
        CargoOrder.OPTION.checkAndUpdateIcon(inventory, CARGO_ORDER_SLOT, FinalTech.getLocationDataService(), location);

        SlotSearchLine.L1_OPTION.checkAndUpdateIcon(inventory, LINE1_SLOT, FinalTech.getLocationDataService(), location);
        SlotSearchLine.L2_OPTION.checkAndUpdateIcon(inventory, LINE2_SLOT, FinalTech.getLocationDataService(), location);
        SlotSearchLine.L3_OPTION.checkAndUpdateIcon(inventory, LINE3_SLOT, FinalTech.getLocationDataService(), location);
    }
}
