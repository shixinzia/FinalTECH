package io.taraxacum.finaltech.core.inventory.common;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.core.group.CraftItemGroup;
import io.taraxacum.finaltech.core.group.RecipeItemGroup;
import io.taraxacum.finaltech.core.group.TypeItemGroup;
import io.taraxacum.finaltech.core.option.Icon;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.dto.SimpleVirtualInventory;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.util.GuideUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class SlimefunItemBigRecipeMenu extends SimpleVirtualInventory {
    private final int backSlot = 1;
    private final int recipeType = 10;
    private final int recipeResult = 37;
    private final int[] recipeContent = new int[] {3, 4, 5, 6, 7, 8, 12, 13, 14, 15, 16, 17, 21, 22, 23, 24, 25, 26, 30, 31, 32, 33, 34, 35, 39, 40, 41, 42, 43, 44, 48, 49, 50, 51, 52, 53};
    private final int[] border = new int[] {0, 2, 11, 18, 19, 20, 27, 28, 29, 36, 38, 45, 47};
    private final int infoSlot = 9;

    private final int workButton = 46;

    private final int workBackSlot = 0;
    private final int workPreviousSlot = 1;
    private final int workNextSlot = 7;
    private final int[] workBorder = new int[] {2, 3, 4, 5, 6, 8};
    private final int[] workContent = new int[] {9, 18, 10, 19, 11, 20, 12, 21, 13, 22, 14, 23, 15, 24, 16, 25, 17, 26};

    private final Player player;
    private final PlayerProfile playerProfile;
    private final SlimefunGuideMode slimefunGuideMode;
    private final SlimefunItem slimefunItem;
    private final ItemGroup itemGroup;

    public SlimefunItemBigRecipeMenu(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode, @Nonnull SlimefunItem slimefunItem, @Nonnull ItemGroup itemGroup) {
        this(player, playerProfile, slimefunGuideMode, slimefunItem, itemGroup, 1);
    }

    public SlimefunItemBigRecipeMenu(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode, @Nonnull SlimefunItem slimefunItem, @Nonnull ItemGroup itemGroup, int page) {
        super(54, slimefunItem.getItemName());
        this.player = player;
        this.playerProfile = playerProfile;
        this.slimefunGuideMode = slimefunGuideMode;
        this.slimefunItem = slimefunItem;
        this.itemGroup = itemGroup;

        this.setAllowClickPlayerInventory(false);

        for (int slot : JavaUtil.generateInts(this.getSize())) {
            this.setOnClick(slot, CANCEL_CLICK_CONSUMER);
        }

        this.setOnOpen(inventoryOpenEvent -> player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1));

        GuideHistory guideHistory = playerProfile.getGuideHistory();

        this.getInventory().setItem(this.backSlot, ChestMenuUtils.getBackButton(player));
        this.setOnClick(this.backSlot, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            if (inventoryClickEvent.getClick().isShiftClick()) {
                SlimefunGuide.openMainMenu(playerProfile, slimefunGuideMode, guideHistory.getMainMenuPage());
            } else {
                guideHistory.goBack(Slimefun.getRegistry().getSlimefunGuide(SlimefunGuideMode.SURVIVAL_MODE));
            }
        });

        this.getInventory().setItem(this.recipeType, MachineUtil.cloneAsDescriptiveItem(slimefunItem.getRecipeType().toItem()));
        this.setOnClick(this.recipeType, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            TypeItemGroup typeItemGroup = TypeItemGroup.getByRecipeType(slimefunItem.getRecipeType());
            typeItemGroup.open(player, playerProfile, slimefunGuideMode);
        });

        this.getInventory().setItem(this.recipeResult, MachineUtil.cloneAsDescriptiveItem(slimefunItem.getRecipeOutput()));
        this.setOnClick(this.recipeResult, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            CraftItemGroup craftItemGroup = CraftItemGroup.getBySlimefunItem(slimefunItem);
            craftItemGroup.open(player, playerProfile, slimefunGuideMode);
        });

        for (int i = 0; i < slimefunItem.getRecipe().length; i++) {
            ItemStack itemStack = slimefunItem.getRecipe()[i];
            ItemStack icon = itemStack;
            SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
            if(sfItem != null && !this.playerProfile.hasUnlocked(sfItem.getResearch())) {
                icon = ChestMenuUtils.getNotResearchedItem();
            }
            if(!ItemStackUtil.isItemNull(icon)) {
                this.getInventory().setItem(this.recipeContent[i], MachineUtil.cloneAsDescriptiveItem(icon));
            }
            this.setOnClick(this.recipeContent[i], inventoryClickEvent -> {
                inventoryClickEvent.setCancelled(true);

                if (sfItem != null && playerProfile.hasUnlocked(sfItem.getResearch())) {
                    RecipeItemGroup recipeItemGroup = RecipeItemGroup.getByItemStack(player, playerProfile, slimefunGuideMode, itemStack);
                    if (recipeItemGroup != null) {
                        recipeItemGroup.open(player, playerProfile, slimefunGuideMode);
                    }
                }
            });
        }

        this.getInventory().setItem(this.infoSlot, RecipeItemGroup.generateInfoIcon(slimefunItem, player));

        for (int slot : this.border) {
            this.getInventory().setItem(slot, ChestMenuUtils.getBackground());
        }

        if (slimefunItem instanceof RecipeDisplayItem recipeDisplayItem && recipeDisplayItem.getDisplayRecipes().size() > 0) {
            this.getInventory().setItem(this.workButton, Icon.RECIPE_ICON);
            this.setOnClick(this.workButton, inventoryClickEvent -> {
                inventoryClickEvent.setCancelled(true);

                SimpleVirtualInventory simpleVirtualInventory = this.setupWorkContent(page);
                if (simpleVirtualInventory != null) {
                    simpleVirtualInventory.open(player);
                }
            });
        } else {
            this.getInventory().setItem(this.workButton, ChestMenuUtils.getBackground());
        }
    }

    @Nullable
    private SimpleVirtualInventory setupWorkContent(int page) {
        if (this.slimefunItem instanceof RecipeDisplayItem recipeDisplayItem) {
            SimpleVirtualInventory simpleVirtualInventory = new SimpleVirtualInventory(27, this.slimefunItem.getItemName());

            for (int slot : this.workBorder) {
                simpleVirtualInventory.getInventory().setItem(slot, ChestMenuUtils.getBackground());
                simpleVirtualInventory.setOnClick(slot, CANCEL_CLICK_CONSUMER);
            }

            List<ItemStack> displayRecipes = recipeDisplayItem.getDisplayRecipes();

            simpleVirtualInventory.getInventory().setItem(this.workBackSlot, ChestMenuUtils.getBackButton(this.player));
            simpleVirtualInventory.setOnClick(this.workBackSlot, inventoryClickEvent -> {
                inventoryClickEvent.setCancelled(true);
                
                GuideHistory guideHistory = this.playerProfile.getGuideHistory();
                GuideUtil.removeLastEntry(guideHistory);
                if (this.itemGroup instanceof RecipeItemGroup recipeItemGroup) {
                    recipeItemGroup.open(this.player, this.playerProfile, this.slimefunGuideMode);
                }
            });

            simpleVirtualInventory.getInventory().setItem(this.workPreviousSlot, ChestMenuUtils.getPreviousButton(this.player, page, (displayRecipes.size() - 1) / this.workContent.length + 1));
            simpleVirtualInventory.setOnClick(this.workPreviousSlot, inventoryClickEvent -> {
                inventoryClickEvent.setCancelled(true);
                
                SimpleVirtualInventory virtualInventory = this.setupWorkContent(Math.max(page - 1, 1));
                if (virtualInventory != null) {
                    virtualInventory.open(this.player);
                }
            });

            simpleVirtualInventory.getInventory().setItem(this.workNextSlot, ChestMenuUtils.getNextButton(this.player, page, (displayRecipes.size() - 1) / this.workContent.length + 1));
            simpleVirtualInventory.setOnClick(this.workNextSlot, inventoryClickEvent -> {
                inventoryClickEvent.setCancelled(true);
                
                SimpleVirtualInventory virtualInventory = this.setupWorkContent(Math.min(page + 1, (displayRecipes.size() - 1) / workContent.length + 1));
                if (virtualInventory != null) {
                    virtualInventory.open(this.player);
                }
            });

            int i;
            for (i = 0; i < this.workContent.length; i++) {
                int index = i + page * this.workContent.length - this.workContent.length;
                if (index < displayRecipes.size()) {
                    ItemStack itemStack = displayRecipes.get(index);
                    ItemStack icon = itemStack;
                    SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
                    if(slimefunItem != null && !this.playerProfile.hasUnlocked(slimefunItem.getResearch())) {
                        icon = ChestMenuUtils.getNotResearchedItem();
                    }
                    simpleVirtualInventory.getInventory().setItem(workContent[i], MachineUtil.cloneAsDescriptiveItem(icon));
                    simpleVirtualInventory.setOnClick(workContent[i], inventoryClickEvent -> {
                        inventoryClickEvent.setCancelled(true);

                        if (slimefunItem != null && this.playerProfile.hasUnlocked(slimefunItem.getResearch())) {
                            RecipeItemGroup recipeItemGroup = RecipeItemGroup.getByItemStack(this.player, this.playerProfile, this.slimefunGuideMode, itemStack);
                            if (recipeItemGroup != null) {
                                recipeItemGroup.open(this.player, this.playerProfile, this.slimefunGuideMode);
                            }
                        }
                    });
                } else {
                    simpleVirtualInventory.getInventory().setItem(this.workContent[i], null);
                    simpleVirtualInventory.setOnClick(this.workContent[i], CANCEL_CLICK_CONSUMER);
                }
            }

            return simpleVirtualInventory;
        }
        return null;
    }
}
