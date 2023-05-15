package io.taraxacum.finaltech.core.item.machine.range.point.face;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.ConfigUtil;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 */
public class EnergizedOperationAccelerator extends AbstractOperationAccelerator {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(20000000, this, "capacity");
    private final int baseEfficiency = ConfigUtil.getOrDefaultItemSetting(1, this, "base-efficiency");
    private final int randomEfficiency = ConfigUtil.getOrDefaultItemSetting(1, this, "random-efficiency");

    public EnergizedOperationAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    Set<String> getNotAllowedId() {
        return this.notAllowedId;
    }

    @Override
    int getBaseEfficiency() {
        return this.baseEfficiency;
    }

    @Override
    int getRandomEfficiency() {
        return this.randomEfficiency;
    }
}
