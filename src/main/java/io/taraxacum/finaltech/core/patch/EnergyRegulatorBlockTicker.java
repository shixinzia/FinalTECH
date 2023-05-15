package io.taraxacum.finaltech.core.patch;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNet;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.core.networks.AlteredEnergyNet;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.dto.LocationBlockStorageData;
import io.taraxacum.libs.slimefun.dto.LocationDatabaseData;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * @author Final_ROOT
 */
public class EnergyRegulatorBlockTicker extends BlockTicker implements MenuUpdater {
    private boolean init = false;

    public synchronized void enable() {
        if(this.init) {
            return;
        }

        SlimefunItem energyRegulator = SlimefunItem.getByItem(SlimefunItems.ENERGY_REGULATOR);
        if(energyRegulator != null) {
            // register blockMenu
            new EnergyRegulatorStaticsMenu(energyRegulator);

            // replace blockTicker
            try {
                final Class<SlimefunItem> clazz = SlimefunItem.class;
                Field declaredField = clazz.getDeclaredField("blockTicker");
                declaredField.setAccessible(true);
                declaredField.set(energyRegulator, this);
                declaredField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            FinalTech.logger().severe("Can not find energy regulator! Why?");
        }

        this.init = true;
    }

    @Override
    public boolean isSynchronized() {
        return false;
    }

    public void tick(Block block, SlimefunItem slimefunItem, Config data) {
        this.tick(block, slimefunItem, new LocationBlockStorageData(block.getLocation(), data, slimefunItem.getId(), slimefunItem));
    }

    public void tick(Block block, SlimefunItem slimefunItem, SlimefunBlockData slimefunBlockData) {
        this.tick(block, slimefunItem, new LocationDatabaseData(block.getLocation(), slimefunBlockData));
    }

    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Location location = block.getLocation();
        EnergyNet energyNetwork = AlteredEnergyNet.getNetworkFromLocationOrCreate(location);
        if(energyNetwork instanceof AlteredEnergyNet alteredEnergyNet) {
            try {
                AlteredEnergyNet.Summary summary = alteredEnergyNet.tick(block, slimefunItem);
                Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
                if(inventory != null && !inventory.getViewers().isEmpty()) {
                    this.updateInv(inventory, StatusMenu.STATUS_SLOT, slimefunItem,
                            String.valueOf(summary.getConsumerAmount()),
                            String.valueOf(summary.getConsumerEnergy()),
                            String.valueOf(summary.getConsumerCapacity()),
                            String.valueOf(summary.getGeneratorAmount()),
                            String.valueOf(summary.getGeneratorEnergy()),
                            String.valueOf(summary.getGeneratorCapacity()),
                            String.valueOf(summary.getCapacitorAmount()),
                            String.valueOf(summary.getCapacitorEnergy()),
                            String.valueOf(summary.getCapacitorCapacity()),
                            String.valueOf(summary.getGeneratedEnergy()),
                            String.valueOf(summary.getTransferredEnergy()));
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            energyNetwork.markDirty(location);
            energyNetwork.tick(block);
        }
    }

    @Override
    public void uniqueTick() {
        AlteredEnergyNet.uniqueTick();
    }
}
