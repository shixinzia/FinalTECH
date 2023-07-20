package io.taraxacum.finaltech.core.item.machine.manual.process;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.common.util.StringNumberUtil;
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

public class StorageCardMergeTable extends AbstractProcessMachine {
    public StorageCardMergeTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public boolean canCraft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2) {
        if (!ItemStackUtil.isItemNull(itemStack1) && !ItemStackUtil.isItemNull(itemStack2) && FinalTechItems.STORAGE_CARD.verifyItem(itemStack1) && FinalTechItems.STORAGE_CARD.verifyItem(itemStack2)) {
            ItemStack stringItem1 = StringItemUtil.parseItemInCard(itemStack1);
            ItemStack stringItem2 = StringItemUtil.parseItemInCard(itemStack2);
            return !ItemStackUtil.isItemNull(stringItem1) && !ItemStackUtil.isItemNull(stringItem2) && ItemStackUtil.isItemSimilar(stringItem1, stringItem2);
        }
        return false;
    }

    @Override
    public boolean craft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2, @Nonnull Inventory inventory, int outputSlot, @Nonnull Player player, @Nonnull Location location) {
        if (!ItemStackUtil.isItemNull(itemStack1) && !ItemStackUtil.isItemNull(itemStack2) && itemStack1.hasItemMeta() && itemStack2.hasItemMeta()) {
            ItemMeta itemMeta1 = itemStack1.getItemMeta();
            ItemMeta itemMeta2 = itemStack2.getItemMeta();
            if (FinalTechItems.STORAGE_CARD.verifyItem(itemStack1) && FinalTechItems.STORAGE_CARD.verifyItem(itemStack2)) {
                ItemStack stringItem1 = StringItemUtil.parseItemInCard(itemMeta1);
                ItemStack stringItem2 = StringItemUtil.parseItemInCard(itemMeta2);
                if (!ItemStackUtil.isItemNull(stringItem1) && !ItemStackUtil.isItemNull(stringItem2) && ItemStackUtil.isItemSimilar(stringItem1, stringItem2)) {
                    String amount1 = StringItemUtil.parseAmountInCard(itemMeta1);
                    String amount2 = StringItemUtil.parseAmountInCard(itemMeta2);
                    ItemStack outputItem = FinalTechItems.STORAGE_CARD.getValidItem(stringItem1, StringNumberUtil.add(amount1, amount2));
                    FinalTechItems.STORAGE_CARD.updateLore(outputItem);
                    itemStack1.setAmount(itemStack1.getAmount() - 1);
                    itemStack2.setAmount(itemStack2.getAmount() - 1);
                    inventory.setItem(outputSlot, outputItem);
                    return true;
                }
            }
        }
        return false;
    }
}
