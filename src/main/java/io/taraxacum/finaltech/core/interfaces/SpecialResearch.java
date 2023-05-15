package io.taraxacum.finaltech.core.interfaces;

import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public abstract class SpecialResearch extends Research {

    public SpecialResearch(@Nonnull NamespacedKey key, int id, @Nonnull String defaultName, int defaultCost) {
        super(key, id, defaultName, defaultCost);
    }

    /**
     * Text to show in the slimefun guide while player is not unlocked this research.
     * @return text to show what player is needed to unlocked it.
     */
    abstract public String[] getShowText(@Nonnull Player player);

    /**
     * @return whether player can research this research.
     */
    abstract public boolean canResearch(@Nonnull Player player);

    /**
     * What to do if player unlock this research successful.
     */
    abstract public void afterResearch(@Nonnull Player player);
}
