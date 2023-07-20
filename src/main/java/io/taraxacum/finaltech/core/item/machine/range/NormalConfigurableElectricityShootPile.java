package io.taraxacum.finaltech.core.item.machine.range;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.DigitInjectableItem;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.unit.StatusWithNumberInventory;
import io.taraxacum.finaltech.core.option.SimpleNumber;
import io.taraxacum.libs.plugin.dto.LocationData;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiConsumer;

/**
 * @author Final_ROOT
 */
public class NormalConfigurableElectricityShootPile extends AbstractDigitElectricityShootPile implements DigitInjectableItem {
    private BiConsumer<Inventory, LocationData> digitInjectInventoryUpdater;

    public NormalConfigurableElectricityShootPile(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        StatusWithNumberInventory statusWithNumberInventory = new StatusWithNumberInventory(this);
        this.statusSlot = statusWithNumberInventory.statusSlot;
        this.digitInjectInventoryUpdater = statusWithNumberInventory::updateNumber;
        return super.setMachineInventory();
    }

    @Override
    protected int getDigit(@Nonnull LocationData locationData) {
        return Integer.parseInt(SimpleNumber.OPTION_256.getOrDefaultValue(FinalTech.getLocationDataService(), locationData));
    }

    @Override
    public void injectDigit(@Nonnull LocationData locationData, int digit) {
        SimpleNumber.OPTION_256.setOrClearValue(FinalTech.getLocationDataService(), locationData, String.valueOf(digit));

        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if (inventory != null) {
            this.digitInjectInventoryUpdater.accept(inventory, locationData);
        }
    }
}
