package io.taraxacum.finaltech.core.item.machine.manual;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.enums.LogSourceType;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.slimefun.dto.ItemValueTable;
import io.taraxacum.finaltech.core.menu.manual.AbstractManualMachineMenu;
import io.taraxacum.finaltech.core.menu.manual.EquivalentExchangeTableMenu;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import io.taraxacum.libs.slimefun.interfaces.ValidItem;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EquivalentExchangeTable extends AbstractManualMachine implements RecipeItem {
    private final String key = "v";

    public EquivalentExchangeTable(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(FinalTech.getLocationDataService(), this, EquivalentExchangeTableMenu.PARSE_ITEM_SLOT);
    }

    @Nonnull
    @Override
    protected AbstractManualMachineMenu newMachineMenu() {
        return new EquivalentExchangeTableMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Location location = block.getLocation();
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }

        String value = JavaUtil.getFirstNotNull(FinalTech.getLocationDataService().getLocationData(locationData, this.key), StringNumberUtil.ZERO);
        for (int slot : this.getInputSlot()) {
            ItemStack itemStack = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(itemStack)) {
                continue;
            }

            if (FinalTechItems.UNORDERED_DUST.verifyItem(itemStack)) {
                if (InventoryUtil.slotCount(inventory, this.getOutputSlot()) == this.getOutputSlot().length) {
                    continue;
                }
                value = this.doCraft(value, inventory, location, itemStack.getAmount());
                itemStack.setAmount(0);
                FinalTech.getLogService().subItem(FinalTechItems.UNORDERED_DUST.getId(), itemStack.getAmount(), this.getId(), LogSourceType.SLIMEFUN_MACHINE, null, location, this.getAddon().getJavaPlugin());
                continue;
            }

            SlimefunItem sfItem = SlimefunItem.getByItem(itemStack);
            if (sfItem != null) {
                if(sfItem instanceof ValidItem validItem && !validItem.verifyItem(itemStack)) {
                    value = StringNumberUtil.add(value);
                } else {
                    value = StringNumberUtil.add(value, StringNumberUtil.mul(ItemValueTable.getInstance().getOrCalItemInputValue(sfItem), String.valueOf(itemStack.getAmount())));
                }
                itemStack.setAmount(0);
            }
        }

        FinalTech.getLocationDataService().setLocationData(locationData, this.key, value);

        if (!inventory.getViewers().isEmpty()) {
            this.getMachineMenu().updateInventory(inventory, block.getLocation());
        }
    }

    private String doCraft(@Nonnull String value, @Nonnull Inventory inventory, @Nonnull Location location, int amount) {
        if(StringNumberUtil.compare(value, StringNumberUtil.ZERO) <= 0) {
            return StringNumberUtil.ZERO;
        }

        List<SlimefunItem> slimefunItemList = Slimefun.getRegistry().getEnabledSlimefunItems();
        int searchedTime = 0;
        SlimefunItem searchedSlimefunItem = null;
        String searchedValue = null;

        for (int i = 0, retryTimes = value.length(); i < retryTimes; i++) {
            SlimefunItem slimefunItem = slimefunItemList.get(FinalTech.getRandom().nextInt(slimefunItemList.size()));
            String targetValue = ItemValueTable.getInstance().getOrCalItemOutputValue(slimefunItem);

            if(targetValue.equals(StringNumberUtil.VALUE_INFINITY) && ++searchedTime >= amount) {
                i--;
                continue;
            }

            if (StringNumberUtil.compare(value, targetValue) >= 0) {
                List<String> idList = ItemValueTable.getInstance().getValueItemListOutputMap().get(targetValue);
                if(idList == null || idList.isEmpty()) {
                    continue;
                }
                String id = idList.get(FinalTech.getRandom().nextInt(idList.size()));
                slimefunItem = SlimefunItem.getById(id);
                if (slimefunItem == null || slimefunItem instanceof MultiBlockMachine) {
                    continue;
                }

                if(searchedValue == null || StringNumberUtil.ZERO.equals(targetValue) || StringNumberUtil.compare(targetValue, searchedValue) > 0) {
                    searchedSlimefunItem = slimefunItem;
                    searchedValue = targetValue;
                }
            }
        }

        if(searchedSlimefunItem != null) {
            ItemStack itemStack = searchedSlimefunItem instanceof SimpleValidItem simpleValidItem ? simpleValidItem.getValidItem() : ItemStackUtil.cloneItem(searchedSlimefunItem.getItem(), 1);
            if (InventoryUtil.tryPushAllItem(inventory, this.getOutputSlot(), itemStack)) {
                if(StringNumberUtil.ZERO.equals(searchedValue)) {
                    value = StringNumberUtil.ZERO;
                } else {
                    value = StringNumberUtil.sub(value, searchedValue);
                }
                SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
                if(slimefunItem instanceof ValidItem) {
                    FinalTech.getLogService().addItem(slimefunItem.getId(), 1, this.getId(), LogSourceType.SLIMEFUN_MACHINE, null, location, this.getAddon().getJavaPlugin());
                }
            }
        }

        return value;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
