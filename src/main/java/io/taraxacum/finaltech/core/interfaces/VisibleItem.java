package io.taraxacum.finaltech.core.interfaces;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public interface VisibleItem {

    /**
     * @return whether this item is visible in slimefun guide
     */
    boolean isVisible(@Nonnull Player player);
}