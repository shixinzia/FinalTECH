package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import io.taraxacum.libs.slimefun.util.LocationDataUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class MatrixGenerator extends AbstractCubeElectricGenerator {
    private final int energy = ConfigUtil.getOrDefaultItemSetting(1, this, "energy");
    private final int range = ConfigUtil.getOrDefaultItemSetting(10, this, "range");

    public MatrixGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }
        boolean drawParticle = !inventory.getViewers().isEmpty();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        int range = 0;
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = inventory.getItem(slot);
            if (!ItemStackUtil.isItemNull(itemStack) && FinalTechItems.ITEM_PHONY.verifyItem(itemStack)) {
                for (int i = itemStack.getAmount(); i > 0; i /= 2) {
                    range++;
                }
            }
        }

        int count = this.cubeFunction(block, range + this.getRange(), location -> {
            if(!location.getChunk().isLoaded()) {
                return -1;
            }

            LocationData tempLocationData = FinalTech.getLocationDataService().getLocationData(location);
            if (tempLocationData != null
                    && !this.notAllowedId.contains(LocationDataUtil.getId(FinalTech.getLocationDataService(), tempLocationData))
                    && LocationDataUtil.getSlimefunItem(FinalTech.getLocationDataService(), tempLocationData) instanceof EnergyNetComponent energyNetComponent
                    && !JavaUtil.matchOnce(energyNetComponent.getEnergyComponentType(), EnergyNetComponentType.CAPACITOR, EnergyNetComponentType.GENERATOR)) {
                BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(LocationDataUtil.getId(FinalTech.getLocationDataService(), tempLocationData)), () -> this.chargeMachine(energyNetComponent, tempLocationData), location);
                if (drawParticle) {
                    Location cloneLocation = location.clone();
                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, cloneLocation.getBlock()));
                }
                return 1;
            }
            return 0;
        });

        if (drawParticle) {
            this.updateInv(inventory, StatusL2Menu.STATUS_SLOT, this,
                    String.valueOf(count),
                    String.valueOf(range + this.getRange()));
        }
    }

    private void chargeMachine(@Nonnull EnergyNetComponent energyNetComponent, @Nonnull LocationData locationData) {
        EnergyUtil.setCharge(FinalTech.getLocationDataService(), locationData, String.valueOf(energyNetComponent.getCapacity()));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range),
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }

    @Override
    protected int getEnergy() {
        return this.energy;
    }

    @Override
    protected int getRange() {
        return this.range;
    }
}
