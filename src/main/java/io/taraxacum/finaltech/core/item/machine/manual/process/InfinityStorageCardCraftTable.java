package io.taraxacum.finaltech.core.item.machine.manual.process;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.enums.LogSourceType;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfinityStorageCardCraftTable extends AbstractProcessMachine {
    public InfinityStorageCardCraftTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public boolean canCraft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2) {
        if (ItemStackUtil.isItemNull(itemStack1) || ItemStackUtil.isItemNull(itemStack2)) {
            return false;
        }

        ItemStack itemPhony = null;
        ItemStack itemCopyCard = null;
        if (FinalTechItems.ITEM_PHONY.verifyItem(itemStack1)) {
            itemPhony = itemStack1;
        } else if (FinalTechItems.COPY_CARD.verifyItem(itemStack1)) {
            itemCopyCard = itemStack1;
        }

        if (itemPhony == null && itemCopyCard == null) {
            return false;
        }

        if (itemPhony == null && FinalTechItems.ITEM_PHONY.verifyItem(itemStack2)) {
            itemPhony = itemStack2;
        } else if (itemCopyCard == null && FinalTechItems.COPY_CARD.verifyItem(itemStack2)) {
            itemCopyCard = itemStack2;
        }

        return itemPhony != null && itemCopyCard != null && !ItemStackUtil.isItemNull(StringItemUtil.parseItemInCard(itemCopyCard));
    }

    @Override
    public boolean craft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2, @Nonnull Inventory inventory, int outputSlot, @Nonnull Player player, @Nonnull Location location) {
        if (ItemStackUtil.isItemNull(itemStack1) || ItemStackUtil.isItemNull(itemStack2)) {
            return false;
        }

        ItemStack itemPhony = null;
        ItemStack itemCopyCard = null;
        if (FinalTechItems.ITEM_PHONY.verifyItem(itemStack1)) {
            itemPhony = itemStack1;
        } else if (FinalTechItems.COPY_CARD.verifyItem(itemStack1)) {
            itemCopyCard = itemStack1;
        }

        if (itemPhony == null && itemCopyCard == null) {
            return false;
        }

        if (itemPhony == null && FinalTechItems.ITEM_PHONY.verifyItem(itemStack2)) {
            itemPhony = itemStack2;
        } else if (itemCopyCard == null && FinalTechItems.COPY_CARD.verifyItem(itemStack2)) {
            itemCopyCard = itemStack2;
        }

        if (itemPhony == null || itemCopyCard == null) {
            return false;
        }

        ItemStack stringItem = StringItemUtil.parseItemInCard(itemCopyCard);
        if (ItemStackUtil.isItemNull(stringItem)) {
            return false;
        }

        ItemStack outputItem = FinalTechItems.STORAGE_CARD.getValidItem(stringItem, StringNumberUtil.VALUE_INFINITY);

        itemPhony.setAmount(itemPhony.getAmount() - 1);
        itemCopyCard.setAmount(itemCopyCard.getAmount() - 1);
        inventory.setItem(outputSlot, outputItem);

        FinalTech.getLogService().subItem(FinalTechItems.COPY_CARD.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, player, location, this.getAddon().getJavaPlugin());
        FinalTech.getLogService().subItem(FinalTechItems.ITEM_PHONY.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, player, location, this.getAddon().getJavaPlugin());

        return true;
    }
}
