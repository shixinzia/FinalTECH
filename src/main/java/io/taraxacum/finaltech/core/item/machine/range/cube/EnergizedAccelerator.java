package io.taraxacum.finaltech.core.item.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
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
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.util.LocationDataUtil;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Final_ROOT
 */
public class EnergizedAccelerator extends AbstractCubeMachine implements EnergyNetComponent, RecipeItem, MenuUpdater, LocationMachine {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final Set<String> allowedId = new HashSet<>();
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(20000000, this, "capacity");

    public EnergizedAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.notAllowedId.add(this.getId());
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler();
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }
        boolean hasViewer = !inventory.getViewers().isEmpty();
        Location blockLocation = locationData.getLocation();

        int machineEnergy = Integer.parseInt(EnergyUtil.getCharge(FinalTech.getLocationDataService(), locationData));
        if (machineEnergy == 0) {
            if(hasViewer) {
                this.updateInv(inventory, StatusMenu.STATUS_SLOT, this,
                        String.valueOf(machineEnergy), "0", "0", "0");
            }
            return;
        }

        AtomicLong allCapacity = new AtomicLong(0);
        Map<Integer, List<LocationData>> locationDataMap = new HashMap<>(this.range * 3);
        int count = this.cubeFunction(block, this.range, location -> {
            if(!location.getChunk().isLoaded()) {
                return -1;
            }
            LocationData tempLocationData = FinalTech.getLocationDataService().getLocationData(location);
            if(tempLocationData != null && this.calAllowed(LocationDataUtil.getSlimefunItem(FinalTech.getLocationDataService(), tempLocationData))) {
                int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                locationDataMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2)).add(tempLocationData);
                tempLocationData.cloneLocation();
                allCapacity.getAndAdd(((EnergyNetComponent) LocationDataUtil.getSlimefunItem(FinalTech.getLocationDataService(), tempLocationData)).getCapacity());
                return 1;
            }
            return 0;
        });

        if (count == 0 || allCapacity.get() == 0L) {
            if(hasViewer) {
                this.updateInv(inventory, StatusMenu.STATUS_SLOT, this,
                        String.valueOf(machineEnergy), "0", "0", "0");
            }
            return;
        }

        if(allCapacity.get() > this.capacity) {
            allCapacity.set(this.capacity);
        }

        int storedMachineEnergy = machineEnergy;

        int accelerateTotalTime = 0;
        int accelerateRoundTime = 0;

        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        int nextEnergy;
        List<LocationData> locationDataList;
        while (machineEnergy > allCapacity.get()) {
            for (int distance = 1; distance <= this.range * 3; distance++) {
                locationDataList = locationDataMap.get(distance);
                if (locationDataList != null) {
                    Collections.shuffle(locationDataList);
                    for (LocationData tempLocationData : locationDataList) {
                        BlockTicker blockTicker = LocationDataUtil.getSlimefunItem(FinalTech.getLocationDataService(), tempLocationData).getBlockTicker();
                        if (blockTicker != null) {
                            if (blockTicker.isSynchronized()) {
                                javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                                    try {
                                        FinalTech.getBlockTickerService().run(blockTicker, tempLocationData);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                        throw new RuntimeException(e);
                                    }
                                });
                            } else {
                                BlockTickerUtil.runTask(FinalTech.getLocationRunnableFactory(), FinalTech.isAsyncSlimefunItem(LocationDataUtil.getId(FinalTech.getLocationDataService(), tempLocationData)), () -> {
                                    try {
                                        FinalTech.getBlockTickerService().run(blockTicker, tempLocationData);
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                        throw new RuntimeException(e);
                                    }
                                }, tempLocationData.getLocation());
                            }
                            if (hasViewer) {
                                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, tempLocationData.getLocation().getBlock()));
                            }
                            accelerateTotalTime++;
                        }
                    }
                }
            }

            accelerateRoundTime++;
            nextEnergy = (int) (machineEnergy - allCapacity.get() * accelerateRoundTime);
            if(nextEnergy > 0) {
                machineEnergy = nextEnergy;
            } else {
                break;
            }

            hasViewer = false;
        }

        if (accelerateRoundTime > 0) {
            EnergyUtil.setCharge(FinalTech.getLocationDataService(), locationData, String.valueOf(machineEnergy));
        }

        if(hasViewer) {
            this.updateInv(inventory, StatusMenu.STATUS_SLOT, this,
                    String.valueOf(storedMachineEnergy),
                    String.valueOf(count),
                    String.valueOf(accelerateRoundTime),
                    String.valueOf(accelerateTotalTime));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.range),
                String.valueOf(this.capacity));
    }

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        int i = 0;
        Location location = sourceLocation.clone();
        World world = location.getWorld();
        int minX = location.getBlockX() - this.range;
        int minY = Math.max(location.getBlockY() - this.range, world.getMinHeight());
        int minZ = location.getBlockZ() - this.range;
        int maxX = location.getBlockX() + this.range;
        int maxY  = Math.min(location.getBlockY() + this.range, world.getMaxHeight());
        int maxZ = location.getBlockZ() + this.range;
        Location[] locations = new Location[(maxX - minX + 1) * (maxY - minY + 1) + (maxZ - minZ + 1)];
        for (int x = minX; x <= maxX; x++) {
            location.setX(x);
            for (int y = minY; y <= maxY; y++) {
                location.setY(y);
                for (int z = minZ; z <= maxZ; z++) {
                    location.setZ(z);
                    locations[i++] = location.clone();
                }
            }
        }

        return locations;
    }

    protected boolean calAllowed(@Nonnull SlimefunItem slimefunItem) {
        if(this.allowedId.contains(slimefunItem.getId())) {
            return true;
        } else if(this.notAllowedId.contains(slimefunItem.getId())) {
            return false;
        } else {
            if(slimefunItem.getBlockTicker() == null || slimefunItem.getBlockTicker() == null || !(slimefunItem instanceof EnergyNetComponent energyNetComponent) || !EnergyNetComponentType.CONSUMER.equals(energyNetComponent.getEnergyComponentType())) {
                this.notAllowedId.add(slimefunItem.getId());
                return false;
            }

            this.allowedId.add(slimefunItem.getId());
            return true;
        }
    }
}
