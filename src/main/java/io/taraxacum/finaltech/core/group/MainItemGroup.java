package io.taraxacum.finaltech.core.group;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.dto.SimpleVirtualInventory;
import io.taraxacum.libs.plugin.interfaces.VirtualInventory;
import io.taraxacum.libs.plugin.util.TextUtil;
import io.taraxacum.libs.slimefun.util.GuideUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * @author Final_ROOT
 */
// TODO: abstract as lib
public class MainItemGroup extends FlexItemGroup {
    private final int backSlot = 1;
    private final int previousSlot = 3;
    private final int nextSlot = 5;
    private final int iconSlot = 7;
    private final int[] border = new int[] {0, 2, 4, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
    private final int[] mainContentSlot = new int[] {18, 27, 36, 45};
    private final int[][] subContentSlot = new int[][] {
            new int[] {19, 20, 21, 22, 23, 24, 25, 26},
            new int[] {28, 29, 30, 31, 32, 33, 34, 35},
            new int[] {37, 38, 39, 40, 41, 42, 43, 44},
            new int[] {46, 47, 48, 49, 50, 51, 52, 53}};

    private List<ItemGroup> fatherItemGroupList = new ArrayList<>();
    private List<List<ItemGroup>> sonItemGroupList = new ArrayList<>();

    private Map<Integer, MainItemGroup> pageMap = new LinkedHashMap<>();

    private final ItemStack item;
    private final int page;

    public MainItemGroup(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
        this.page = 1;
        this.item = item;
        this.pageMap.put(1, this);
    }

    private MainItemGroup(NamespacedKey key, ItemStack item, int tier, int page) {
        super(key, item, tier);
        this.page = page;
        this.item = item;
    }

    @Override
    public boolean isVisible(@Nonnull Player p, @Nonnull PlayerProfile profile, @Nonnull SlimefunGuideMode layout) {
        return layout.equals(SlimefunGuideMode.SURVIVAL_MODE) && this.page == 1;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        playerProfile.getGuideHistory().add(this, this.page);
        this.generateMenu(player, playerProfile, slimefunGuideMode).open(player);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        for (int i = 0; i < this.fatherItemGroupList.size(); i++) {
            this.fatherItemGroupList.get(i).register(addon);
            for (ItemGroup itemGroup : this.sonItemGroupList.get(i)) {
                itemGroup.register(addon);
            }
        }
    }

    public void addTo(@Nonnull ItemGroup fatherItemGroup, @Nonnull ItemGroup... itemGroup) {
        if (this.fatherItemGroupList.contains(fatherItemGroup) && this.sonItemGroupList.size() > this.fatherItemGroupList.indexOf(fatherItemGroup)) {
            this.sonItemGroupList.get(this.fatherItemGroupList.indexOf(fatherItemGroup)).addAll(Arrays.stream(itemGroup).toList());
        } else if (!this.fatherItemGroupList.contains(fatherItemGroup)) {
            this.fatherItemGroupList.add(fatherItemGroup);
            this.sonItemGroupList.add(new ArrayList<>(Arrays.stream(itemGroup).toList()));
        }
    }

    @Nonnull
    private VirtualInventory generateMenu(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        int size = 54;
        SimpleVirtualInventory virtualInventory = new SimpleVirtualInventory(size, TextUtil.colorRandomString(FinalTech.getLanguageString(FinalTech.class.getSimpleName()) + "-" + FinalTech.getConfigManager().getOrDefault("unknown", "version")));
        virtualInventory.setAllowClickPlayerInventory(false);

        for (int slot : JavaUtil.generateInts(size)) {
            virtualInventory.setOnClick(slot, virtualInventory.CANCEL_CLICK_CONSUMER);
        }

        virtualInventory.setOnOpen(inventoryOpenEvent -> player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1));

        List<ItemGroup> fatherItemGroupList = new ArrayList<>();
        List<List<ItemGroup>> sonItemGroupList = new ArrayList<>();
        for(int i = 0; i < this.fatherItemGroupList.size(); i++) {
            List<ItemGroup> itemGroupList = new ArrayList<>();
            this.sonItemGroupList.get(i).forEach(itemGroup -> {
                if(itemGroup instanceof SubFlexItemGroup subFlexItemGroup) {
                    if(subFlexItemGroup.isTrulyVisible(player)) {
                        itemGroupList.add(itemGroup);
                    }
                } else if(itemGroup instanceof FlexItemGroup flexItemGroup ? flexItemGroup.isVisible(player, playerProfile, slimefunGuideMode) : itemGroup.isVisible(player)) {
                    itemGroupList.add(itemGroup);
                }
            });

            if(!itemGroupList.isEmpty()) {
                sonItemGroupList.add(itemGroupList);
                fatherItemGroupList.add(this.fatherItemGroupList.get(i));
            }
        }
        int page;
        if(this.page > (fatherItemGroupList.size() - 1) / mainContentSlot.length + 1) {
            page = 1;
        } else {
            page = this.page;
        }

        virtualInventory.getInventory().setItem(this.backSlot, ChestMenuUtils.getBackButton(player));
        virtualInventory.setOnClick(this.backSlot, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            GuideHistory guideHistory = playerProfile.getGuideHistory();
            if (inventoryClickEvent.getClick().isShiftClick()) {
                SlimefunGuide.openMainMenu(playerProfile, slimefunGuideMode, guideHistory.getMainMenuPage());
            } else {
                guideHistory.goBack(Slimefun.getRegistry().getSlimefunGuide(SlimefunGuideMode.SURVIVAL_MODE));
            }
        });

