package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.simple.EnergyOutputTableInventory;
import io.taraxacum.finaltech.core.item.usable.EnergyCard;
import io.taraxacum.finaltech.core.item.usable.PortableEnergyStorage;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;

/**
 * @author Final_ROOT
 */
public class EnergyOutputTable extends AbstractMachine implements RecipeItem {
    public EnergyOutputTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        return new EnergyOutputTableInventory(this);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(FinalTech.getLocationDataService(), this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null || InventoryUtil.slotCount(inventory, this.getOutputSlot()) == this.getOutputSlot().length) {
            return;
        }

        ItemStack energyStorageItem = inventory.getItem(this.getInputSlot()[0]);
        if(ItemStackUtil.isItemNull(energyStorageItem)
                || energyStorageItem.getAmount() > 1
                || energyStorageItem.getMaxStackSize() > 1
                || !(SlimefunItem.getByItem(energyStorageItem) instanceof PortableEnergyStorage portableEnergyStorage)) {
            return;
        }

        String energy = portableEnergyStorage.getEnergy(energyStorageItem);

        boolean update = false;
        for(int slot : this.getOutputSlot()) {
            ItemStack itemStack = inventory.getItem(slot);
            if(ItemStackUtil.isItemNull(itemStack)) {
                EnergyCard energyCard = EnergyCard.getByEnergy(energy);
                if(energyCard != null && StringNumberUtil.compare(energy, energyCard.getEnergy()) >= 0) {
                    String count = new BigInteger(energy).divide(new BigInteger(energyCard.getEnergy())).toString();
                    count = StringNumberUtil.min(count, String.valueOf(energyCard.getItem().getMaxStackSize()));
                    if(InventoryUtil.tryPushAllItem(inventory, this.getOutputSlot(), Integer.parseInt(count), energyCard.getItem())) {
                        energy = StringNumberUtil.sub(energy, StringNumberUtil.mul(energyCard.getEnergy(), count));
                        update = true;
                    }
                }
            }
        }

        if(update) {
            portableEnergyStorage.setEnergy(energyStorageItem, energy);
            portableEnergyStorage.updateLore(energyStorageItem);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
