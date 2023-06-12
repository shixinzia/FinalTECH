package io.taraxacum.finaltech.core.group;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerPreResearchEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.SpecialResearch;
import io.taraxacum.finaltech.core.option.Icon;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.dto.SimpleVirtualInventory;
import io.taraxacum.libs.plugin.interfaces.VirtualInventory;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.RecipeTypeRegistry;
import io.taraxacum.libs.slimefun.util.GuideUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public class TypeItemGroup extends FlexItemGroup {
    private static final JavaPlugin JAVA_PLUGIN = FinalTech.getInstance();

    private final int backSlot = 1;
    private final int previousSlot = 3;
    private final int nextSlot = 5;
    private final int iconSlot = 7;
    private final int[] border = new int[] {0, 2, 4, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
    private final int[] mainContentSlot = new int[] {
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53};


    private final Map<RecipeType, TypeItemGroup> recipeTypeMap = new LinkedHashMap<>();

    private final int page;
    private Map<Integer, TypeItemGroup> pageMap = new LinkedHashMap<>();
    private final RecipeType recipeType;
    private final List<SlimefunItem> slimefunItemList;

    protected TypeItemGroup(NamespacedKey key, RecipeType recipeType) {
        super(key, ItemStackUtil.cloneWithoutNBT(recipeType.toItem() == null ? Icon.ERROR_ICON : recipeType.toItem()));
        this.page = 1;
        this.recipeType = recipeType;
        this.slimefunItemList = new ArrayList<>();

        List<SlimefunItem> sfItemList = RecipeTypeRegistry.getInstance().getByRecipeType(recipeType);
        for(SlimefunItem sfItem : sfItemList) {
            if(!sfItem.isDisabled() && !sfItem.isHidden()) {
                this.slimefunItemList.add(sfItem);
            }
        }

        this.pageMap.put(1, this);
        recipeTypeMap.put(recipeType, this);
    }

    protected TypeItemGroup(NamespacedKey key, RecipeType recipeType, int page) {
        super(key, ItemStackUtil.cloneWithoutNBT(recipeType.toItem() == null ? Icon.ERROR_ICON : recipeType.toItem()));
        this.page = page;
        this.recipeType = recipeType;
        this.slimefunItemList = new ArrayList<>();

        List<SlimefunItem> sfItemList = RecipeTypeRegistry.getInstance().getByRecipeType(recipeType);
        for(SlimefunItem sfItem : sfItemList) {
            if(!sfItem.isDisabled() && !sfItem.isHidden()) {
                this.slimefunItemList.add(sfItem);
            }
        }
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return false;
    }

    @Override
    public void open(Player player, PlayerProfile playerProfile, SlimefunGuideMode slimefunGuideMode) {
        playerProfile.getGuideHistory().add(this, this.page);
        this.generateMenu(player, playerProfile, slimefunGuideMode).open(player);
    }

    public void refresh(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        GuideUtil.removeLastEntry(playerProfile.getGuideHistory());
        this.open(player, playerProfile, slimefunGuideMode);
    }

    @Nonnull
    private VirtualInventory generateMenu(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        int size = 54;
        SimpleVirtualInventory virtualInventory = new SimpleVirtualInventory(size, ItemStackUtil.getItemName(super.item));
        virtualInventory.setAllowClickPlayerInventory(false);

        for (int slot : JavaUtil.generateInts(size)) {
            virtualInventory.setOnClick(slot, virtualInventory.CANCEL_CLICK_CONSUMER);
        }

        virtualInventory.setOnOpen(inventoryOpenEvent -> player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1));

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

        virtualInventory.getInventory().setItem(this.previousSlot, ChestMenuUtils.getPreviousButton(player, this.page, (this.slimefunItemList.size() - 1) / this.mainContentSlot.length + 1));
        virtualInventory.setOnClick(this.previousSlot, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            GuideUtil.removeLastEntry(playerProfile.getGuideHistory());
            TypeItemGroup craftItemGroup = this.getByPage(Math.max(this.page - 1, 1));
            craftItemGroup.open(player, playerProfile, slimefunGuideMode);
        });

        virtualInventory.getInventory().setItem(this.nextSlot, ChestMenuUtils.getNextButton(player, this.page, (this.slimefunItemList.size() - 1) / this.mainContentSlot.length + 1));
        virtualInventory.setOnClick(this.nextSlot, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            GuideUtil.removeLastEntry(playerProfile.getGuideHistory());
            TypeItemGroup craftItemGroup = this.getByPage(Math.min(this.page + 1, (this.slimefunItemList.size() - 1) / this.mainContentSlot.length + 1));
            craftItemGroup.open(player, playerProfile, slimefunGuideMode);
        });

        virtualInventory.getInventory().setItem(this.iconSlot, ItemStackUtil.cloneWithoutNBT(super.item));

        for (int slot : this.border) {
            virtualInventory.getInventory().setItem(slot, ChestMenuUtils.getBackground());
        }

        for (int i = 0; i < this.mainContentSlot.length; i++) {
            int index = i + this.page * this.mainContentSlot.length - this.mainContentSlot.length;
            if (index < this.slimefunItemList.size()) {
                SlimefunItem slimefunItem = this.slimefunItemList.get(index);
                Research research = slimefunItem.getResearch();
                if (playerProfile.hasUnlocked(research)) {
                    ItemStack itemStack = MachineUtil.cloneAsDescriptiveItem(slimefunItem);
                    ItemStackUtil.addLoreToFirst(itemStack, "§7" + slimefunItem.getId());
                    virtualInventory.getInventory().setItem(this.mainContentSlot[i], itemStack);
                    virtualInventory.setOnClick(this.mainContentSlot[i], inventoryClickEvent -> {
                        inventoryClickEvent.setCancelled(true);

                        RecipeItemGroup recipeItemGroup = RecipeItemGroup.getByItemStack(player, playerProfile, slimefunGuideMode, slimefunItem.getItem());
                        if (recipeItemGroup != null) {
                            Bukkit.getScheduler().runTask(JAVA_PLUGIN, () -> recipeItemGroup.open(player, playerProfile, slimefunGuideMode));
                        }
                    });
                } else {
                    ItemStack icon = ItemStackUtil.cloneItem(ChestMenuUtils.getNotResearchedItem());
                    List<String> stringList = new ArrayList<>();
                    stringList.add("§7" + research.getName(player));
                    stringList.add("§4§l" + Slimefun.getLocalization().getMessage(player, "guide.locked"));
                    stringList.add("§a> Click to unlock");
                    if(research instanceof SpecialResearch specialResearch) {
                        stringList.addAll(List.of(specialResearch.getShowText(player)));
                    } else {
                        stringList.add("");
                        stringList.add("§7Cost: §b" + research.getCost() + " Level(s)");
                    }
                    ItemStackUtil.setLore(icon, stringList);
                    virtualInventory.getInventory().setItem(this.mainContentSlot[i], icon);
                    virtualInventory.setOnClick(this.mainContentSlot[i], inventoryClickEvent -> {
                        inventoryClickEvent.setCancelled(true);

                        PlayerPreResearchEvent event = new PlayerPreResearchEvent(player, research, slimefunItem);
                        Bukkit.getPluginManager().callEvent(event);

                        if (!event.isCancelled() && !playerProfile.hasUnlocked(research)) {
                            if (research.canUnlock(player)) {
                                Slimefun.getRegistry().getSlimefunGuide(SlimefunGuideMode.SURVIVAL_MODE).unlockItem(player, slimefunItem, player1 -> this.refresh(player, playerProfile, slimefunGuideMode));
                            } else {
                                this.refresh(player, playerProfile, slimefunGuideMode);
                                Slimefun.getLocalization().sendMessage(player, "messages.not-enough-xp", true);
                            }
                        } else {
                            GuideUtil.removeLastEntry(playerProfile.getGuideHistory());
                            this.open(player, playerProfile, slimefunGuideMode);
                        }
                    });
                }
            }
        }

        return virtualInventory;
    }

    @Nonnull
    private TypeItemGroup getByPage(int page) {
        if (this.pageMap.containsKey(page)) {
            return this.pageMap.get(page);
        } else {
            synchronized (this.pageMap.get(1)) {
                if (this.pageMap.containsKey(page)) {
                    return this.pageMap.get(page);
                }
                TypeItemGroup typeItemGroup = new TypeItemGroup(new NamespacedKey(JAVA_PLUGIN, this.getKey().getKey() + "_" + page), this.recipeType, page);
                typeItemGroup.pageMap = this.pageMap;
                this.pageMap.put(page, typeItemGroup);
                return typeItemGroup;
            }
        }
    }

    @Nonnull
    public static TypeItemGroup getByRecipeType(@Nonnull RecipeType recipeType) {
        return new TypeItemGroup(recipeType.getKey(), recipeType);
    }
}
