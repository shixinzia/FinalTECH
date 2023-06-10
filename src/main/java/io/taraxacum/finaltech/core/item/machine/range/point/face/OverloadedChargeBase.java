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
import io.taraxacum.finaltech.core.interfaces.LocationMachine;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.slimefun.util.LocationDataUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 */
public class OverloadedChargeBase extends AbstractFaceMachine implements RecipeItem, MenuUpdater, LocationMachine {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final double efficiency = ConfigUtil.getOrDefaultItemSetting(0.1, this, "efficiency");
    private final double maxLimit = ConfigUtil.getOrDefaultItemSetting(2, this, "max-limit");

    public OverloadedChargeBase(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
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

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        this.pointFunction(block, 1, location -> {
            LocationData tempLocationData = FinalTech.getLocationDataService().getLocationData(location);
            if (tempLocationData != null && !notAllowedId.contains(LocationDataUtil.getId(FinalTech.getLocationDataService(), tempLocationData))) {
                BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(LocationDataUtil.getId(FinalTech.getLocationDataService(), tempLocationData)), () -> OverloadedChargeBase.this.doCharge(block, tempLocationData), location);
                return 0;
            }
            Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
            if (inventory != null && !inventory.getViewers().isEmpty()) {
                this.updateInv(inventory, StatusMenu.STATUS_SLOT, this,
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
            int maxValue = capacity < Integer.MAX_VALUE / OverloadedChargeBase.this.maxLimit ? (int)(capacity * OverloadedChargeBase.this.maxLimit) : Integer.MAX_VALUE;
            storedEnergy = Integer.parseInt(EnergyUtil.getCharge(FinalTech.getLocationDataService(), locationData));
            chargeEnergy = storedEnergy < maxValue - capacity * OverloadedChargeBase.this.efficiency ? (int)(capacity * OverloadedChargeBase.this.efficiency) : (maxValue - storedEnergy);
            if (chargeEnergy > 0) {
                storedEnergy += chargeEnergy;
                EnergyUtil.setCharge(FinalTech.getLocationDataService(), locationData, String.valueOf(storedEnergy));
            }
        }

        LocationData tempLocationData = FinalTech.getLocationDataService().getLocationData(block.getLocation());
        if(tempLocationData != null) {
            Inventory inventory = FinalTech.getLocationDataService().getInventory(tempLocationData);
            if (inventory != null && !inventory.getViewers().isEmpty()) {
                this.updateInv(inventory, StatusMenu.STATUS_SLOT, this,
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
                String.valueOf(this.efficiency),
                String.valueOf(this.maxLimit * 100));
    }
    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        return new Location[] {this.getTargetLocation(sourceLocation, 1)};
    }
}
