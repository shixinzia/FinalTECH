package io.taraxacum.finaltech.core.service;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public interface ItemService {

    @Nonnull
    NamespacedKey getIdKey();

    @Nonnull
    PersistentDataType getIdDataType();
}
