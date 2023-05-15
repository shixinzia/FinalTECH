package io.taraxacum.finaltech.core.item.machine.electric;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import io.taraxacum.libs.slimefun.util.LocationDataUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class VariableWireResistance extends AbstractElectricMachine implements RecipeItem, MenuUpdater {
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(65536, this, "capacity");
    private final String capacityString = String.valueOf(this.capacity);

    public VariableWireResistance(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Location location = block.getLocation();
        String charge = EnergyUtil.getCharge(FinalTech.getLocationDataService(), locationData);
        if (this.capacityString.equals(charge)) {
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            Runnable runnable = () -> {
                FinalTech.getLocationDataService().clearLocationData(location);
                if (FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
                    LocationData tempLocationData = slimefunLocationDataService.getOrCreateEmptyLocationData(location, FinalTechItems.VARIABLE_WIRE_CAPACITOR.getId());
                    slimefunLocationDataService.setLocationData(tempLocationData, ConstantTableUtil.CONFIG_CHARGE, charge);
                    Slimefun.getNetworkManager().updateAllNetworks(location);
                    javaPlugin.getServer().getScheduler().runTaskLater(javaPlugin, () -> {
                        LocationData tempLocationData2 = FinalTech.getLocationDataService().getLocationData(location);
                        if(!location.getBlock().getType().isAir()
                                && tempLocationData2 != null
                                && FinalTechItems.VARIABLE_WIRE_CAPACITOR.getId().equals(LocationDataUtil.getId(FinalTech.getLocationDataService(), tempLocationData2))) {
                            block.setType(FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR.getType());
                        }
                    }, 0);
                }
            };

            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(this.getId()), runnable, location));
        } else {
            Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
            if (inventory != null && !inventory.getViewers().isEmpty()) {
                this.updateInv(inventory, StatusMenu.STATUS_SLOT, this, charge);
            }
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.capacity));
    }
}
