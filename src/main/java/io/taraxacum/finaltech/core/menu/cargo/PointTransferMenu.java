package io.taraxacum.finaltech.core.menu.cargo;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.option.*;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.slimefun.util.ChestMenuUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class PointTransferMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 15, 16, 17, 21, 22, 23, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[] {18, 20};
    private static final int[] OUTPUT_BORDER = new int[] {24, 26};
    private static final int[] INPUT_SLOT = new int[] {30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int[] OUTPUT_SLOT = new int[] {30, 31, 32, 39, 40, 41, 48, 49, 50};

    private static final int CARGO_FILTER_SLOT = 12;
    private static final int CARGO_MODE_SLOT = 13;

    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 19;

    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 25;

    public static final int[] ITEM_MATCH = new int[] {30, 31, 32, 39, 40, 41, 48, 49, 50};

    public PointTransferMenu(@Nonnull AbstractMachine machine) {
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

        this.addItem(CARGO_FILTER_SLOT, CargoFilter.OPTION.defaultIcon());
        this.addItem(CARGO_MODE_SLOT, CargoMode.OPTION.defaultIcon());

        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_INPUT_OPTION.defaultIcon());

        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_OUTPUT_OPTION.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Location location = block.getLocation();
        blockMenu.addMenuOpeningHandler(p -> FinalTech.getLocationDataService().setLocationData(location, ConstantTableUtil.CONFIG_UUID, p.getUniqueId().toString()));
        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, ChestMenuUtil.warpByConsumer(CargoFilter.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, ChestMenuUtil.warpByConsumer(CargoMode.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, ChestMenuUtil.warpByConsumer(BlockSearchMode.POINT_INPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, ChestMenuUtil.warpByConsumer(BlockSearchMode.POINT_OUTPUT_OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        CargoFilter.OPTION.checkAndUpdateIcon(inventory, CARGO_FILTER_SLOT, FinalTech.getLocationDataService(), location);
        CargoMode.OPTION.checkAndUpdateIcon(inventory, CARGO_MODE_SLOT, FinalTech.getLocationDataService(), location);
        BlockSearchMode.POINT_INPUT_OPTION.checkAndUpdateIcon(inventory, INPUT_BLOCK_SEARCH_MODE_SLOT, FinalTech.getLocationDataService(), location);
        BlockSearchMode.POINT_OUTPUT_OPTION.checkAndUpdateIcon(inventory, OUTPUT_BLOCK_SEARCH_MODE_SLOT, FinalTech.getLocationDataService(), location);
    }
}