        virtualInventory.getInventory().setItem(this.previousSlot, ChestMenuUtils.getPreviousButton(player, page, (fatherItemGroupList.size() - 1) / this.mainContentSlot.length + 1));
        virtualInventory.setOnClick(this.previousSlot, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            GuideUtil.removeLastEntry(playerProfile.getGuideHistory());
            MainItemGroup mainItemGroup = this.getByPage(Math.max(page - 1, 1));
            mainItemGroup.open(player, playerProfile, slimefunGuideMode);
        });

        virtualInventory.getInventory().setItem(this.nextSlot, ChestMenuUtils.getNextButton(player, page, (fatherItemGroupList.size() - 1) / this.mainContentSlot.length + 1));
        virtualInventory.setOnClick(this.nextSlot, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            GuideUtil.removeLastEntry(playerProfile.getGuideHistory());
            MainItemGroup mainItemGroup = this.getByPage(Math.min(page + 1, (fatherItemGroupList.size() - 1) / this.mainContentSlot.length + 1));
            mainItemGroup.open(player, playerProfile, slimefunGuideMode);
        });

        virtualInventory.getInventory().setItem(this.iconSlot, MachineUtil.cloneAsDescriptiveItem(super.item));

        for (int slot : this.border) {
            virtualInventory.getInventory().setItem(slot, ChestMenuUtils.getBackground());
        }

        for (int i = page * this.mainContentSlot.length - this.mainContentSlot.length; i < page * this.mainContentSlot.length; i++) {
            if (i < fatherItemGroupList.size() && i < sonItemGroupList.size()) {
                virtualInventory.getInventory().setItem(this.mainContentSlot[i % this.mainContentSlot.length], fatherItemGroupList.get(i).getItem(player));
                final int index = i;
                virtualInventory.setOnClick(this.mainContentSlot[i % this.mainContentSlot.length], inventoryClickEvent -> {
                    inventoryClickEvent.setCancelled(true);

                    ItemGroup itemGroup = fatherItemGroupList.get(index);
                    if (itemGroup instanceof FlexItemGroup flexItemGroup) {
                        flexItemGroup.open(player, playerProfile, slimefunGuideMode);
                    }
                });

                List<ItemGroup> subItemGroupList = sonItemGroupList.get(i);
                for (int j = 0; j < subItemGroupList.size(); j++) {
                    virtualInventory.getInventory().setItem(this.subContentSlot[i % this.mainContentSlot.length][j], subItemGroupList.get(j).getItem(player));
                    final int subIndex = j;
                    virtualInventory.setOnClick(this.subContentSlot[i % this.mainContentSlot.length][j], inventoryClickEvent -> {
                        inventoryClickEvent.setCancelled(true);

                        ItemGroup itemGroup = subItemGroupList.get(subIndex);
                        if (itemGroup instanceof FlexItemGroup flexItemGroup) {
                            flexItemGroup.open(player, playerProfile, slimefunGuideMode);
                        }
                    });
                }
            }
        }

        return virtualInventory;
    }

    @Nonnull
    public MainItemGroup getByPage(int page) {
        if (this.pageMap.containsKey(page)) {
            return this.pageMap.get(page);
        } else {
            synchronized (this.pageMap.get(1)) {
                if (this.pageMap.containsKey(page)) {
                    return this.pageMap.get(page);
                }
                MainItemGroup mainItemGroup = this.pageMap.get(1);
                mainItemGroup = new MainItemGroup(new NamespacedKey(FinalTech.getInstance(), this.getKey().getKey() + "_" + page), mainItemGroup.item, mainItemGroup.getTier(), page);
                mainItemGroup.fatherItemGroupList = this.fatherItemGroupList;
                mainItemGroup.sonItemGroupList = this.sonItemGroupList;
                mainItemGroup.pageMap = this.pageMap;
                this.pageMap.put(page, mainItemGroup);
                return mainItemGroup;
            }
        }
    }
}
