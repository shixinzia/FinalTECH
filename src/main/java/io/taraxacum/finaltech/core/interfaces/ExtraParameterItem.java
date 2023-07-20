package io.taraxacum.finaltech.core.interfaces;

import io.taraxacum.finaltech.core.exception.ParseErrorException;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ExtraParameterItem {
    @Nullable
    ItemStack getByExtraParameter(@Nonnull String... argList) throws ParseErrorException;
}
