package io.taraxacum.finaltech.core.item.machine.cargo.storage;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.dto.StringItemCardCache;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.machine.cargo.AbstractCargo;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.StorageInteractPortMenu;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class StorageInteractPort extends AbstractCargo implements RecipeItem {
    private final int searchLimit = ConfigUtil.getOrDefaultItemSetting(3, this, "search-limit");

    public StorageInteractPort(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StorageInteractPortMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Block targetBlock = block.getRelative(BlockFace.UP);
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }
        if (FinalTech.getLocationDataService().getInventory(targetBlock.getLocation()) != null) {
            if (Bukkit.isPrimaryThread()) {
                BlockState blockState = targetBlock.getState();
                if (blockState instanceof InventoryHolder inventoryHolder) {
                    Inventory targetInventory = inventoryHolder.getInventory();
                    this.doFunction(targetInventory, inventory, block.getLocation());
                }
            } else {
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                    BlockState blockState = targetBlock.getState();
                    if (blockState instanceof InventoryHolder inventoryHolder) {
                        Inventory targetInventory = inventoryHolder.getInventory();
                        FinalTech.getLocationRunnableFactory().waitThenRun(() -> StorageInteractPort.this.doFunction(targetInventory, inventory, block.getLocation()), targetBlock.getLocation(), block.getLocation());
                    }
                });
            }
        }
    }

    private void doFunction(@Nonnull Inventory targetInventory, @Nonnull Inventory blockInventory, @Nonnull Location location) {
        boolean canInput = !InventoryUtil.isEmpty(blockInventory, this.getInputSlot()) && InventoryUtil.slotCount(blockInventory, this.getInputSlot()) >= this.getInputSlot().length / 2;
        boolean canOutput = !InventoryUtil.isFull(blockInventory, this.getOutputSlot()) && InventoryUtil.slotCount(blockInventory, this.getOutputSlot()) < this.getOutputSlot().length / 2;

        if (!canInput && !canOutput) {
            return;
        }

        if (canInput) {
            InventoryUtil.stockSlots(blockInventory, this.getInputSlot());
        }

        canInput = !InventoryUtil.isEmpty(blockInventory, this.getInputSlot()) && InventoryUtil.slotCount(blockInventory, this.getInputSlot()) >= this.getInputSlot().length / 2;

        if (!canInput && !canOutput) {
            return;
        }

        int pushItemAmount = 0;
        StringItemCardCache[] stringItemCardCaches = new StringItemCardCache[this.searchLimit];
        for (int i = 0, size = Math.min(targetInventory.getSize(), this.searchLimit); i < size; i++) {
            ItemStack itemStack = targetInventory.getItem(i);
            if(!ItemStackUtil.isItemNull(itemStack) && FinalTechItems.STORAGE_CARD.verifyItem(itemStack)) {
                stringItemCardCaches[i] = new StringItemCardCache(itemStack);
            }

            if (stringItemCardCaches[i] != null && stringItemCardCaches[i].getCardItem().getAmount() == 1) {
                pushItemAmount++;
            }
        }

        for (StringItemCardCache stringItemCardCache : stringItemCardCaches) {
            if(stringItemCardCache == null) {
                continue;
            }
            if (!canInput && !canOutput) {
                break;
            }
            ItemMeta itemMeta = stringItemCardCache.getCardItemMeta();
            ItemWrapper stringItem = stringItemCardCache.getTemplateStringItem();

            int pushCount = 0;
            if (canOutput && stringItemCardCache.getCardItem().getAmount() == 1 && stringItemCardCache.storedItem()) {
                pushItemAmount--;
                pushCount = StringItemUtil.pullItemFromCard(stringItemCardCache, blockInventory, this.getOutputSlot());
                if (pushCount > 0) {
                    InventoryUtil.stockSlots(blockInventory, this.getOutputSlot());
                    canOutput = !InventoryUtil.isFull(blockInventory, this.getOutputSlot());
                }
                if (pushItemAmount == 0) {
                    canOutput = false;
                }
            }

            int stackCount = 0;
            if (canInput) {
                stackCount = StringItemUtil.storageItemToCard(stringItemCardCache, blockInventory, JavaUtil.shuffle(this.getInputSlot()));
                if (stackCount > 0) {
                    canInput = !InventoryUtil.isEmpty(blockInventory, this.getInputSlot());
                }
            }
            if (pushCount - stackCount != 0 || stringItem != stringItemCardCache.getTemplateStringItem()) {
                FinalTechItems.STORAGE_CARD.updateLore(itemMeta, stringItemCardCache.storedItem() ? stringItemCardCache.getTemplateStringItem().getItemStack() : null, stringItemCardCache.getAmount());
                stringItemCardCache.updateCardItemMeta();
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.searchLimit));
    }
}
