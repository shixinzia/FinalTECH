package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.ItemDeserializeParserMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.StringItemUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * This is a slimefun machine
 * it will be used in gameplay
 * It's not a function class!
 * @author Final_ROOT
 */
public class ItemDeserializeParser extends AbstractMachine implements RecipeItem {
    public ItemDeserializeParser(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new ItemDeserializeParserMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null || InventoryUtil.slotCount(inventory, this.getOutputSlot()) == this.getOutputSlot().length) {
            return;
        }
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = inventory.getItem(slot);
            if (FinalTechItems.COPY_CARD.verifyItem(itemStack)) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                ItemStack stringItem = StringItemUtil.parseItemInCard(itemMeta);
                if (!ItemStackUtil.isItemNull(stringItem)) {
                    int amount = itemStack.getAmount();
                    String amountInCardStr = StringItemUtil.parseAmountInCard(itemMeta);
                    if(StringNumberUtil.compare(amountInCardStr, "3456") >= 0) {
                        amount = 3456;
                    } else {
                        amount *= Integer.parseInt(amountInCardStr);
                    }
                    if(amount <= 0) {
                        return;
                    }

                    InventoryUtil.tryPushItem(inventory, this.getOutputSlot(), amount, new ItemAmountWrapper(stringItem, 1));
                }
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }
}
