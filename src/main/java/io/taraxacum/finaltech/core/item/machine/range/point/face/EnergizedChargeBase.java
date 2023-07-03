package io.taraxacum.finaltech.core.item.machine.range.point.face;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.unit.StatusInventory;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.slimefun.util.LocationDataUtil;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 */
public class EnergizedChargeBase extends AbstractFaceMachine implements RecipeItem, MenuUpdater {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final double efficiency = ConfigUtil.getOrDefaultItemSetting(0.25, this, "efficiency");
    private int statusSlot;

    public EnergizedChargeBase(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        StatusInventory statusInventory = new StatusInventory(this);
        this.statusSlot = statusInventory.statusSlot;
        return statusInventory;
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
        this.pointFunction(block, 1, location -> {
            LocationData tempLocationData = FinalTech.getLocationDataService().getLocationData(location);
            if (tempLocationData != null ) {
                String id = LocationDataUtil.getId(FinalTech.getLocationDataService(), tempLocationData);
                if (id != null && !this.notAllowedId.contains(id)) {
                    BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(id), () -> this.doCharge(block, tempLocationData), locationData.getLocation(), location);
                    return 0;
                }
            }

            Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
            if (inventory != null && !inventory.getViewers().isEmpty()) {
                this.updateInv(inventory, this.statusSlot, this,
                        "0",
                        "0");
            }
            return 0;
        });
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void doCharge(@Nonnull Block block, @Nonnull LocationData locationData) {
        int storedEnergy = 0;
        int chargeEnergy = 0;

        if (LocationDataUtil.getSlimefunItem(FinalTech.getLocationDataService(), locationData) instanceof EnergyNetComponent energyNetComponent
                && !JavaUtil.matchOnce(energyNetComponent.getEnergyComponentType(), EnergyNetComponentType.CAPACITOR, EnergyNetComponentType.GENERATOR)) {
            int capacity = energyNetComponent.getCapacity();
            storedEnergy = Integer.parseInt(EnergyUtil.getCharge(FinalTech.getLocationDataService(), locationData));
            chargeEnergy = Math.min(capacity - storedEnergy, (int)(capacity * EnergizedChargeBase.this.efficiency));
            if (chargeEnergy > 0) {
                storedEnergy += chargeEnergy;
                EnergyUtil.setCharge(FinalTech.getLocationDataService(), locationData, String.valueOf(storedEnergy));
            }
        }

        LocationData tempLocationData = FinalTech.getLocationDataService().getLocationData(block.getLocation());
        if(tempLocationData != null) {
            Inventory inventory = FinalTech.getLocationDataService().getInventory(tempLocationData);
            if (inventory != null && !inventory.getViewers().isEmpty()) {
                this.updateInv(inventory, this.statusSlot, this,
                        String.valueOf(storedEnergy),
                        String.valueOf(chargeEnergy));
            }
        }
    }

    @Nonnull
    @Override
    protected BlockFace getBlockFace() {
        return BlockFace.UP;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.efficiency));
    }
}
