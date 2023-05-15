package io.taraxacum.finaltech.core.item.machine;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.AbstractMySlimefunItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.libs.slimefun.dto.LocationBlockStorageData;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.slimefun.dto.LocationDatabaseData;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
// TODO: Optimization
public abstract class AbstractMachine extends AbstractMySlimefunItem {
    private AbstractMachineMenu menu;

    public AbstractMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        super.preRegister();

        this.addItemHandler(this.onBlockBreak());
        this.addItemHandler(this.onBlockPlace());
        this.menu = this.setMachineMenu();

        if (FinalTech.getMultiThreadLevel() == 2) {
            this.getAddon().getJavaPlugin().getLogger().info(this.getId() + "(" + this.getItemName() + ")" + " is optimized for multi-thread！！！");
        } else if (!this.isSynchronized() && (FinalTech.getMultiThreadLevel() == 1 || FinalTech.isAsyncSlimefunItem(this.getId()))) {
            this.getAddon().getJavaPlugin().getLogger().info(this.getId() + "(" + this.getItemName() + ")" + " is optimized for multi-thread！！！");
        }

        BlockTicker blockTicker;
        if (FinalTech.getMultiThreadLevel() == 2) {
            blockTicker = new BlockTicker() {
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

                public void tick(Block block, SlimefunItem slimefunItem, LocationData locationData) {
                    AbstractMachine.this.tick(block, slimefunItem, locationData);
                }

                @Override
                public void uniqueTick() {
                    AbstractMachine.this.uniqueTick();
                }
            };
            FinalTech.addAsyncSlimefunItem(this.getId());
        } else {
            blockTicker = new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return AbstractMachine.this.isSynchronized();
                }

                public void tick(Block block, SlimefunItem slimefunItem, Config data) {
                    this.tick(block, slimefunItem, new LocationBlockStorageData(block.getLocation(), data, slimefunItem.getId(), slimefunItem));
                }

                public void tick(Block block, SlimefunItem slimefunItem, SlimefunBlockData slimefunBlockData) {
                    this.tick(block, slimefunItem, new LocationDatabaseData(block.getLocation(), slimefunBlockData));
                }

                public void tick(Block block, SlimefunItem slimefunItem, LocationData locationData) {
                    AbstractMachine.this.tick(block, slimefunItem, locationData);
                }

                @Override
                public void uniqueTick() {
                    AbstractMachine.this.uniqueTick();
                }
            };
            if(!this.isSynchronized() && FinalTech.getMultiThreadLevel() >= 1) {
                FinalTech.addAsyncSlimefunItem(this.getId());
            }
        }
        this.addItemHandler(blockTicker);
    }

    @Nonnull
    public final int[] getInputSlot() {
        return this.menu == null ? new int[0] : this.menu.getInputSlot();
    }

    @Nonnull
    public final int[] getOutputSlot() {
        return this.menu == null ? new int[0] : this.menu.getOutputSlot();
    }

    protected void uniqueTick() {

    }

    @Nonnull
    protected abstract BlockPlaceHandler onBlockPlace();

    @Nonnull
    protected abstract BlockBreakHandler onBlockBreak();

    @Nullable
    protected abstract AbstractMachineMenu setMachineMenu();

    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {

    }

    protected abstract boolean isSynchronized();
}
