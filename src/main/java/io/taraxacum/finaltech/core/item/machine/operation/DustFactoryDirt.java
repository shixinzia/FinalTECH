package io.taraxacum.finaltech.core.item.machine.operation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.enums.LogSourceType;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.operation.DustFactoryOperation;
import io.taraxacum.finaltech.core.menu.limit.DustFactoryDirtMenu;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class DustFactoryDirt extends AbstractOperationMachine implements RecipeItem, MenuUpdater {
    public final int baseAmountDifficulty = ConfigUtil.getOrDefaultItemSetting(1024, this, "difficulty", "base", "amount");
    public final int baseTypeDifficulty = ConfigUtil.getOrDefaultItemSetting(16, this, "difficulty", "base", "type");
    public final int multiAmountDifficulty = ConfigUtil.getOrDefaultItemSetting(64, this, "difficulty", "multi", "amount");
    public final int multiTypeDifficulty = ConfigUtil.getOrDefaultItemSetting(1, this, "difficulty", "multi", "type");
    public final int deviationDifficulty = ConfigUtil.getOrDefaultItemSetting(-4, this, "difficulty", "deviation");

    public DustFactoryDirt(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent blockBreakEvent, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                Location location = blockBreakEvent.getBlock().getLocation();
                if(FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
                    BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(location);
                    if(blockMenu != null && blockMenu.getPreset().getID().equals(DustFactoryDirt.this.getId())) {
                        Inventory inventory = blockMenu.toInventory();
                        InventoryUtil.dropItems(inventory, location, DustFactoryDirt.this.getInputSlot());
                        InventoryUtil.dropItems(inventory, location, DustFactoryDirt.this.getOutputSlot());
                    }
                }

                DustFactoryDirt.this.getMachineProcessor().endOperation(location);
            }
        };
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new DustFactoryDirtMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }

        DustFactoryOperation operation = (DustFactoryOperation) this.getMachineProcessor().getOperation(block);

        for (int slot : this.getInputSlot()) {
            ItemStack inputItem = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }

            if (operation == null) {
                int amount = this.baseAmountDifficulty;
                int type = this.baseTypeDifficulty;
                if(this.deviationDifficulty != 0) {
                    int deviation = this.deviationDifficulty / Math.abs(this.deviationDifficulty) * FinalTech.getRandom().nextInt(Math.abs(this.deviationDifficulty) + 1);
                    amount += this.multiAmountDifficulty * (this.deviationDifficulty - deviation);
                    type += this.multiTypeDifficulty * deviation;
                }

                operation = new DustFactoryOperation(amount, type);
                this.getMachineProcessor().startOperation(block, operation);
            }
            operation.addItem(inputItem);
            inventory.clear(slot);

            if(operation.isFinished()) {
                ItemStack itemStack = operation.getResult();
                SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
                if(sfItem != null && InventoryUtil.tryPushAllItem(inventory, this.getOutputSlot(), itemStack)) {
                    FinalTech.getLogService().addItem(sfItem.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, null, block.getLocation(), this.getAddon().getJavaPlugin());
                    this.getMachineProcessor().endOperation(block);
                    operation = null;
                }
            }
        }

        if (operation == null) {
            int amount = this.baseAmountDifficulty;
            int type = this.baseTypeDifficulty;
            if(this.deviationDifficulty != 0) {
                int deviation = this.deviationDifficulty / Math.abs(this.deviationDifficulty) * FinalTech.getRandom().nextInt(Math.abs(this.deviationDifficulty) + 1);
                amount += this.multiAmountDifficulty * (this.deviationDifficulty - deviation);
                type += this.multiTypeDifficulty * deviation;
            }

            operation = new DustFactoryOperation(amount, type);
            this.getMachineProcessor().startOperation(block, operation);
        }

        if (!inventory.getViewers().isEmpty()) {
            this.updateInv(inventory, DustFactoryDirtMenu.STATUS_SLOT, this,
                    String.valueOf(operation.getAmountCount()),
                    String.valueOf(operation.getTypeCount()),
                    String.valueOf(operation.getAmountDifficulty()),
                    String.valueOf(operation.getTypeDifficulty()));
        }
    }

    @Override
    public void updateInv(@Nonnull Inventory inventory, int slot, @Nonnull SlimefunItem slimefunItem, @Nonnull String... text) {
        MenuUpdater.super.updateInv(inventory, slot, slimefunItem, text);
        if(text.length == 4) {
            int amountCount = Integer.parseInt(text[0]);
            int typeCount = Integer.parseInt(text[1]);
            int amountDifficulty = Integer.parseInt(text[2]);
            int typeDifficulty = Integer.parseInt(text[3]);

            ItemStack itemStack = inventory.getItem(slot);

            if (amountCount == 0 && typeCount == 0) {
                itemStack.setType(Material.RED_STAINED_GLASS_PANE);
            } else if (amountCount > amountDifficulty || typeCount > typeDifficulty) {
                itemStack.setType(Material.YELLOW_STAINED_GLASS_PANE);
            } else {
                itemStack.setType(Material.GREEN_STAINED_GLASS_PANE);
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.baseAmountDifficulty),
                String.valueOf(this.baseTypeDifficulty),
                String.valueOf(this.baseAmountDifficulty + this.deviationDifficulty * this.multiAmountDifficulty),
                String.valueOf(this.baseTypeDifficulty + this.deviationDifficulty * this.multiTypeDifficulty));
    }
}
