package io.taraxacum.finaltech.core.interfaces;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public interface LineMachine extends RangeMachine {
    default int lineFunction(@Nonnull Block block, int range, @Nonnull LineMachine.RangeFunction function) {
        if (block.getBlockData() instanceof Directional directional) {
            return this.lineFunction(block, range, directional.getFacing(), function);
        } else {
            return 0;
        }
    }

    default int lineFunction(@Nonnull Block block, int range, @Nonnull BlockFace blockFace, @Nonnull LineMachine.RangeFunction function) {
        int count = 0;
        for (int i = 0; i < range; i++) {
            block = block.getRelative(blockFace);
            int result = function.apply(block.getLocation());
            if (result < 0) {
                return count;
            }
            count += result;
        }
        return count;
    }
}
