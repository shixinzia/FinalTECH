package io.taraxacum.finaltech.core.group;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.inventory.common.MainItemGroupInventory;
import io.taraxacum.libs.plugin.service.InventoryHistoryService;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Yeah it need to be updated.
 * @author Final_ROOT
 */
public class MainItemGroup extends FlexItemGroup {
    private final InventoryHistoryService inventoryHistoryService;
    private final List<FatherSonItemGroup> fatherSonItemGroupList = new ArrayList<>();
    private final int page;

    public MainItemGroup(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
        this.inventoryHistoryService = FinalTech.getSlimefunGuideHistoryService();
        this.page = 1;
    }

    public MainItemGroup(NamespacedKey key, ItemStack item, @Nonnull InventoryHistoryService inventoryHistoryService, int tier, int page) {
        super(key, item, tier);
        this.inventoryHistoryService = inventoryHistoryService;
        this.page = page;
    }

    /**
     * @return if it is showed in slimefun guide.
     */
    @Override
    public boolean isVisible(@Nonnull Player p, @Nonnull PlayerProfile profile, @Nonnull SlimefunGuideMode layout) {
        return layout.equals(SlimefunGuideMode.SURVIVAL_MODE) && this.page == 1 && "guide".equals(FinalTech.getConfigManager().getOrDefault("table", "menu-mode"));
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        this.inventoryHistoryService.tryAddToLast(player, this);
        new MainItemGroupInventory(player, playerProfile, slimefunGuideMode, this.inventoryHistoryService, super.item, this, this.page).open(player);
    }

    /**
     * @return if it is showed as it is recognized as main item group.
     */
    public boolean isTrulyVisible(@Nonnull Player p) {
        return this.page == 1;
    }

    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode, @Nonnull InventoryHistoryService inventoryHistoryService) {
        MainItemGroup instance = this;
        if (this.inventoryHistoryService != inventoryHistoryService) {
            instance = this.generateByService(inventoryHistoryService);
        }
        inventoryHistoryService.tryAddToLast(player, instance);
        new MainItemGroupInventory(player, playerProfile, slimefunGuideMode, inventoryHistoryService, instance.item, instance, instance.page).open(player);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        for (FatherSonItemGroup fatherSonItemGroup : this.fatherSonItemGroupList) {
            fatherSonItemGroup.fatherItemGroup.register(addon);
            for (ItemGroup itemGroup : fatherSonItemGroup.sonItemGoupList) {
                itemGroup.register(addon);
            }
        }
    }

    public void addTo(@Nonnull ItemGroup fatherItemGroup, @Nonnull ItemGroup... itemGroup) {
        for (FatherSonItemGroup fatherSonItemGroup : this.fatherSonItemGroupList) {
            if (fatherItemGroup.equals(fatherSonItemGroup.fatherItemGroup)) {
                fatherSonItemGroup.sonItemGoupList.addAll(List.of(itemGroup));
                return;
            }
        }
        this.fatherSonItemGroupList.add(new FatherSonItemGroup(fatherItemGroup, itemGroup));
    }

    public void addTo(@Nonnull ItemGroup fatherItemGroup, @Nonnull List<ItemGroup> itemGroup) {
        for (FatherSonItemGroup fatherSonItemGroup : this.fatherSonItemGroupList) {
            if (fatherItemGroup.equals(fatherSonItemGroup.fatherItemGroup)) {
                fatherSonItemGroup.sonItemGoupList.addAll(itemGroup);
                return;
            }
        }
        this.fatherSonItemGroupList.add(new FatherSonItemGroup(fatherItemGroup, itemGroup));
    }

    @Nonnull
    public List<FatherSonItemGroup> getFatherSonItemGroupList() {
        return fatherSonItemGroupList;
    }

    @Nonnull
    public MainItemGroup generateByPage(int page) {
        // TODO remove finaltech
        MainItemGroup mainItemGroup = new MainItemGroup(new NamespacedKey(FinalTech.getInstance(), this.key.getKey() + "_" + page), this.item, this.inventoryHistoryService, this.tier, page);
        mainItemGroup.fatherSonItemGroupList.addAll(this.fatherSonItemGroupList);
        return mainItemGroup;
    }

    @Nonnull
    public MainItemGroup generateByService(@Nonnull InventoryHistoryService inventoryHistoryService) {
        // TODO remove finaltech
        MainItemGroup mainItemGroup = new MainItemGroup(new NamespacedKey(FinalTech.getInstance(), this.key.getKey() + "_" + page), this.item, inventoryHistoryService, this.tier, this.page);
        mainItemGroup.fatherSonItemGroupList.addAll(this.fatherSonItemGroupList);
        return mainItemGroup;
    }

    public MainItemGroup generate(int page, @Nonnull InventoryHistoryService inventoryHistoryService) {
        // TODO remove finaltech
        MainItemGroup mainItemGroup = new MainItemGroup(new NamespacedKey(FinalTech.getInstance(), this.key.getKey() + "_" + page), this.item, inventoryHistoryService, this.tier, page);
        mainItemGroup.fatherSonItemGroupList.addAll(this.fatherSonItemGroupList);
        return mainItemGroup;
    }

    public static class FatherSonItemGroup {
        private final ItemGroup fatherItemGroup;
        private final List<ItemGroup> sonItemGoupList;

        public FatherSonItemGroup(@Nonnull ItemGroup fatherItemGroup, @Nonnull List<ItemGroup> sonItemGoupList) {
            this.fatherItemGroup = fatherItemGroup;
            this.sonItemGoupList = sonItemGoupList;
        }

        public FatherSonItemGroup(@Nonnull ItemGroup fatherItemGroup, @Nonnull ItemGroup... sonItemGroupList) {
            this.fatherItemGroup = fatherItemGroup;
            this.sonItemGoupList = Arrays.stream(sonItemGroupList).collect(Collectors.toList());
        }

        @Nonnull
        public ItemGroup getFatherItemGroup() {
            return fatherItemGroup;
        }

        @Nonnull
        public List<ItemGroup> getSonItemGoupList() {
            return sonItemGoupList;
        }
    }
}
