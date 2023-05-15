package io.taraxacum.finaltech.core.menu.cargo;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.option.*;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.slimefun.util.ChestMenuUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LineTransferMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {5, 9, 10, 11, 14, 18, 19, 20, 21, 22, 23};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {36, 37, 38, 39, 40, 41, 42, 43, 44};
    private static final int[] OUTPUT_SLOT = new int[] {45, 46, 47, 48, 49, 50, 51, 52, 53};

    private static final int[] SPECIAL_BORDER = new int[] {27, 28, 29, 30, 31, 32, 33, 34, 35};

    private static final int BLOCK_SEARCH_MODE_SLOT = 0;
    private static final int BLOCK_SEARCH_ORDER_SLOT = 1;
    private static final int CARGO_ORDER_SLOT = 2;
    private static final int BLOCK_SEARCH_CYCLE_SLOT = 3;
    private static final int BLOCK_SEARCH_SELF_SLOT = 4;

//    private static final int CARGO_NUMBER_SUB_SLOT = 9;
//    private static final int CARGO_NUMBER_SLOT = 10;
//    private static final int CARGO_NUMBER_ADD_SLOT = 11;
    private static final int CARGO_MODE_SLOT = 12;
    private static final int CARGO_FILTER_SLOT = 13;

//    private static final int INPUT_SLOT_SEARCH_SIZE_SLOT = 18;
//    private static final int INPUT_SLOT_SEARCH_ORDER_SLOT = 19;
//    private static final int CARGO_LIMIT_SLOT = 20;
//    private static final int OUTPUT_SLOT_SEARCH_SIZE_SLOT = 21;
//    private static final int OUTPUT_SLOT_SEARCH_ORDER_SLOT = 22;

    public static final int[] ITEM_MATCH = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};

    public LineTransferMenu(@Nonnull AbstractMachine machine) {
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
        setSize(54);

        for (int slot : SPECIAL_BORDER) {
            this.addItem(slot, Icon.SPECIAL_BORDER_ICON);
            this.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }

        this.addItem(BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.LINE_OPTION.defaultIcon());
        this.addItem(BLOCK_SEARCH_ORDER_SLOT, BlockSearchOrder.OPTION.defaultIcon());
        this.addItem(CARGO_ORDER_SLOT, CargoOrder.OPTION.defaultIcon());
        this.addItem(BLOCK_SEARCH_CYCLE_SLOT, BlockSearchCycle.OPTION.defaultIcon());
        this.addItem(BLOCK_SEARCH_SELF_SLOT, BlockSearchSelf.OPTION.defaultIcon());

        this.addItem(CARGO_MODE_SLOT, CargoMode.OPTION.defaultIcon());
        this.addItem(CARGO_FILTER_SLOT, CargoFilter.OPTION.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Location location = block.getLocation();
        blockMenu.addMenuOpeningHandler(p -> FinalTech.getLocationDataService().setLocationData(location, ConstantTableUtil.CONFIG_UUID, p.getUniqueId().toString()));

        blockMenu.addMenuClickHandler(BLOCK_SEARCH_MODE_SLOT, ChestMenuUtil.warpByConsumer(BlockSearchMode.LINE_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_ORDER_SLOT, ChestMenuUtil.warpByConsumer(BlockSearchOrder.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(CARGO_ORDER_SLOT, ChestMenuUtil.warpByConsumer(CargoOrder.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_CYCLE_SLOT, ChestMenuUtil.warpByConsumer(BlockSearchCycle.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(BLOCK_SEARCH_SELF_SLOT, ChestMenuUtil.warpByConsumer(BlockSearchSelf.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));

        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, ChestMenuUtil.warpByConsumer(CargoMode.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, ChestMenuUtil.warpByConsumer(CargoFilter.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        BlockSearchMode.LINE_OPTION.checkAndUpdateIcon(inventory, BLOCK_SEARCH_MODE_SLOT, FinalTech.getLocationDataService(), location);
        BlockSearchOrder.OPTION.checkAndUpdateIcon(inventory, BLOCK_SEARCH_ORDER_SLOT, FinalTech.getLocationDataService(), location);
        CargoOrder.OPTION.checkAndUpdateIcon(inventory, CARGO_ORDER_SLOT, FinalTech.getLocationDataService(), location);
        BlockSearchCycle.OPTION.checkAndUpdateIcon(inventory, BLOCK_SEARCH_CYCLE_SLOT, FinalTech.getLocationDataService(), location);
        BlockSearchSelf.OPTION.checkAndUpdateIcon(inventory, BLOCK_SEARCH_SELF_SLOT, FinalTech.getLocationDataService(), location);

        CargoMode.OPTION.checkAndUpdateIcon(inventory, CARGO_MODE_SLOT, FinalTech.getLocationDataService(), location);
        CargoFilter.OPTION.checkAndUpdateIcon(inventory, CARGO_FILTER_SLOT, FinalTech.getLocationDataService(), location);
    }
}
