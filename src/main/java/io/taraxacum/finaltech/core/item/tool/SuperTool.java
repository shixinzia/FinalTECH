package io.taraxacum.finaltech.core.item.tool;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.taraxacum.finaltech.core.item.AbstractMySlimefunItem;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public abstract class SuperTool extends AbstractMySlimefunItem {
    public SuperTool(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
        this.addItemHandler((ToolUseHandler) (blockBreakEvent, itemStack, fortune, drops) -> {
            blockBreakEvent.setDropItems(false);
            blockBreakEvent.setExpToDrop(blockBreakEvent.getExpToDrop() + 1);
        });
    }
}
