package io.taraxacum.finaltech.core.item.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.manual.CardOperationPortMenu;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.libs.plugin.dto.LocationData;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class CardOperationTable extends AbstractManualMachine implements RecipeItem {
    public CardOperationTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if (inventory != null && !inventory.getViewers().isEmpty()) {
            this.getMachineMenu().updateInventory(inventory, block.getLocation());
        }
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new CardOperationPortMenu(this);
    }

    @Override
    public void registerDefaultRecipes() {
        for (CardOperationPortMenu.Craft craft : CardOperationPortMenu.CRAFT_LIST) {
            if (craft.isEnabled()) {
                String outputItemId = craft.getInfoOutput();
                SlimefunItem slimefunItem = SlimefunItem.getById(outputItemId);
                if (slimefunItem != null) {
                    this.registerDescriptiveRecipe(slimefunItem.getItem(), craft.getInfoName(), craft.getInfoLore());
                } else {
                    this.registerDescriptiveRecipe(craft.getInfoName(), craft.getInfoLore());
                }
            }
        }
    }
}
