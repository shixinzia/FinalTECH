package io.taraxacum.finaltech.core.menu.cargo;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.option.*;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.slimefun.util.ChestMenuUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class AdvancedMeshTransferMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[0];
    private static final int[] INPUT_BORDER = new int[] {5, 14, 23};
    private static final int[] OUTPUT_BORDER = new int[] {32, 41, 50};
    private static final int[] INPUT_SLOT = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26};
    private static final int[] OUTPUT_SLOT = new int[] {33, 34, 35, 42, 43, 44, 51, 52, 53};

    private static final int POSITION_NORTH_SLOT = 1;
    private static final int POSITION_EAST_SLOT = 11;
    private static final int POSITION_SOUTH_SLOT = 19;
    private static final int POSITION_WEST_SLOT = 9;
    private static final int POSITION_UP_SLOT = 0;
    private static final int POSITION_DOWN_SLOT = 18;

    private static final int CARGO_FILTER_SLOT = 10;
    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 2;
    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 20;

    private static final int INPUT_CARGO_NUMBER_SUB_SLOT = 3;
    private static final int INPUT_CARGO_NUMBER_MODE_SLOT = 12;
    private static final int INPUT_CARGO_NUMBER_ADD_SLOT = 21;
    private static final int INPUT_SLOT_SEARCH_SIZE_SLOT = 4;
    private static final int INPUT_SLOT_SEARCH_ORDER_SLOT = 13;
    private static final int INPUT_CARGO_LIMIT_SLOT = 22;

    private static final int OUTPUT_CARGO_NUMBER_SUB_SLOT = 30;
    private static final int OUTPUT_CARGO_NUMBER_MODE_SLOT = 39;
    private static final int OUTPUT_CARGO_NUMBER_ADD_SLOT = 48;
    private static final int OUTPUT_SLOT_SEARCH_SIZE_SLOT = 31;
    private static final int OUTPUT_SLOT_SEARCH_ORDER_SLOT = 40;
    private static final int OUTPUT_CARGO_LIMIT_SLOT = 49;

    public static final int[] ITEM_MATCH = new int[] {27, 28, 29, 36, 37, 38, 45, 46, 47};

    public AdvancedMeshTransferMenu(@Nonnull AbstractMachine machine) {
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

        this.addItem(POSITION_NORTH_SLOT, PositionInfo.NORTH_ICON);
        this.addItem(POSITION_EAST_SLOT, PositionInfo.EAST_ICON);
        this.addItem(POSITION_SOUTH_SLOT, PositionInfo.SOUTH_ICON);
        this.addItem(POSITION_WEST_SLOT, PositionInfo.WEST_ICON);
        this.addItem(POSITION_UP_SLOT, PositionInfo.UP_ICON);
        this.addItem(POSITION_DOWN_SLOT, PositionInfo.DOWN_ICON);

        this.addItem(CARGO_FILTER_SLOT, CargoFilter.OPTION.defaultIcon());
        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.MESH_INPUT_OPTION.defaultIcon());
        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.MESH_OUTPUT_OPTION.defaultIcon());

        this.addItem(INPUT_CARGO_NUMBER_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(INPUT_CARGO_NUMBER_MODE_SLOT, CargoNumber.CARGO_NUMBER_ICON);
        this.addItem(INPUT_CARGO_NUMBER_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);
        this.addItem(INPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.INPUT_OPTION.defaultIcon());
        this.addItem(INPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.INPUT_OPTION.defaultIcon());
        this.addItem(INPUT_CARGO_LIMIT_SLOT, CargoLimit.INPUT_OPTION.defaultIcon());

        this.addItem(OUTPUT_CARGO_NUMBER_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(OUTPUT_CARGO_NUMBER_MODE_SLOT, CargoNumber.CARGO_NUMBER_ICON);
        this.addItem(OUTPUT_CARGO_NUMBER_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);
        this.addItem(OUTPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.OUTPUT_OPTION.defaultIcon());
        this.addItem(OUTPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.OUTPUT_OPTION.defaultIcon());
        this.addItem(OUTPUT_CARGO_LIMIT_SLOT, CargoLimit.OUTPUT_OPTION.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Location location = block.getLocation();
        blockMenu.addMenuOpeningHandler(p -> FinalTech.getLocationDataService().setLocationData(location, ConstantTableUtil.CONFIG_UUID, p.getUniqueId().toString()));

        blockMenu.addMenuClickHandler(POSITION_NORTH_SLOT, ChestMenuUtil.warpByConsumer(PositionInfo.NORTH_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(POSITION_EAST_SLOT, ChestMenuUtil.warpByConsumer(PositionInfo.EAST_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(POSITION_SOUTH_SLOT, ChestMenuUtil.warpByConsumer(PositionInfo.SOUTH_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(POSITION_WEST_SLOT, ChestMenuUtil.warpByConsumer(PositionInfo.WEST_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(POSITION_UP_SLOT, ChestMenuUtil.warpByConsumer(PositionInfo.UP_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(POSITION_DOWN_SLOT, ChestMenuUtil.warpByConsumer(PositionInfo.DOWN_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));

        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, ChestMenuUtil.warpByConsumer(CargoFilter.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, ChestMenuUtil.warpByConsumer(BlockSearchMode.MESH_INPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, ChestMenuUtil.warpByConsumer(BlockSearchMode.MESH_OUTPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));

        blockMenu.addMenuClickHandler(INPUT_CARGO_NUMBER_ADD_SLOT, ChestMenuUtil.warpByConsumer(CargoNumber.INPUT_OPTION.getNextHandler(FinalTech.getLocationDataService(), location, INPUT_CARGO_NUMBER_MODE_SLOT, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(INPUT_CARGO_NUMBER_SUB_SLOT, ChestMenuUtil.warpByConsumer(CargoNumber.INPUT_OPTION.getPreviousHandler(FinalTech.getLocationDataService(), location, INPUT_CARGO_NUMBER_MODE_SLOT, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(INPUT_CARGO_NUMBER_MODE_SLOT, ChestMenuUtil.warpByConsumer(CargoNumberMode.INPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(INPUT_SLOT_SEARCH_SIZE_SLOT, ChestMenuUtil.warpByConsumer(SlotSearchSize.INPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(INPUT_SLOT_SEARCH_ORDER_SLOT, ChestMenuUtil.warpByConsumer(SlotSearchOrder.INPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(INPUT_CARGO_LIMIT_SLOT, ChestMenuUtil.warpByConsumer(CargoLimit.INPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));

        blockMenu.addMenuClickHandler(OUTPUT_CARGO_NUMBER_ADD_SLOT, ChestMenuUtil.warpByConsumer(CargoNumber.OUTPUT_OPTION.getNextHandler(FinalTech.getLocationDataService(), location, OUTPUT_CARGO_NUMBER_MODE_SLOT, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(OUTPUT_CARGO_NUMBER_SUB_SLOT, ChestMenuUtil.warpByConsumer(CargoNumber.OUTPUT_OPTION.getPreviousHandler(FinalTech.getLocationDataService(), location, OUTPUT_CARGO_NUMBER_MODE_SLOT, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(OUTPUT_CARGO_NUMBER_MODE_SLOT, ChestMenuUtil.warpByConsumer(CargoNumberMode.OUTPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(OUTPUT_SLOT_SEARCH_SIZE_SLOT, ChestMenuUtil.warpByConsumer(SlotSearchSize.OUTPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(OUTPUT_SLOT_SEARCH_ORDER_SLOT, ChestMenuUtil.warpByConsumer(SlotSearchOrder.OUTPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(OUTPUT_CARGO_LIMIT_SLOT, ChestMenuUtil.warpByConsumer(CargoLimit.OUTPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        PositionInfo.NORTH_OPTION.checkAndUpdateIcon(inventory, POSITION_NORTH_SLOT, FinalTech.getLocationDataService(), location);
        PositionInfo.EAST_OPTION.checkAndUpdateIcon(inventory, POSITION_EAST_SLOT, FinalTech.getLocationDataService(), location);
        PositionInfo.SOUTH_OPTION.checkAndUpdateIcon(inventory, POSITION_SOUTH_SLOT, FinalTech.getLocationDataService(), location);
        PositionInfo.WEST_OPTION.checkAndUpdateIcon(inventory, POSITION_WEST_SLOT, FinalTech.getLocationDataService(), location);
        PositionInfo.UP_OPTION.checkAndUpdateIcon(inventory, POSITION_UP_SLOT, FinalTech.getLocationDataService(), location);
        PositionInfo.DOWN_OPTION.checkAndUpdateIcon(inventory, POSITION_DOWN_SLOT, FinalTech.getLocationDataService(), location);

        CargoFilter.OPTION.checkAndUpdateIcon(inventory, CARGO_FILTER_SLOT, FinalTech.getLocationDataService(), location);
        BlockSearchMode.MESH_INPUT_OPTION.checkAndUpdateIcon(inventory, INPUT_BLOCK_SEARCH_MODE_SLOT, FinalTech.getLocationDataService(), location);
        BlockSearchMode.MESH_OUTPUT_OPTION.checkAndUpdateIcon(inventory, OUTPUT_BLOCK_SEARCH_MODE_SLOT, FinalTech.getLocationDataService(), location);

        CargoNumber.INPUT_OPTION.checkAndUpdateIcon(inventory, INPUT_CARGO_NUMBER_MODE_SLOT, FinalTech.getLocationDataService(), location);
        CargoNumberMode.INPUT_OPTION.checkAndUpdateIcon(inventory, INPUT_CARGO_NUMBER_MODE_SLOT, FinalTech.getLocationDataService(), location);
        SlotSearchSize.INPUT_OPTION.checkAndUpdateIcon(inventory, INPUT_SLOT_SEARCH_SIZE_SLOT, FinalTech.getLocationDataService(), location);
        SlotSearchOrder.INPUT_OPTION.checkAndUpdateIcon(inventory, INPUT_SLOT_SEARCH_ORDER_SLOT, FinalTech.getLocationDataService(), location);
        CargoLimit.INPUT_OPTION.checkAndUpdateIcon(inventory, INPUT_CARGO_LIMIT_SLOT, FinalTech.getLocationDataService(), location);

        CargoNumber.OUTPUT_OPTION.checkAndUpdateIcon(inventory, OUTPUT_CARGO_NUMBER_MODE_SLOT, FinalTech.getLocationDataService(), location);
        CargoNumberMode.OUTPUT_OPTION.checkAndUpdateIcon(inventory, OUTPUT_CARGO_NUMBER_MODE_SLOT, FinalTech.getLocationDataService(), location);
        SlotSearchSize.OUTPUT_OPTION.checkAndUpdateIcon(inventory, OUTPUT_SLOT_SEARCH_SIZE_SLOT, FinalTech.getLocationDataService(), location);
        SlotSearchOrder.OUTPUT_OPTION.checkAndUpdateIcon(inventory, OUTPUT_SLOT_SEARCH_ORDER_SLOT, FinalTech.getLocationDataService(), location);
        CargoLimit.OUTPUT_OPTION.checkAndUpdateIcon(inventory, OUTPUT_CARGO_LIMIT_SLOT, FinalTech.getLocationDataService(), location);
    }
}
