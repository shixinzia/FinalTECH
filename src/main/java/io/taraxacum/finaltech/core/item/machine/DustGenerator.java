package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.MathUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.DustGeneratorMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.interfaces.BeautifulEnergyProvider;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class DustGenerator extends AbstractMachine implements RecipeItem, MenuUpdater, EnergyNetProvider, BeautifulEnergyProvider {
    private final String keyCount = "count";
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(Integer.MAX_VALUE / 4, this, "capacity");
    // default = 144115188344291328
    // long.max= 9223372036854775808
    private final long countLimit = (long) this.capacity * (this.capacity + 1) / 2;

    public DustGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new DustGeneratorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }

        Location location = block.getLocation();

        String keyCountStr = FinalTech.getLocationDataService().getLocationData(locationData, this.keyCount);
        long count = keyCountStr == null ? 0 : Long.parseLong(keyCountStr);
        boolean work = false;
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = inventory.getItem(slot);
            if (FinalTechItems.UNORDERED_DUST.verifyItem(itemStack)) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                count = Math.min(++count, this.countLimit);
                work = true;
                break;
            } else if (FinalTechItems.ITEM_PHONY.verifyItem(itemStack)) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                count *= 2;
                count = Math.min(count, this.countLimit);
                work = true;
                break;
            }
        }
        if (!work) {
            count /= 2;
        }
        int charge = (int) MathUtil.getBig(1, 1, -2 * count);


        FinalTech.getLocationDataService().setLocationData(locationData, this.keyCount, String.valueOf(count));
        if (count > 0) {
            this.addCharge(location, charge);
        }

        if(!inventory.getViewers().isEmpty()) {
            this.updateInv(inventory, DustGeneratorMenu.STATUS_SLOT, this,
                    String.valueOf(count),
                    String.valueOf(charge),
                    String.valueOf(this.getCharge(location)));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public int getGeneratedOutput(@Nonnull LocationData locationData) {
        String chargeStr = EnergyUtil.getCharge(FinalTech.getLocationDataService(), locationData);
        int charge = Integer.parseInt(chargeStr);
        EnergyUtil.setCharge(FinalTech.getLocationDataService(), locationData, "0");
        return charge;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.capacity));
    }

}
