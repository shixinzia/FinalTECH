package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class GravityReversalRune extends UsableSlimefunItem implements RecipeItem {
    private final int interval = ConfigUtil.getOrDefaultItemSetting(500, this, "interval");
    private final int threshold = ConfigUtil.getOrDefaultItemSetting(2, this, "threshold");

    public GravityReversalRune(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        Player player = playerRightClickEvent.getPlayer();
        boolean sprinting = player.isSprinting();
        Vector velocity = player.getVelocity();
        Vector vector = velocity.setY(velocity.getY() * -1);
        player.setVelocity(vector);
        player.setSprinting(sprinting);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }

    @Override
    int getThreshold() {
        return this.threshold;
    }

    @Override
    int getInterval() {
        return this.interval;
    }
}
