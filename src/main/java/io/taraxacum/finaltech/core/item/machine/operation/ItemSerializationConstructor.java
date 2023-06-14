package io.taraxacum.finaltech.core.item.machine.operation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.enums.LogSourceType;
import io.taraxacum.finaltech.core.interfaces.UnCopiableItem;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.simple.ItemSerializationConstructorInventory;
import io.taraxacum.finaltech.core.operation.ItemSerializationConstructorOperation;
import io.taraxacum.finaltech.core.operation.ItemCopyCardOperation;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a slimefun machine
 * it will be used in gameplay
 * It's not a function class!
 * @author Final_ROOT
 */
public class ItemSerializationConstructor extends AbstractOperationMachine {
    private final CustomItemStack nullInfoIcon = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTech.getLanguageString("items", this.getId(), "null-icon", "name"), FinalTech.getLanguageStringArray("items", this.getId(), "null-icon", "lore"));
    private final String keyItem = "i";
    private final String keyAmount = "a";

    private double efficiency = 1;
    private final double rate = ConfigUtil.getOrDefaultItemSetting(0.9, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId(), "rate");
    private List<Location> locationList = new ArrayList<>();
    private List<Location> lastLocationList = new ArrayList<>();
    private int statusSlot;

    public ItemSerializationConstructor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        ItemSerializationConstructorInventory itemSerializationConstructorInventory = new ItemSerializationConstructorInventory(this);
        this.statusSlot = itemSerializationConstructorInventory.statusSlot;
        return itemSerializationConstructorInventory;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                Location location = blockBreakEvent.getBlock().getLocation();
                if(FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
                    BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(location);
                    if(blockMenu != null && blockMenu.getPreset().getID().equals(ItemSerializationConstructor.this.getId())) {
                        Inventory inventory = blockMenu.toInventory();
                        InventoryUtil.dropItems(inventory, location, ItemSerializationConstructor.this.getInputSlot());
                        InventoryUtil.dropItems(inventory, location, ItemSerializationConstructor.this.getOutputSlot());
                    }
                }

                ItemSerializationConstructor.this.getMachineProcessor().endOperation(location);
            }
        };
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Location location = block.getLocation();
        this.locationList.add(location);

        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }

        if(FinalTech.getTps() < ConstantTableUtil.WARNING_TPS && this.lastLocationList.size() > 1) {
            if (!BlockTickerUtil.subSleep(FinalTech.getLocationDataService(), locationData)) {
                return;
            }

            Location randomLocation = this.lastLocationList.get(FinalTech.getRandom().nextInt(this.lastLocationList.size()));
            double manhattanDistance = LocationUtil.getManhattanDistance(randomLocation, location);
            if(manhattanDistance < this.lastLocationList.size()) {
                BlockTickerUtil.setSleep(FinalTech.getLocationDataService(), locationData, this.lastLocationList.size() * (int)(20 - FinalTech.getTps() + 1) * (1 + InventoryUtil.slotCount(inventory, this.getInputSlot())));
                return;
            }
        }

        ItemSerializationConstructorOperation operation = (ItemSerializationConstructorOperation) this.getMachineProcessor().getOperation(block);

        String itemString = FinalTech.getLocationDataService().getLocationData(locationData, this.keyItem);
        if (operation == null && itemString != null) {
            ItemStack stringItem = ItemStackUtil.stringToItemStack(itemString);
            if (!ItemStackUtil.isItemNull(stringItem) && ItemSerializationConstructorOperation.getType(stringItem) == ItemSerializationConstructorOperation.COPY_CARD) {
                operation = ItemSerializationConstructorOperation.newInstance(stringItem);
                if (operation != null) {
                    this.getMachineProcessor().startOperation(block, operation);
                    int amount = (int) Double.parseDouble(FinalTech.getLocationDataService().getLocationData(locationData, this.keyAmount));
                    ((ItemCopyCardOperation)operation).setCount(amount);
                }
            }
        }

        for (int slot : this.getInputSlot()) {
            ItemStack inputItem = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            if (operation == null) {
                SlimefunItem sfItem = SlimefunItem.getByItem(inputItem);
                if (sfItem == null || sfItem instanceof UnCopiableItem) {
                    break;
                }

                operation = ItemSerializationConstructorOperation.newInstance(inputItem);
                if (operation == null) {
                    break;
                }
                this.getMachineProcessor().startOperation(block, operation);
            } else {
                operation.addItem(inputItem);
            }
        }

        if (operation != null && operation.isFinished() && InventoryUtil.tryPushAllItem(inventory, this.getOutputSlot(), operation.getResult())) {
            if(operation.getType() == ItemSerializationConstructorOperation.COPY_CARD) {
                FinalTech.getLogService().addItem(FinalTechItems.COPY_CARD.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, null, location, this.getAddon().getJavaPlugin());
            } else if(operation.getType() == ItemSerializationConstructorOperation.ITEM_PHONY) {
                SlimefunItem sfItem = SlimefunItem.getByItem(operation.getResult());
                if(sfItem != null) {
                    FinalTech.getLogService().addItem(sfItem.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, null, location, this.getAddon().getJavaPlugin());
                }
            }
            this.getMachineProcessor().endOperation(block);
            operation = null;
            FinalTech.getLocationDataService().setLocationData(locationData, this.keyItem, null);
            FinalTech.getLocationDataService().setLocationData(locationData, this.keyAmount, null);
        }

        if (operation != null && operation.getType() == ItemSerializationConstructorOperation.COPY_CARD) {
            itemString = FinalTech.getLocationDataService().getLocationData(locationData, this.keyItem);
            if(itemString == null) {
                itemString = FinalTech.safeSql() ? SqlUtil.getSafeSql(ItemStackUtil.itemStackToString(((ItemCopyCardOperation)operation).getMatchItem())) : ItemStackUtil.itemStackToString(((ItemCopyCardOperation)operation).getMatchItem());
                if(itemString != null) {
                    FinalTech.getLocationDataService().setLocationData(locationData, this.keyItem, itemString);
                }
            }
            FinalTech.getLocationDataService().setLocationData(locationData, this.keyAmount, String.valueOf((int)((ItemCopyCardOperation)operation).getCount()));
        }

        if (!inventory.getViewers().isEmpty()) {
            ItemStack showItem;
            if (operation != null) {
                operation.updateShowItem();
                showItem = operation.getShowItem();
            } else {
                showItem = this.nullInfoIcon;
            }
            inventory.setItem(this.statusSlot, showItem);
        }
    }

    @Override
    protected void uniqueTick() {
        super.uniqueTick();
        List<Location> locationList = this.lastLocationList;
        this.lastLocationList = this.locationList;
        this.locationList = locationList;
        this.locationList.clear();

        if(FinalTech.getTps() < ConstantTableUtil.WARNING_TPS) {
            this.efficiency = Math.pow(this.rate / (1 + this.lastLocationList.size() + FinalTechItems.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR.getLastLocationList().size()), 20.0 - FinalTech.getTps());
            this.efficiency /= 1 + this.lastLocationList.size() + FinalTechItems.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR.getLastLocationList().size();
        } else {
            this.efficiency = 1;
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT),
                String.valueOf(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT),
                String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT));
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public double getRate() {
        return rate;
    }

    public List<Location> getLastLocationList() {
        return lastLocationList;
    }
}
