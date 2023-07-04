package io.taraxacum.finaltech.core.item.machine.manual.process;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.item.unusable.CopyCard;
import io.taraxacum.finaltech.core.item.unusable.StorageCard;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class EntropyCraftTable extends AbstractProcessMachine {
    public EntropyCraftTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public boolean canCraft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2) {
        if(ItemStackUtil.isItemNull(itemStack1) || ItemStackUtil.isItemNull(itemStack2)) {
            return false;
        }

        ItemStack itemEntropy = null;
        ItemStack itemCard = null;
        if(ItemStackUtil.isItemSimilar(itemStack1, FinalTechItemStacks.ENTROPY)) {
            itemEntropy = itemStack1;
        } else if(FinalTechItems.COPY_CARD.verifyItem(itemStack1) || FinalTechItems.STORAGE_CARD.verifyItem(itemStack1)) {
            itemCard = itemStack1;
        }

        if(itemEntropy == null && itemCard == null) {
            return false;
        }

        if(itemEntropy == null && ItemStackUtil.isItemSimilar(itemStack2, FinalTechItemStacks.ENTROPY)) {
            itemEntropy = itemStack2;
        } else if(itemCard == null && (FinalTechItems.COPY_CARD.verifyItem(itemStack2) || FinalTechItems.STORAGE_CARD.verifyItem(itemStack2))) {
            itemCard = itemStack2;
        }

        return itemEntropy != null && itemCard != null && !ItemStackUtil.isItemNull(StringItemUtil.parseItemInCard(itemCard));
    }

    @Override
    public boolean craft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2, @Nonnull Inventory inventory, int outputSlot, @Nonnull Player player, @Nonnull Location location) {
        if(ItemStackUtil.isItemNull(itemStack1) || ItemStackUtil.isItemNull(itemStack2)) {
            return false;
        }

        ItemStack itemEntropy = null;
        ItemStack itemCard = null;
        if(ItemStackUtil.isItemSimilar(itemStack1, FinalTechItemStacks.ENTROPY)) {
            itemEntropy = itemStack1;
        } else if(FinalTechItems.COPY_CARD.verifyItem(itemStack1) || FinalTechItems.STORAGE_CARD.verifyItem(itemStack1)) {
            itemCard = itemStack1;
        }

        if(itemEntropy == null && itemCard == null) {
            return false;
        }

        if(itemEntropy == null && ItemStackUtil.isItemSimilar(itemStack2, FinalTechItemStacks.ENTROPY)) {
            itemEntropy = itemStack2;
        } else if(itemCard == null && (FinalTechItems.COPY_CARD.verifyItem(itemStack2) || FinalTechItems.STORAGE_CARD.verifyItem(itemStack2))) {
            itemCard = itemStack2;
        }

        if(itemEntropy == null || itemCard == null) {
            return false;
        }

        ItemMeta itemMeta = itemCard.getItemMeta();
        String amount = StringItemUtil.parseAmountInCard(itemMeta);

        ItemStack outputItem;
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemCard);
        if(slimefunItem instanceof StorageCard storageCard) {
            BigInteger bigInteger = new BigDecimal(amount).sqrt(MathContext.DECIMAL32).toBigInteger();
            outputItem = storageCard.getValidItem(new ItemStack(FinalTechItemStacks.ENTROPY), bigInteger.toString());
        } else if(slimefunItem instanceof CopyCard copyCard) {
            outputItem = copyCard.getValidItem(new ItemStack(FinalTechItemStacks.ENTROPY), amount);
        } else {
            return false;
        }

        inventory.setItem(outputSlot, outputItem);
        itemCard.setAmount(itemCard.getAmount() - 1);
        itemEntropy.setAmount(itemEntropy.getAmount() - 1);

        return true;
    }
}
