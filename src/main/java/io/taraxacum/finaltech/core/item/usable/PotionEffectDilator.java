package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class PotionEffectDilator extends UsableSlimefunItem implements RecipeItem {
    public PotionEffectDilator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        Player player = playerRightClickEvent.getPlayer();
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getAmplifier() >= 1) {
                player.removePotionEffect(potionEffect.getType());
                player.addPotionEffect(new PotionEffect(potionEffect.getType(), potionEffect.getDuration() * 2, potionEffect.getAmplifier() - 1));
            }
        }
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
