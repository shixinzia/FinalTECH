package io.taraxacum.finaltech.core.menu.limit.lock;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.limit.AbstractLimitMachineMenu;
import io.taraxacum.finaltech.core.option.MachineRecipeLock;
import io.taraxacum.libs.slimefun.util.ChestMenuUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class AbstractLockMachineMenu extends AbstractLimitMachineMenu {
    public static final int RECIPE_LOCK_SLOT = 4;

    public AbstractLockMachineMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
    }

    @Override
    public void init() {
        super.init();
        this.addItem(this.getRecipeLockSlot(), MachineRecipeLock.ICON);
        this.addMenuClickHandler(this.getRecipeLockSlot(), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(this.getRecipeLockSlot(), ChestMenuUtil.warpByConsumer(MachineRecipeLock.OPTION.getHandler(FinalTech.getLocationDataService(), location, this.getSlimefunItem())));
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        super.updateInventory(inventory, location);
        MachineRecipeLock.OPTION.checkOrSetDefault(FinalTech.getLocationDataService(), location);
        ItemStack item = inventory.getItem(this.getRecipeLockSlot());
        String recipeLock = MachineRecipeLock.OPTION.getOrDefaultValue(FinalTech.getLocationDataService(), location);
        MachineRecipeLock.OPTION.updateLore(item, recipeLock, this.getSlimefunItem());
    }

    public int getRecipeLockSlot() {
        return RECIPE_LOCK_SLOT;
    }
}
