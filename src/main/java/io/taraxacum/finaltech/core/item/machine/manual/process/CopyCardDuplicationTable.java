package io.taraxacum.finaltech.core.item.machine.manual.process;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
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

public class CopyCardDuplicationTable extends AbstractProcessMachine {
    public CopyCardDuplicationTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public boolean canCraft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2) {if (!ItemStackUtil.isItemNull(itemStack1) && FinalTechItems.COPY_CARD.verifyItem(itemStack1) && FinalTechItems.SHELL.verifyItem(itemStack2) && !ItemStackUtil.isItemNull(StringItemUtil.parseItemInCard(itemStack1))) {
        return true;
    } else return !ItemStackUtil.isItemNull(itemStack2) && FinalTechItems.COPY_CARD.verifyItem(itemStack2) && FinalTechItems.SHELL.verifyItem(itemStack1) && !ItemStackUtil.isItemNull(StringItemUtil.parseItemInCard(itemStack2));
    }

    @Override
    public boolean craft(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2, @Nonnull Inventory inventory, int outputSlot, @Nonnull Player player, @Nonnull Location location) {
        if (!ItemStackUtil.isItemNull(itemStack1) && FinalTechItems.COPY_CARD.verifyItem(itemStack1) && FinalTechItems.SHELL.verifyItem(itemStack2) && !ItemStackUtil.isItemNull(StringItemUtil.parseItemInCard(itemStack1))) {
            itemStack2.setAmount(itemStack2.getAmount() - 1);
            ItemStack outputItem = ItemStackUtil.cloneItem(itemStack1);
            outputItem.setAmount(1);
            inventory.setItem(outputSlot, outputItem);
            FinalTech.getLogService().subItem(FinalTechItems.SHELL.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, player, location, this.getAddon().getJavaPlugin());
            FinalTech.getLogService().addItem(FinalTechItems.COPY_CARD.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, player, location, this.getAddon().getJavaPlugin());
            return true;
        } else if (!ItemStackUtil.isItemNull(itemStack2) && FinalTechItems.COPY_CARD.verifyItem(itemStack2) && FinalTechItems.SHELL.verifyItem(itemStack1) && !ItemStackUtil.isItemNull(StringItemUtil.parseItemInCard(itemStack2))) {
            itemStack1.setAmount(itemStack1.getAmount() - 1);
            ItemStack outputItem = ItemStackUtil.cloneItem(itemStack2);
            outputItem.setAmount(1);
            inventory.setItem(outputSlot, outputItem);
            FinalTech.getLogService().subItem(FinalTechItems.SHELL.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, player, location, this.getAddon().getJavaPlugin());
            FinalTech.getLogService().addItem(FinalTechItems.COPY_CARD.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, player, location, this.getAddon().getJavaPlugin());
            return true;
        }
        return false;
    }
}
