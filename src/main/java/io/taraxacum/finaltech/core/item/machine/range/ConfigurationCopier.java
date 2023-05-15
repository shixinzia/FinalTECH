package io.taraxacum.finaltech.core.item.machine.range;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.*;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ConfigurationWorkerMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.util.LocationDataUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Final_ROOT
 */
public class ConfigurationCopier extends AbstractRangeMachine implements RecipeItem, PointMachine, LineMachine, LocationMachine {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final int range = ConfigUtil.getOrDefaultItemSetting(16, this, "range");

    public ConfigurationCopier(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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

    @Nullable
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new ConfigurationWorkerMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }
        boolean hasViewer = !inventory.getViewers().isEmpty();

        if(!ItemStackUtil.isItemNull(inventory.getItem(this.getOutputSlot()[0]))) {
            return;
        }

        ItemStack itemConfigurator = inventory.getItem(this.getInputSlot()[0]);
        SlimefunItem machineConfigurator = SlimefunItem.getByItem(itemConfigurator);
        if(machineConfigurator == null || !FinalTechItemStacks.MACHINE_CONFIGURATOR.getItemId().equals(machineConfigurator.getId())) {
            return;
        }

        ItemStack digitalItemStack = inventory.getItem(ConfigurationWorkerMenu.DIGITAL_SLOT);
        int digital = SlimefunItem.getByItem(digitalItemStack) instanceof DigitalItem digitalItem ? digitalItem.getDigit() : 0;

        BlockData blockData = block.getState().getBlockData();
        if(blockData instanceof Directional directional) {
            Runnable runnable = () -> {
                ItemStack outputItem = ItemStackUtil.cloneItem(itemConfigurator, 1);
                AtomicBoolean atomicBoolean = new AtomicBoolean(false);

                RangeFunction rangeFunction = location -> {
                    if(atomicBoolean.get() || !location.getChunk().isLoaded()) {
                        return -1;
                    }
                    LocationData tempLocationData = FinalTech.getLocationDataService().getLocationData(location);
                    if(tempLocationData != null) {
                        if(ConfigurationCopier.this.notAllowedId.contains(LocationDataUtil.getId(FinalTech.getLocationDataService(), tempLocationData))) {
                            return -1;
                        }

                        if(ItemConfigurationUtil.saveConfigToItem(outputItem, FinalTech.getLocationDataService(), tempLocationData)) {
                            SlimefunItem sfItem = LocationDataUtil.getSlimefunItem(FinalTech.getLocationDataService(), tempLocationData);
                            if(sfItem != null) {
                                ItemStackUtil.setLore(outputItem, sfItem.getItemName());
                            }

                            if (hasViewer) {
                                JavaPlugin javaPlugin = ConfigurationCopier.this.getAddon().getJavaPlugin();
                                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, tempLocationData.getLocation().getBlock()));
                            }
                        }
                        atomicBoolean.set(true);
                        return 1;
                    }

                    return 0;
                };

                int result = digital == 0 ? this.lineFunction(block, this.range, rangeFunction) : this.pointFunction(block, digital, rangeFunction);

                if(InventoryUtil.tryPushAllItem(inventory, this.getOutputSlot(), outputItem)) {
                    itemConfigurator.setAmount(itemConfigurator.getAmount() - 1);
                }
            };

            BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(this.getId()), runnable, () -> {
                if(digital > 0) {
                    return new Location[] {block.getLocation(), block.getRelative(directional.getFacing(), digital).getLocation()};
                } else if(digital == 0) {
                    return this.getLocations(block.getLocation());
                } else {
                    return new Location[0];
                }
            });
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

    @Nonnull
    @Override
    public Location getTargetLocation(@Nonnull Location location, int range) {
        Block block = location.getBlock();
        BlockData blockData = block.getBlockData();
        return blockData instanceof Directional directional ? block.getRelative(directional.getFacing().getOppositeFace(), range).getLocation() : location;
    }

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        Block block = sourceLocation.getBlock();
        BlockData blockData = block.getBlockData();
        if(blockData instanceof Directional directional) {
            BlockFace blockFace = directional.getFacing();
            Location[] locations = new Location[this.range + 1];
            int i = 0;
            locations[i++] = sourceLocation;
            while (i < locations.length) {
                locations[i++] = block.getRelative(blockFace, i - 1).getLocation();
            }
            return locations;
        }
        return new Location[0];
    }
}
