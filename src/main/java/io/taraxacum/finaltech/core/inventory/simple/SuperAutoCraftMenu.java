//package io.taraxacum.finaltech.core.inventory.simple;
//
//import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
//import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
//import io.taraxacum.finaltech.core.operation.AutoCraftOperation;
//import io.taraxacum.finaltech.setup.FinalTechItems;
//import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
//import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
//import org.bukkit.Location;
//import org.bukkit.block.Block;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.function.Consumer;
//
//public class SuperAutoCraftMenu extends AbstractSimpleMachineInventory {
//    private final int[] border = new int[] {4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43 ,44};
//    private final int[] inputBorder = new int[] {3, 12, 21, 30};
//    private final int[] outputBorder = new int[] {5, 14, 23, 32};
//    private final int[] inputSlot = new int[] {0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29};
//    private final int[] outputSlot = new int[] {6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35};
//
//    public final int[] parseSlot = new int[] {45, 46, 47, 48, 49, 50, 51, 52, 53};
//    public final int statusSlot = 4;
//
//    public SuperAutoCraftMenu(@Nonnull SlimefunItem slimefunItem) {
//        super(slimefunItem);
//    }
//
//
//    @Override
//    protected int[] getBorder() {
//        return this.border;
//    }
//
//    @Override
//    protected int[] getInputBorder() {
//        return this.inputBorder;
//    }
//
//    @Override
//    protected int[] getOutputBorder() {
//        return this.outputBorder;
//    }
//
//    @Nullable
//    @Override
//    public Consumer<InventoryClickEvent> onClick(@Nonnull Location location, int slot) {
//        return slot == this.statusSlot ? inventoryClickEvent -> {
//            ItemStack itemOnCursor = inventoryClickEvent.getWhoClicked().getItemOnCursor();
//            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemOnCursor);
//            if(slimefunItem != null && FinalTechItems.SUPER_AUTO_CRAFT.calAllowed(slimefunItem)) {
//                FinalTechItems.SUPER_AUTO_CRAFT.setupCraft(block.getLocation(), slimefunItem);
//                // TODO: send message to player
//            }
//        } : super.onClick(location, slot);
//    }
//
//    @Nonnull
//    @Override
//    public int[] getInputSlot() {
//        return this.inputSlot;
//    }
//
//    @Nonnull
//    @Override
//    public int[] getOutputSlot() {
//        return this.outputSlot;
//    }
//
//    @Override
//    public int getSize() {
//        return 54;
//    }
//
//    @Override
//    protected void initSelf() {
//
//    }
//
//    @Override
//    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
//        super.newInstance(blockMenu, block);
//
//        blockMenu.addMenuClickHandler(statusSlot, (player, slot, itemStack, action) -> {
//            ItemStack itemOnCursor = player.getItemOnCursor();
//            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemOnCursor);
//            if(slimefunItem != null && FinalTechItems.SUPER_AUTO_CRAFT.calAllowed(slimefunItem)) {
//                FinalTechItems.SUPER_AUTO_CRAFT.setupCraft(block.getLocation(), slimefunItem);
//                // TODO: send message to player
//            }
//            return false;
//        });
//    }
//
//    @Override
//    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
//
//    }
//
//    @Override
//    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, @Nullable ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
//        if(itemTransportFlow != null && menu instanceof BlockMenu blockMenu) {
//            Location location = blockMenu.getBlock().getLocation();
//            MachineOperation machineOperation = FinalTechItems.SUPER_AUTO_CRAFT.getMachineProcessor().getOperation(location);
//            if(machineOperation instanceof AutoCraftOperation autoCraftOperation) {
//                switch (itemTransportFlow) {
//                    case INSERT -> FinalTechItems.SUPER_AUTO_CRAFT.doInput(blockMenu.toInventory(), autoCraftOperation);
//                    case WITHDRAW -> FinalTechItems.SUPER_AUTO_CRAFT.doOutput(blockMenu.toInventory(), autoCraftOperation);
//                }
//            }
//        }
//        return super.getSlotsAccessedByItemTransport(menu, itemTransportFlow, itemStack);
//    }
//}
