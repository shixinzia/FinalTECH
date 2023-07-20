package io.taraxacum.finaltech.core.group;

import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.inventory.common.TypeItemGroupInventory;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.service.InventoryHistoryService;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * Yeah it need to be updated.
 * @author Final_ROOT
 */
public class TypeItemGroup extends FlexItemGroup {
    private final InventoryHistoryService inventoryHistoryService;
    private final RecipeType recipeType;
    private final int page;

    protected TypeItemGroup(@Nonnull NamespacedKey key, @Nonnull RecipeType recipeType, @Nonnull InventoryHistoryService inventoryHistoryService) {
        this(key, recipeType, inventoryHistoryService, 1);
    }

    protected TypeItemGroup(@Nonnull NamespacedKey key, @Nonnull RecipeType recipeType, @Nonnull InventoryHistoryService inventoryHistoryService, int page) {
        super(key, MachineUtil.cloneAsDescriptiveItem(recipeType.toItem()));
        this.inventoryHistoryService = inventoryHistoryService;
        this.recipeType = recipeType;
        this.page = page;
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return false;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        this.inventoryHistoryService.tryAddToLast(player, this);
        new TypeItemGroupInventory(player, playerProfile, slimefunGuideMode, this.inventoryHistoryService, this, this.page).open(player);
    }

    @Nonnull
    public RecipeType getRecipeType() {
        return this.recipeType;
    }

    @Nonnull
    public TypeItemGroup generateByPage(int page) {
        // TODO remove finaltech
        return new TypeItemGroup(new NamespacedKey(FinalTech.getInstance(), this.key.getKey() + "_" + page), this.recipeType, this.inventoryHistoryService, page);
    }

    @Nonnull
    public static TypeItemGroup getByRecipeType(@Nonnull RecipeType recipeType, @Nonnull InventoryHistoryService inventoryHistoryService) {
        return new TypeItemGroup(recipeType.getKey(), recipeType, inventoryHistoryService);
    }
}
