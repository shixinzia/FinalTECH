package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class TimeCapacitor extends AbstractMachine implements EnergyNetComponent, RecipeItem, MenuUpdater {
    private final String key = "t";
    private final int interval = ConfigUtil.getOrDefaultItemSetting(1600, this, "interval");
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(16777216, this, "capacity");

    public TimeCapacitor(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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
        Location location = locationData.getLocation();
        World world = location.getWorld();
        String chargeStr = EnergyUtil.getCharge(FinalTech.getLocationDataService(), locationData);
        int charge = Integer.parseInt(chargeStr);

        if(world != null) {
            long time = world.getTime() / this.interval;
            String oldTime = FinalTech.getLocationDataService().getLocationData(locationData, this.key);
            if(oldTime == null) {
                FinalTech.getLocationDataService().setLocationData(locationData, this.key, String.valueOf(time));
            } else if (!oldTime.equals(String.valueOf(time))) {
                charge *= 2;
                FinalTech.getLocationDataService().setLocationData(locationData, this.key, String.valueOf(time));
            }
        }

        charge = charge > this.capacity ? 0 : charge;
        chargeStr = String.valueOf(charge);
        EnergyUtil.setCharge(FinalTech.getLocationDataService(), locationData, chargeStr);

        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory != null && !inventory.getViewers().isEmpty()) {
            this.updateInv(inventory, StatusMenu.STATUS_SLOT, this,
                    chargeStr);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CAPACITOR;
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
