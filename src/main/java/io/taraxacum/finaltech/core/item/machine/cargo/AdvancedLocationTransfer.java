package io.taraxacum.finaltech.core.item.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.dto.CargoDTO;
import io.taraxacum.finaltech.core.interfaces.DigitInjectableItem;
import io.taraxacum.finaltech.core.interfaces.LogicInjectableItem;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.cargo.AdvancedLocationTransferInventory;
import io.taraxacum.finaltech.core.option.*;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Final_ROOT
 */
public class AdvancedLocationTransfer extends AbstractCargo implements RecipeItem, DigitInjectableItem, LogicInjectableItem {
    private final double particleDistance = 0.25;
    private final int particleInterval = 2;
    private int locationRecorderSlot;
    private BiConsumer<Inventory, LocationData> logicInjectInventoryUpdater;
    private BiConsumer<Inventory, LocationData> digitInjectInventoryUpdater;

    public AdvancedLocationTransfer(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        AdvancedLocationTransferInventory advancedLocationTransferInventory = new AdvancedLocationTransferInventory(this);
        this.locationRecorderSlot = advancedLocationTransferInventory.locationRecorderSlot;
        this.logicInjectInventoryUpdater = advancedLocationTransferInventory::updateCargoOrder;
        this.digitInjectInventoryUpdater = advancedLocationTransferInventory::updateCargoNumber;
        return advancedLocationTransferInventory;
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Block block = blockPlaceEvent.getBlock();
                Location location = block.getLocation();
                LocationData locationData = FinalTech.getLocationDataService().getLocationData(location);
                if(locationData != null) {
                    FinalTech.getLocationDataService().setLocationData(locationData, ConstantTableUtil.CONFIG_UUID, blockPlaceEvent.getPlayer().getUniqueId().toString());

                    CargoNumber.OPTION.checkOrSetDefault(FinalTech.getLocationDataService(), locationData);
                    SlotSearchSize.OPTION.checkOrSetDefault(FinalTech.getLocationDataService(), locationData);
                    SlotSearchOrder.OPTION.checkOrSetDefault(FinalTech.getLocationDataService(), locationData);
                    CargoLimit.OPTION.checkOrSetDefault(FinalTech.getLocationDataService(), locationData);

                    CargoMode.OPTION.checkOrSetDefault(FinalTech.getLocationDataService(), locationData);
                    CargoOrder.OPTION.checkOrSetDefault(FinalTech.getLocationDataService(), locationData);
                }
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(FinalTech.getLocationDataService(), this, this.locationRecorderSlot);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean drawParticle = !inventory.getViewers().isEmpty() || RouteShow.VALUE_TRUE.equals(RouteShow.OPTION.getOrDefaultValue(FinalTech.getLocationDataService(), locationData));

        ItemStack locationRecorder = inventory.getItem(this.locationRecorderSlot);
        if (ItemStackUtil.isItemNull(locationRecorder)) {
            return;
        }
        Location targetLocation = LocationUtil.parseLocationInItem(locationRecorder);
        if (targetLocation == null || targetLocation.equals(block.getLocation())) {
            return;
        }
        Block targetBlock = targetLocation.getBlock();

        if (!targetLocation.getChunk().isLoaded() || !PermissionUtil.checkOfflinePermission(locationRecorder, targetLocation)) {
            return;
        }

        String slotSearchSize = SlotSearchSize.OPTION.getOrDefaultValue(FinalTech.getLocationDataService(), locationData);
        String slotSearchOrder = SlotSearchOrder.OPTION.getOrDefaultValue(FinalTech.getLocationDataService(), locationData);

        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setJavaPlugin(this.getAddon().getJavaPlugin());

        boolean positive;
        if (CargoOrder.VALUE_POSITIVE.equals(CargoOrder.OPTION.getOrDefaultValue(FinalTech.getLocationDataService(), locationData))) {
            cargoDTO.setInputBlock(block);
            cargoDTO.setInputSize(SlotSearchSize.VALUE_INPUTS_ONLY);
            cargoDTO.setInputOrder(SlotSearchOrder.VALUE_ASCENT);

            cargoDTO.setOutputBlock(targetBlock);
            cargoDTO.setOutputSize(slotSearchSize);
            cargoDTO.setOutputOrder(slotSearchOrder);
            positive = true;
        } else {
            cargoDTO.setOutputBlock(block);
            cargoDTO.setOutputSize(SlotSearchSize.VALUE_INPUTS_ONLY);
            cargoDTO.setOutputOrder(SlotSearchOrder.VALUE_ASCENT);

            cargoDTO.setInputBlock(targetBlock);
            cargoDTO.setInputSize(slotSearchSize);
            cargoDTO.setInputOrder(slotSearchOrder);
            positive = false;
        }

        if (drawParticle) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, targetBlock));
            if(FinalTech.getSlimefunTickCount() % this.particleInterval == 0) {
                List<Location> locationList = new ArrayList<>();
                locationList.add(LocationUtil.getCenterLocation(block));
                locationList.add(LocationUtil.getCenterLocation(targetBlock));
                final List<Location> finalLocationList = positive ? locationList : JavaUtil.reserve(locationList);
                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.CRIT_MAGIC, this.particleInterval * Slimefun.getTickerTask().getTickRate() * 50L, this.particleDistance, finalLocationList));
            }
        }

        cargoDTO.setCargoNumber(Integer.parseInt(CargoNumber.OPTION.getOrDefaultValue(FinalTech.getLocationDataService(), locationData)));
        cargoDTO.setCargoLimit(CargoLimit.OPTION.getOrDefaultValue(FinalTech.getLocationDataService(), locationData));
        cargoDTO.setCargoFilter(CargoFilter.VALUE_BLACK);
        cargoDTO.setFilterInv(inventory);
        cargoDTO.setFilterSlots(new int[0]);

        CargoUtil.doCargo(cargoDTO, CargoMode.OPTION.getOrDefaultValue(FinalTech.getLocationDataService(), locationData));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }

    @Override
    public void injectDigit(@Nonnull LocationData locationData, int digit) {
        CargoNumber.OPTION.setOrClearValue(FinalTech.getLocationDataService(), locationData, String.valueOf(digit));

        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if (inventory != null) {
            this.digitInjectInventoryUpdater.accept(inventory, locationData);
        }
    }

    @Override
    public void injectLogic(@Nonnull LocationData locationData, boolean logic) {
        if (logic) {
            CargoOrder.OPTION.setOrClearValue(FinalTech.getLocationDataService(), locationData, CargoOrder.VALUE_POSITIVE);
        } else {
            CargoOrder.OPTION.setOrClearValue(FinalTech.getLocationDataService(), locationData, CargoOrder.VALUE_REVERSE);
        }

        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if (inventory != null) {
            this.logicInjectInventoryUpdater.accept(inventory, locationData);
        }
    }
}
