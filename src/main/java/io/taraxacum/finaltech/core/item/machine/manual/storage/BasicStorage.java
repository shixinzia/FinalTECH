package io.taraxacum.finaltech.core.item.machine.manual.storage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.inventory.manual.AbstractManualMachineInventory;
import io.taraxacum.finaltech.core.inventory.manual.StorageInventory;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Final_ROOT
 */
public class BasicStorage extends AbstractStorageMachine implements RecipeItem {
    private final Map<Location, Map<String, ItemWithAmount>> locationMap;
    private final String amountLimit = ConfigUtil.getOrDefaultItemSetting("16384", this, "limit-amount");
    private final int typeLimit = ConfigUtil.getOrDefaultItemSetting(4, this, "limit-type");

    public BasicStorage(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
        this.locationMap = FinalTech.isAsyncSlimefunItem(this.getId()) ? new ConcurrentHashMap<>() : new HashMap<>();
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> drops) {
                Location location = blockBreakEvent.getBlock().getLocation();
                if(FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
                    BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(location);
                    if(blockMenu != null && blockMenu.getPreset().getID().equals(BasicStorage.this.getId())) {
                        Inventory inventory = blockMenu.toInventory();
                        InventoryUtil.dropItems(inventory, location, BasicStorage.this.getInputSlot());
                        InventoryUtil.dropItems(inventory, location, BasicStorage.this.getOutputSlot());
                    }
                }

                BasicStorage.this.locationMap.remove(location);
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractManualMachineInventory newMachineInventory() {
        return new StorageInventory(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if (inventory == null) {
            return;
        }

        if (!inventory.getViewers().isEmpty()) {
            this.getMachineInventory().updateInventory(inventory, locationData.getLocation());
        }
    }

    @Nonnull
    public Map<String, ItemWithAmount> getItemMap(@Nonnull LocationData locationData) {
        Map<String, ItemWithAmount> itemMap = this.locationMap.get(locationData.getLocation());
        if (itemMap == null) {
            itemMap = new HashMap<>();
            Set<String> keySet = FinalTech.getLocationDataService().getKeys(locationData);
            for (String key : keySet) {
                if (key.startsWith(this.keyItem)) {
                    String index = key.substring(this.keyItem.length());
                    String itemStr = FinalTech.getLocationDataService().getLocationData(locationData, key);
                    String amountStr = FinalTech.getLocationDataService().getLocationData(locationData, this.keyAmount + index);
                    if(itemStr != null && amountStr != null) {
                        ItemStack itemStack = ItemStackUtil.stringToItemStack(itemStr);
                        if(itemStack != null) {
                            itemMap.put(itemStr, new ItemWithAmount(new ItemWrapper(itemStack), itemStr, amountStr, index));
                        }
                    }
                }
            }
            this.locationMap.put(locationData.getLocation(), itemMap);
        }
        return itemMap;
    }

    @Nonnull
    @Override
    public String getAmountLimit() {
        return this.amountLimit;
    }

    @Override
    public int getTypeLimit() {
        return this.typeLimit;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                this.amountLimit,
                String.valueOf(this.typeLimit));
    }
}
