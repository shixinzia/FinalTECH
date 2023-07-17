package io.taraxacum.finaltech.core.group;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.inventory.common.CraftItemGroupInventory;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.service.InventoryHistoryService;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.SlimefunCraftRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Yeah it need to be updated.
 * @author Final_ROOT
 */
public class CraftItemGroup extends FlexItemGroup {
    private final InventoryHistoryService inventoryHistoryService;
    private final ItemStack itemStack;
    private final int page;

    protected CraftItemGroup(@Nonnull NamespacedKey key, @Nonnull SlimefunItem slimefunItem, @Nonnull InventoryHistoryService inventoryHistoryService, int page) {
        super(key, MachineUtil.cloneAsDescriptiveItem(slimefunItem.getItem()));
        this.inventoryHistoryService = inventoryHistoryService;
        this.itemStack = slimefunItem.getItem();
        this.page = page;
    }

    protected CraftItemGroup(@Nonnull NamespacedKey key, @Nonnull ItemStack itemStack, @Nonnull InventoryHistoryService inventoryHistoryService, int page) {
        super(key, MachineUtil.cloneAsDescriptiveItem(itemStack));
        this.inventoryHistoryService = inventoryHistoryService;
        this.itemStack = itemStack;
        this.page = page;
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return false;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        this.inventoryHistoryService.tryAddToLast(player, this);
        new CraftItemGroupInventory(player, playerProfile, slimefunGuideMode, this.inventoryHistoryService, this, this.page).open(player);
    }

    @Nonnull
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Nonnull
    public CraftItemGroup generateByPage(int page) {
        // TODO remove finaltech
        return new CraftItemGroup(new NamespacedKey(FinalTech.getInstance(), this.key.getKey() + "_" + page), this.itemStack, this.inventoryHistoryService, page);
    }

    @Nonnull
    public static CraftItemGroup getBySlimefunItem(@Nonnull SlimefunItem slimefunItem, @Nonnull InventoryHistoryService inventoryHistoryService) {
        return new CraftItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_CRAFT_ITEM_GROUP_" + slimefunItem.getId().hashCode()), slimefunItem, inventoryHistoryService, 1);
    }

    @Nullable
    public static CraftItemGroup getByItemStack(@Nonnull ItemStack itemStack, @Nonnull InventoryHistoryService inventoryHistoryService) {
        String id = null;
        if (ItemStackUtil.isRawMaterial(itemStack)) {
            id = SlimefunCraftRegistry.getInstance().generateIdByMaterial(itemStack.getType());
        } else {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
            if (slimefunItem != null) {
                id = slimefunItem.getId();
            }
        }

        if (id == null) {
            return null;
        }

        return new CraftItemGroup(new NamespacedKey(FinalTech.getInstance(), "FINALTECH_CRAFT_ITEM_GROUP_" + id.hashCode()), itemStack, inventoryHistoryService, 1);
    }
}
