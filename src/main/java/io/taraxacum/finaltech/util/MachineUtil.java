package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.api.items.ItemState;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.libs.plugin.interfaces.LocationDataService;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Final_ROOT
 * @since 1.0
 */
// TODO: abstract as lib
public final class MachineUtil {
    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_PLACER_ALLOW = new BlockPlaceHandler(true) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {

        }
    };
    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_PLACER_DENY = new BlockPlaceHandler(false) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {

        }
    };
    public static final BlockPlaceHandler BLOCK_PLACE_HANDLER_DENY = new BlockPlaceHandler(false) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
            blockPlaceEvent.setCancelled(true);
        }
    };

    public static BlockBreakHandler simpleBlockBreakerHandler() {
        return new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {

            }
        };
    }

    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull LocationDataService locationDataService, @Nonnull AbstractMachine abstractMachine) {
        return new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                if(locationDataService instanceof SlimefunLocationDataService slimefunLocationDataService) {
                    BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(location);
                    if(blockMenu != null && blockMenu.getPreset().getID().equals(abstractMachine.getId())) {
                        Inventory inventory = blockMenu.toInventory();
                        InventoryUtil.dropItems(inventory, location, abstractMachine.getInputSlot());
                        InventoryUtil.dropItems(inventory, location, abstractMachine.getOutputSlot());
                    }
                }
            }
        };
    }

    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull LocationDataService locationDataService, @Nonnull String id, @Nonnull int... slots) {
        return new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                if(locationDataService instanceof SlimefunLocationDataService slimefunLocationDataService) {
                    BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(location);
                    if(blockMenu != null && blockMenu.getPreset().getID().equals(id)) {
                        Inventory inventory = blockMenu.toInventory();
                        InventoryUtil.dropItems(inventory, location, slots);
                    }
                }
            }
        };
    }

    public static BlockBreakHandler simpleBlockBreakerHandler(@Nonnull LocationDataService locationDataService, @Nonnull AbstractMachine abstractMachine, int... slots) {
        return new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
                Location location = blockBreakEvent.getBlock().getLocation();
                if(locationDataService instanceof SlimefunLocationDataService slimefunLocationDataService) {
                    BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(location);
                    if(blockMenu != null && blockMenu.getPreset().getID().equals(abstractMachine.getId())) {
                        Inventory inventory = blockMenu.toInventory();
                        InventoryUtil.dropItems(inventory, location, abstractMachine.getInputSlot());
                        InventoryUtil.dropItems(inventory, location, abstractMachine.getOutputSlot());
                        InventoryUtil.dropItems(inventory, location, slots);
                    }
                }
            }
        };
    }

    /**
     * @return Number of slots the machine have and can be interacted.
     */
    public static int calMachineSlotSize(@Nonnull AbstractMachine abstractMachine) {
        Set<Integer> slots = new HashSet<>();
        for (int slot : abstractMachine.getInputSlot()) {
            slots.add(slot);
        }
        for (int slot : abstractMachine.getOutputSlot()) {
            slots.add(slot);
        }
        return slots.size();
    }

    /**
     * @param sourceList May be altered! Clone it if necessary!
     */
    @Nonnull
    public static List<ItemAmountWrapper> calParsed(@Nonnull List<ItemAmountWrapper> sourceList, @Nonnull List<AdvancedMachineRecipe> recipeList, int amount) {
        for(int i = 0; i < amount; i++) {
            boolean work = false;
            List<ItemAmountWrapper> tempList = new ArrayList<>();
            for(ItemAmountWrapper oldItem : sourceList) {
                if(oldItem.getAmount() < Integer.MAX_VALUE / ConstantTableUtil.ITEM_MAX_STACK) {
                    for (AdvancedMachineRecipe advancedMachineRecipe : recipeList) {
                        for (AdvancedMachineRecipe.AdvancedRandomOutput advancedRandomOutput : advancedMachineRecipe.getOutputs()) {
                            ItemAmountWrapper outputItem = advancedRandomOutput.getOutputItem()[0];
                            if (advancedRandomOutput.getOutputItem().length == 1 && oldItem.getAmount() >= outputItem.getAmount() && ItemStackUtil.isItemSimilar(oldItem, outputItem)) {
                                int count = oldItem.getAmount() / outputItem.getAmount();
                                for (ItemAmountWrapper inputItem : advancedMachineRecipe.getInput()) {
                                    ItemAmountWrapper.addToList(tempList, inputItem, count * advancedMachineRecipe.getWeightSum() / advancedRandomOutput.weight());
                                }
                                oldItem.setAmount(oldItem.getAmount() - count * outputItem.getAmount());
                                work = true;
                            }
                        }
                    }
                }
                if (oldItem.getAmount() > 0) {
                    ItemAmountWrapper.addToList(tempList, oldItem);
                    oldItem.setAmount(0);
                }
            }
            sourceList = tempList;
            if (!work) {
                break;
            }
        }
        return sourceList;
    }

    @Nonnull
    public static ItemStack cloneAsDescriptiveItemWithLore(@Nonnull ItemStack itemStack, @Nonnull String... loreToLast) {
        ItemMeta itemMeta;
        if(!itemStack.hasItemMeta()) {
            itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        } else {
            itemMeta = itemStack.getItemMeta();
        }

        ItemStack result = new ItemStack(itemStack);
        ItemStackUtil.clearNBT(itemMeta);
        ItemStackUtil.addLoresToLast(itemMeta, loreToLast);
        result.setItemMeta(itemMeta);

        return result;
    }

    @Nonnull
    public static ItemStack cloneAsDescriptiveItem(@Nullable ItemStack itemStack) {
        if(ItemStackUtil.isItemNull(itemStack)) {
            return ItemStackUtil.AIR;
        }
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if(slimefunItem != null && ItemState.ENABLED.equals(slimefunItem.getState())) {
            return MachineUtil.cloneAsDescriptiveItem(slimefunItem);
        } else if(ItemStackUtil.isItemSimilar(itemStack, new ItemStack(itemStack.getType()))) {
            ItemStack result = ItemStackUtil.cloneItem(itemStack);
            ItemMeta itemMeta;
            if(!itemStack.hasItemMeta()) {
                itemMeta = Bukkit.getItemFactory().getItemMeta(result.getType());
            } else {
                itemMeta = result.getItemMeta();
            }
            if(itemMeta == null) {
                return result;
            }

            ItemStackUtil.clearNBT(itemMeta);
            if(FinalTech.getLanguageManager().containPath("option", "ICON", "descriptive-item", "minecraft")) {
                ItemStackUtil.addLoresToLast(itemMeta, FinalTech.getLanguageManager().getString("option", "ICON", "descriptive-item", "minecraft"));
            } else {
                String lore = FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("option", "ICON", "descriptive-item", "default"),
                        "minecraft");
                ItemStackUtil.addLoresToLast(itemMeta, lore);
            }
            result.setItemMeta(itemMeta);
            return result;
        } else {
            return ItemStackUtil.cloneWithoutNBT(itemStack);
        }
    }

    @Nonnull
    public static ItemStack cloneAsDescriptiveItem(@Nonnull SlimefunItem slimefunItem) {
        ItemStack itemStack = ItemStackUtil.cloneItem(slimefunItem.getItem());
        if(!itemStack.hasItemMeta()) {
            return itemStack;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        ItemStackUtil.clearNBT(itemMeta);
        if(FinalTech.getLanguageManager().containPath("option", "ICON", "descriptive-item", slimefunItem.getAddon().getName())) {
            ItemStackUtil.addLoresToLast(itemMeta, FinalTech.getLanguageManager().getString("option", "ICON", "descriptive-item", slimefunItem.getAddon().getName()));
        } else {
            String lore = FinalTech.getLanguageManager().replaceString(FinalTech.getLanguageString("option", "ICON", "descriptive-item", "default"),
                    slimefunItem.getAddon().getName());
            ItemStackUtil.addLoresToLast(itemMeta, lore);
        }
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
