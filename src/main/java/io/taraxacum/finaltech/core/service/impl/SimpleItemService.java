package io.taraxacum.finaltech.core.service.impl;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.core.service.ItemService;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class SimpleItemService implements ItemService {

    @Nonnull
    @Override
    public NamespacedKey getIdKey() {
        return new NamespacedKey(Slimefun.instance(), "slimefun_item");
    }
}
