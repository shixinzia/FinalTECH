package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.MatrixReactorMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixReactor extends AbstractMachine implements RecipeItem, MenuUpdater {
    private final String keyItem = "i";
    private final String keyCount = "c";
    private final int difficulty = ConfigUtil.getOrDefaultItemSetting(80, this, "difficulty");
    private final Set<String> notAllowedIdList = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));

    public MatrixReactor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(FinalTech.getLocationDataService(), this.getId(), MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new MatrixReactorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Location location = block.getLocation();
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }
        ItemStack itemStack = inventory.getItem(MatrixReactorMenu.ITEM_INPUT_SLOT[0]);

        if (ItemStackUtil.isItemNull(itemStack)) {
            FinalTech.getLocationDataService().setLocationData(locationData, this.keyItem, null);
            FinalTech.getLocationDataService().setLocationData(locationData, this.keyCount, "0");
            if (!inventory.getViewers().isEmpty()) {
                this.updateInv(inventory, MatrixReactorMenu.STATUS_SLOT, this,
                        "0",
                        String.valueOf(this.difficulty));
            }
            return;
        } else if (!this.allowedItem(itemStack)) {
            FinalTech.getLocationDataService().setLocationData(locationData, this.keyItem, null);
            FinalTech.getLocationDataService().setLocationData(locationData, this.keyCount, "0");
            if (!inventory.getViewers().isEmpty()) {
                this.updateInv(inventory, MatrixReactorMenu.STATUS_SLOT, this,
                        "0",
                        String.valueOf(this.difficulty));
            }
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> InventoryUtil.dropItems(inventory, location, MatrixReactorMenu.ITEM_INPUT_SLOT));
            return;
        }

        ItemStack stringItem = null;
        String keyItemStr = FinalTech.getLocationDataService().getLocationData(locationData, this.keyItem);
        if (keyItemStr != null) {
            stringItem = ItemStackUtil.stringToItemStack(keyItemStr);
        }

        boolean match = true;

        int[] orderedDustItemSlots = new int[MatrixReactorMenu.ORDERED_DUST_INPUT_SLOT.length];
        int[] unorderedDustItemSlots = new int[MatrixReactorMenu.UNORDERED_DUST_INPUT_SLOT.length];

        int amount = itemStack.getAmount();
        int orderedDustItemSlotsP = 0;
        int orderedDustItemCount = 0;
        int unorderedDustItemSlotsP = 0;
        int unorderedDustItemCount = 0;
        for (int slot : MatrixReactorMenu.ORDERED_DUST_INPUT_SLOT) {
            if (FinalTechItems.ORDERED_DUST.verifyItem(inventory.getItem(slot))) {
                orderedDustItemSlots[orderedDustItemSlotsP++] = slot;
                orderedDustItemCount += inventory.getItem(slot).getAmount();
                if (orderedDustItemCount > amount) {
                    break;
                }
            }
        }
        if (orderedDustItemCount >= amount) {
            for (int slot : MatrixReactorMenu.UNORDERED_DUST_INPUT_SLOT) {
                if (FinalTechItems.UNORDERED_DUST.verifyItem(inventory.getItem(slot))) {
                    unorderedDustItemSlots[unorderedDustItemSlotsP++] = slot;
                    unorderedDustItemCount += inventory.getItem(slot).getAmount();
                    if (unorderedDustItemCount > amount) {
                        break;
                    }
                }
            }
        }
        if (orderedDustItemCount < amount || unorderedDustItemCount < amount) {
            match = false;
        }

        if (!match) {
            String keyCountStr = FinalTech.getLocationDataService().getLocationData(locationData, this.keyCount);
            int count = keyCountStr != null ? Integer.parseInt(keyCountStr) : 0;
            count = count > 0 ? count - 1 : 0;
            FinalTech.getLocationDataService().setLocationData(locationData, this.keyCount, String.valueOf(count));
        } else  {
            orderedDustItemCount = amount;
            for (int slot : orderedDustItemSlots) {
                ItemStack dustItemStack = inventory.getItem(slot);
                int n = Math.min(dustItemStack.getAmount(), orderedDustItemCount);
                dustItemStack.setAmount(dustItemStack.getAmount() - n);
                orderedDustItemCount -= n;
                if (orderedDustItemCount == 0) {
                    break;
                }
            }

            unorderedDustItemCount = amount;
            for (int slot : unorderedDustItemSlots) {
                ItemStack dustItemStack = inventory.getItem(slot);
                int n = Math.min(dustItemStack.getAmount(), unorderedDustItemCount);
                dustItemStack.setAmount(dustItemStack.getAmount() - n);
                unorderedDustItemCount -= n;
                if (unorderedDustItemCount == 0) {
                    break;
                }
            }

            if (ItemStackUtil.isItemNull(stringItem) || !ItemStackUtil.isItemSimilar(itemStack, stringItem) || itemStack.getAmount() != stringItem.getAmount()) {
                FinalTech.getLocationDataService().setLocationData(locationData, this.keyItem, ItemStackUtil.itemStackToString(itemStack));

                int count;
                if (FinalTechItems.ITEM_PHONY.verifyItem(inventory.getItem(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]))) {
                    ItemStack itemPhony = inventory.getItem(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]);
                    itemPhony.setAmount(itemPhony.getAmount() - 1);
                    count = 1;
                } else {
                    count = FinalTech.getRandom().nextBoolean() ? 1 : 0;
                }

                FinalTech.getLocationDataService().setLocationData(locationData, this.keyCount, String.valueOf(count));
            } else {
                String keyCountStr = FinalTech.getLocationDataService().getLocationData(locationData, this.keyCount);
                int count = keyCountStr != null ? Integer.parseInt(keyCountStr) : 0;
                if (FinalTechItems.ITEM_PHONY.verifyItem(inventory.getItem(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0])) && inventory.getItem(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]).getAmount() >= amount + count && amount + count <= ConstantTableUtil.ITEM_MAX_STACK) {
                    ItemStack itemPhony = inventory.getItem(MatrixReactorMenu.ITEM_PHONY_INPUT_SLOT[0]);
                    itemPhony.setAmount(itemPhony.getAmount() - count - amount);
                    count++;
                } else {
                    count = FinalTech.getRandom().nextBoolean() ? count - 1 : count + 1;
                }

                if (count + itemStack.getAmount() >= this.difficulty) {
                    if(InventoryUtil.tryPushAllItem(inventory, this.getOutputSlot(), new ItemAmountWrapper(itemStack, 1))) {
                        FinalTech.getLocationDataService().setLocationData(locationData, this.keyItem, null);
                        FinalTech.getLocationDataService().setLocationData(locationData, this.keyCount, "0");
                        if (!inventory.getViewers().isEmpty()) {
                            this.updateInv(inventory, MatrixReactorMenu.STATUS_SLOT, this,
                                    "0",
                                    String.valueOf(difficulty));
                        }
                        return;
                    }

                    count = count < this.difficulty ? count + 1 : this.difficulty;
                }

                count = Math.max(count, 0);
                FinalTech.getLocationDataService().setLocationData(locationData, this.keyCount, String.valueOf(count));
            }
        }

        if (!inventory.getViewers().isEmpty()) {
            this.updateInv(inventory, MatrixReactorMenu.STATUS_SLOT, this,
                    JavaUtil.getFirstNotNull(FinalTech.getLocationDataService().getLocationData(locationData, this.keyCount), "0"),
                    String.valueOf(difficulty));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.difficulty),
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }

    private boolean allowedItem(@Nonnull ItemStack item) {
        if (Tag.SHULKER_BOXES.isTagged(item.getType()) || Material.BUNDLE.equals(item.getType())) {
            return false;
        }
        if (item.hasItemMeta()) {
            ItemMeta itemMeta = item.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            if (persistentDataContainer.getKeys().size() > 0) {
                for (NamespacedKey namespacedKey : persistentDataContainer.getKeys()) {
                    if (!"slimefun".equals(namespacedKey.getNamespace())) {
                        return false;
                    }
                }
            }
        }
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        return slimefunItem == null || !this.notAllowedIdList.contains(slimefunItem.getId());
    }
}
