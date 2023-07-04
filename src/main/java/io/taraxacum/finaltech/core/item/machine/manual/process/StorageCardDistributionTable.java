package io.taraxacum.finaltech.core.item.machine.manual.process;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
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
import java.math.BigInteger;

public class StorageCardDistributionTable extends AbstractProcessMachine {
    public StorageCardDistributionTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public boolean canCraft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2) {
        if (ItemStackUtil.isItemNull(itemStack1) || ItemStackUtil.isItemNull(itemStack2)) {
            return false;
        }

        if (itemStack1.getAmount() > 1 || itemStack2.getAmount() > 1) {
            return false;
        }

        if (!FinalTechItems.STORAGE_CARD.verifyItem(itemStack1) || !FinalTechItems.STORAGE_CARD.verifyItem(itemStack2)) {
            return false;
        }

        ItemStack stringItem1 = StringItemUtil.parseItemInCard(itemStack1);
        ItemStack stringItem2 = StringItemUtil.parseItemInCard(itemStack2);

        if (ItemStackUtil.isItemNull(stringItem1) && ItemStackUtil.isItemNull(stringItem2)) {
            return false;
        }

        return ItemStackUtil.isItemNull(stringItem1) || ItemStackUtil.isItemNull(stringItem2);
    }

    @Override
    public boolean craft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2, @Nonnull Inventory inventory, int outputSlot, @Nonnull Player player, @Nonnull Location location) {
        if (ItemStackUtil.isItemNull(itemStack1) || ItemStackUtil.isItemNull(itemStack2)) {
            return false;
        }

        if (itemStack1.getAmount() > 1 || itemStack2.getAmount() > 1) {
            return false;
        }

        if (!FinalTechItems.STORAGE_CARD.verifyItem(itemStack1) || !FinalTechItems.STORAGE_CARD.verifyItem(itemStack2)) {
            return false;
        }

        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        ItemMeta itemMeta2 = itemStack2.getItemMeta();

        ItemStack stringItem1 = StringItemUtil.parseItemInCard(itemMeta1);
        ItemStack stringItem2 = StringItemUtil.parseItemInCard(itemMeta2);

        if (ItemStackUtil.isItemNull(stringItem1) && ItemStackUtil.isItemNull(stringItem2)) {
            return false;
        }

        ItemStack stringItem;
        if (ItemStackUtil.isItemNull(stringItem1)) {
            stringItem = stringItem2;
        } else if (ItemStackUtil.isItemNull(stringItem2)) {
            stringItem = stringItem1;
        } else {
            return false;
        }

        String amount1 = StringItemUtil.parseAmountInCard(itemMeta1);
        String amount2 = StringItemUtil.parseAmountInCard(itemMeta2);

        String amount = StringNumberUtil.add(amount1, amount2);
        String resultAmount1;
        String resultAmount2;
        if (StringNumberUtil.VALUE_INFINITY.equals(amount)) {
            resultAmount1 = StringNumberUtil.VALUE_INFINITY;
            resultAmount2 = StringNumberUtil.VALUE_INFINITY;
        } else {
            resultAmount1 = new BigInteger(amount).divide(new BigInteger("2")).toString();
            resultAmount2 = StringNumberUtil.sub(amount, resultAmount1);
        }

        StringItemUtil.setItemInCard(itemMeta1, stringItem, resultAmount1);
        StringItemUtil.setItemInCard(itemMeta2, stringItem, resultAmount2);

        FinalTechItems.STORAGE_CARD.updateLore(itemMeta1);
        FinalTechItems.STORAGE_CARD.updateLore(itemMeta2);

        itemStack1.setItemMeta(itemMeta1);
        itemStack2.setItemMeta(itemMeta2);

        if (FinalTech.getRandom().nextBoolean()) {
            ItemStack outputItem = ItemStackUtil.cloneItem(itemStack1);
            itemStack1.setAmount(itemStack1.getAmount() - 1);
            inventory.setItem(outputSlot, outputItem);
        } else {
            ItemStack outputItem = ItemStackUtil.cloneItem(itemStack2);
            itemStack2.setAmount(itemStack2.getAmount() - 1);
            inventory.setItem(outputSlot, outputItem);
        }

        return true;
    }
}
