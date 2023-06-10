package io.taraxacum.finaltech.core.item.machine.operation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.enums.LogSourceType;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ItemSerializationConstructorMenu;
import io.taraxacum.finaltech.core.operation.ItemCopyCardOperation;
import io.taraxacum.finaltech.core.operation.ItemSerializationConstructorOperation;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class MatrixItemSerializationConstructor extends AbstractOperationMachine {
    private final CustomItemStack nullInfoIcon = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, FinalTech.getLanguageString("items", this.getId(), "null-icon", "name"), FinalTech.getLanguageStringArray("items", this.getId(), "null-icon", "lore"));
    private final String keyItem = "item";
    private final String keyAmount = "amount";

    private List<Location> locationList = new ArrayList<>();
    private List<Location> lastLocationList = new ArrayList<>();

    public MatrixItemSerializationConstructor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
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
                    if(blockMenu != null && blockMenu.getPreset().getID().equals(MatrixItemSerializationConstructor.this.getId())) {
                        Inventory inventory = blockMenu.toInventory();
                        InventoryUtil.dropItems(inventory, location, MatrixItemSerializationConstructor.this.getInputSlot());
                        InventoryUtil.dropItems(inventory, location, MatrixItemSerializationConstructor.this.getOutputSlot());
                    }
                }

                MatrixItemSerializationConstructor.this.getMachineProcessor().endOperation(location);
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ItemSerializationConstructorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Location location = block.getLocation();
        this.locationList.add(location);


        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }

        if(FinalTech.getTps() < 19.5 && this.lastLocationList.size() > 1) {
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
            inventory.setItem(ItemSerializationConstructorMenu.STATUS_SLOT, showItem);
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
            FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.setEfficiency(Math.pow(FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getRate() / (1 + FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getLastLocationList().size() + this.lastLocationList.size()), 20.0 - FinalTech.getTps()));
            FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.setEfficiency(FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getEfficiency() / (1 + FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getLastLocationList().size() + this.lastLocationList.size()));
        } else {
            FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.setEfficiency(1);
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(ConstantTableUtil.ITEM_COPY_CARD_AMOUNT),
                String.valueOf(ConstantTableUtil.ITEM_SINGULARITY_AMOUNT),
                String.valueOf(ConstantTableUtil.ITEM_SPIROCHETE_AMOUNT));
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public List<Location> getLastLocationList() {
        return lastLocationList;
    }
}
