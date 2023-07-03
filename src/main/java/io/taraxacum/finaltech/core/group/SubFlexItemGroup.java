package io.taraxacum.finaltech.core.group;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.VisibleItem;
import io.taraxacum.finaltech.core.inventory.common.SubFlexItemGroupInventory;
import io.taraxacum.libs.plugin.service.InventoryHistoryService;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class SubFlexItemGroup extends FlexItemGroup {
    private final InventoryHistoryService inventoryHistoryService;
    private final ItemStack itemStack;
    private final List<List<SlimefunItem>> slimefunItemList = new ArrayList<>();
    private final int page;

    public SubFlexItemGroup(NamespacedKey key, ItemStack itemStack, @Nonnull InventoryHistoryService inventoryHistoryService) {
        super(key, itemStack, 1);
        this.inventoryHistoryService = inventoryHistoryService;
        this.itemStack = itemStack;
        this.page = 1;
    }

    public SubFlexItemGroup(NamespacedKey key, ItemStack itemStack, @Nonnull InventoryHistoryService inventoryHistoryService, int page) {
        super(key, itemStack, 1);
        this.inventoryHistoryService = inventoryHistoryService;
        this.itemStack = itemStack;
        this.page = page;
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return false;
    }

    @Override
    public boolean isAccessible(@Nonnull Player p) {
        return false;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        this.inventoryHistoryService.tryAddToLast(player, this);
        new SubFlexItemGroupInventory(player, playerProfile, slimefunGuideMode, this.inventoryHistoryService, this, this.page).open(player);
    }

    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode, @Nonnull InventoryHistoryService inventoryHistoryService) {
        SubFlexItemGroup instance = this;
        if (this.inventoryHistoryService != inventoryHistoryService) {
            instance = this.generateByService(inventoryHistoryService);
        }
        inventoryHistoryService.tryAddToLast(player, instance);
        new SubFlexItemGroupInventory(player, playerProfile, slimefunGuideMode, inventoryHistoryService, instance, instance.page).open(player);
    }

    public void addTo(@Nonnull SlimefunItem... slimefunItems) {
        for (int j = 0; j * 9 < slimefunItems.length; j++) {
            List<SlimefunItem> slimefunItemList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (j * 9 + i < slimefunItems.length) {
                    slimefunItemList.add(slimefunItems[j * 9 + i]);
                }
            }
            this.slimefunItemList.add(slimefunItemList);
        }
    }

    public void addTo(@Nonnull SlimefunItemStack... slimefunItemStacks) {
        for (int j = 0; j * 9 < slimefunItemStacks.length; j++) {
            List<SlimefunItem> slimefunItemList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (j * 9 + i < slimefunItemStacks.length) {
                    SlimefunItemStack slimefunItemStack = slimefunItemStacks[j * 9 + i];
                    SlimefunItem slimefunItem = SlimefunItem.getByItem(slimefunItemStack);
                    if (slimefunItem != null) {
                        slimefunItemList.add(slimefunItem);
                    }
                }
            }
            this.slimefunItemList.add(slimefunItemList);
        }
    }

    public void addTo(@Nonnull List<SlimefunItem> slimefunItemList) {
        for (int j = 0; j * 9 < slimefunItemList.size(); j++) {
            List<SlimefunItem> aSlimefunItemList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (j * 9 + i < slimefunItemList.size()) {
                    aSlimefunItemList.add(slimefunItemList.get(j * 9 + i));
                }
            }
            this.slimefunItemList.add(aSlimefunItemList);
        }
    }

    public void addFrom(@Nonnull SubFlexItemGroup... subFlexItemGroups) {
        for (SubFlexItemGroup subFlexItemGroup : subFlexItemGroups) {
            this.slimefunItemList.addAll(subFlexItemGroup.slimefunItemList);
        }
    }

    public void addFrom(@Nonnull List<SubFlexItemGroup> subFlexItemGroups) {
        for (SubFlexItemGroup subFlexItemGroup : subFlexItemGroups) {
            this.slimefunItemList.addAll(subFlexItemGroup.slimefunItemList);
        }
    }

    @Nonnull
    public List<SlimefunItem> getSlimefunItems() {
        List<SlimefunItem> result = new ArrayList<>();
        for (List<SlimefunItem> list : this.slimefunItemList) {
            result.addAll(list);
        }
        return result;
    }

    /**
     * Do not update the result.
     */
    @Nonnull
    public List<List<SlimefunItem>> getSlimefunItemList() {
        return this.slimefunItemList;
    }

    @Nonnull
    public SubFlexItemGroup generateByPage(int page) {
        // TODO remove finaltech
        SubFlexItemGroup subFlexItemGroup = new SubFlexItemGroup(new NamespacedKey(FinalTech.getInstance(), this.key.getKey() + "_" + page), this.itemStack, this.inventoryHistoryService, page);
        subFlexItemGroup.slimefunItemList.addAll(this.slimefunItemList);
        return subFlexItemGroup;
    }

    @Nonnull
    public SubFlexItemGroup generateByService(@Nonnull InventoryHistoryService inventoryHistoryService) {
        // TODO remove finaltech
        SubFlexItemGroup subFlexItemGroup = new SubFlexItemGroup(new NamespacedKey(FinalTech.getInstance(), this.key.getKey() + "_" + page), this.itemStack, inventoryHistoryService, this.page);
        subFlexItemGroup.slimefunItemList.addAll(this.slimefunItemList);
        return subFlexItemGroup;
    }

    public boolean isTrulyVisible(@Nonnull Player player) {
        for (List<SlimefunItem> slimefunItemList : this.slimefunItemList) {
            for (SlimefunItem slimefunItem : slimefunItemList) {
                if (slimefunItem.isHidden()) {
                    continue;
                }
                if (slimefunItem instanceof VisibleItem visibleItem && !visibleItem.isVisible(player)) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    // TODO
    @Nonnull
    public static SubFlexItemGroup generateFromItemGroup(@Nonnull JavaPlugin javaPlugin, @Nonnull ItemGroup itemGroup, @Nonnull Player player, @Nonnull InventoryHistoryService inventoryHistoryService) {
        SubFlexItemGroup subFlexItemGroup = new SubFlexItemGroup(new NamespacedKey(javaPlugin, itemGroup.getKey().getNamespace()), itemGroup.getItem(player), inventoryHistoryService);
        subFlexItemGroup.addTo(itemGroup.getItems());
        return subFlexItemGroup;
    }
}