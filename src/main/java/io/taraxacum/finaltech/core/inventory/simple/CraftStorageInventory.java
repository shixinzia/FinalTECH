package io.taraxacum.finaltech.core.inventory.simple;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.inventory.AbstractOrdinaryMachineInventory;
import io.taraxacum.finaltech.core.item.machine.operation.CraftStorage;
import io.taraxacum.finaltech.core.operation.CraftStorageOperation;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CraftStorageInventory extends AbstractOrdinaryMachineInventory {
    private final int[] border = new int[] {4, 13, 22, 31, 40, 49};
    private final int[] inputBorder = new int[] {3, 12, 21, 30, 39, 48};
    private final int[] outputBorder = new int[] {5, 14, 23, 32, 41, 50};
    private final int[] inputSlot = new int[] {0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47};
    private final int[] outputSlot = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53};
    public final int statusSlot = 4;

    private final CraftStorage craftStorage;

    public CraftStorageInventory(@Nonnull CraftStorage craftStorage) {
        super(craftStorage);
        this.craftStorage = craftStorage;
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

    @Nullable
    @Override
    public Consumer<InventoryClickEvent> onClick(@Nonnull Location location, int slot) {
        return slot == this.statusSlot ? this.onClickStatus(location) : super.onClick(location, slot);
    }

    @Override
    protected void initSelf() {

    }

    @Nonnull
    @Override
    protected int[] getInputSlot() {
        return this.inputSlot;
    }

    @Nonnull
    @Override
    protected int[] getOutputSlot() {
        return this.outputSlot;
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Nonnull
    @Override
    public int[] requestSlots(@Nonnull RequestType requestType, @Nonnull ItemStack itemStack, @Nonnull Inventory inventory, @Nonnull Location location) {
        if (FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
            BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(location);
            SlimefunItem slimefunItem = slimefunLocationDataService.getSlimefunItem(location);
            if (blockMenu != null && slimefunItem == this.craftStorage) {
                MachineOperation machineOperation = this.craftStorage.getMachineProcessor().getOperation(location);
                if (machineOperation instanceof CraftStorageOperation craftStorageOperation) {
                    switch (requestType) {
                        case INPUT -> this.craftStorage.doInput(blockMenu.toInventory(), craftStorageOperation);
                        case OUTPUT -> this.craftStorage.doOutput(blockMenu.toInventory(), craftStorageOperation);
                    }
                }

            }
        }
        return super.requestSlots(requestType, itemStack, inventory, location);
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }

    private Consumer<InventoryClickEvent> onClickStatus(@Nonnull Location location) {
        return inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            ItemStack itemOnCursor = inventoryClickEvent.getWhoClicked().getItemOnCursor();
            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemOnCursor);
            if(slimefunItem != null && this.craftStorage.calAllowed(slimefunItem)) {
                this.craftStorage.setupCraft(location, slimefunItem);
            }
        };
    }
}
