package io.taraxacum.finaltech.core.item.machine.operation;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.interfaces.UnCopiableItem;
import io.taraxacum.libs.slimefun.interfaces.ValidItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MatrixCopyCardFactory extends CopyCardFactory {
    public MatrixCopyCardFactory(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected boolean allowedItem(@Nonnull ItemStack itemStack) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if (slimefunItem instanceof UnCopiableItem || slimefunItem instanceof ValidItem) {
            return false;
        }

        return true;
    }
}
