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
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.dto.SimpleVirtualInventory;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.util.GuideUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class SlimefunItemSmallRecipeMenu extends SimpleVirtualInventory {
    private final int backSlot = 1;
    private final int recipeType = 10;
    private final int recipeResult = 16;
    private final int[] recipeContent = new int[] {3, 4, 5, 12, 13, 14, 21, 22, 23};
    private final int infoSlot = 9;

    private final int[] border = new int[] {27, 29, 30, 31, 32, 33, 35};
    private final int previousSlot =  28;
    private final int nextSlot = 34;
    private final int[] workContent = new int[] {36, 45, 37, 46, 38, 47, 39, 48, 40, 49, 41, 50, 42, 51, 43, 52, 44, 53};

    private final Player player;
    private final PlayerProfile playerProfile;
    private final SlimefunGuideMode slimefunGuideMode;
    private final SlimefunItem slimefunItem;

    public SlimefunItemSmallRecipeMenu(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode, @Nonnull SlimefunItem slimefunItem, @Nonnull ItemGroup itemGroup) {
        this(player, playerProfile, slimefunGuideMode, slimefunItem, itemGroup, 0);
    }

    public SlimefunItemSmallRecipeMenu(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode, @Nonnull SlimefunItem slimefunItem, @Nonnull ItemGroup itemGroup, int page) {
        super(slimefunItem instanceof RecipeDisplayItem recipeDisplayItem && recipeDisplayItem.getDisplayRecipes().size() > 0 ? 54 : 27, slimefunItem.getItemName());
        this.player = player;
        this.playerProfile = playerProfile;
        this.slimefunGuideMode = slimefunGuideMode;
        this.slimefunItem = slimefunItem;

        this.setAllowClickPlayerInventory(false);

        for (int slot : JavaUtil.generateInts(this.getSize())) {
            this.setOnClick(slot, CANCEL_CLICK_CONSUMER);
        }

        this.setOnOpen(inventoryOpenEvent -> player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1));

        this.getInventory().setItem(this.backSlot, ChestMenuUtils.getBackButton(player));
        this.setOnClick(this.backSlot, inventoryClickEvent -> {
            inventoryClickEvent.setCancelled(true);

            GuideHistory guideHistory = playerProfile.getGuideHistory();
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
            if (!ItemStackUtil.isItemNull(icon)) {
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

        this.setupWorkContent(page);
    }

    private void setupWorkContent(int page) {
        if (this.slimefunItem instanceof RecipeDisplayItem recipeDisplayItem) {
            List<ItemStack> displayRecipeItemList = recipeDisplayItem.getDisplayRecipes();
            if (!displayRecipeItemList.isEmpty()) {
                for (int slot : this.border) {
                    this.getInventory().setItem(slot, ChestMenuUtils.getBackground());
                }

            }

            List<ItemStack> displayRecipes = recipeDisplayItem.getDisplayRecipes();

            this.getInventory().setItem(this.previousSlot, ChestMenuUtils.getPreviousButton(this.player, page, (displayRecipes.size() - 1) / this.workContent.length + 1));
            this.setOnClick(this.previousSlot, inventoryClickEvent -> {
                inventoryClickEvent.setCancelled(true);

                RecipeItemGroup recipeItemGroup = RecipeItemGroup.getByItemStack(this.player, this.playerProfile, this.slimefunGuideMode, this.slimefunItem.getItem(), Math.max(page - 1, 1));
                if (recipeItemGroup != null) {
                    GuideUtil.removeLastEntry(this.playerProfile.getGuideHistory());
                    recipeItemGroup.open(this.player, this.playerProfile, this.slimefunGuideMode);
                }
            });

            this.getInventory().setItem(this.nextSlot, ChestMenuUtils.getNextButton(this.player, page, (displayRecipes.size() - 1) / this.workContent.length + 1));
            this.setOnClick(this.nextSlot, inventoryClickEvent -> {
                inventoryClickEvent.setCancelled(true);

                RecipeItemGroup recipeItemGroup = RecipeItemGroup.getByItemStack(this.player, this.playerProfile, this.slimefunGuideMode, this.slimefunItem.getItem(), Math.min(page + 1, (displayRecipes.size() - 1) / this.workContent.length + 1));
                if (recipeItemGroup != null) {
                    GuideUtil.removeLastEntry(this.playerProfile.getGuideHistory());
                    recipeItemGroup.open(this.player, this.playerProfile, this.slimefunGuideMode);
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
                    this.getInventory().setItem(this.workContent[i], MachineUtil.cloneAsDescriptiveItem(icon));
                    this.setOnClick(this.workContent[i], inventoryClickEvent -> {
                        inventoryClickEvent.setCancelled(true);

                        if (slimefunItem != null && this.playerProfile.hasUnlocked(slimefunItem.getResearch())) {
                            RecipeItemGroup recipeItemGroup = RecipeItemGroup.getByItemStack(this.player, this.playerProfile, this.slimefunGuideMode, itemStack);
                            if (recipeItemGroup != null) {
                                recipeItemGroup.open(this.player, this.playerProfile, this.slimefunGuideMode);
                            }
                        }
                    });
                } else {
                    this.getInventory().setItem(this.workContent[i], null);
                    this.setOnClick(this.workContent[i], CANCEL_CLICK_CONSUMER);
                }
            }
        }
    }
}
