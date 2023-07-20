package io.taraxacum.finaltech.core.inventory.clicker;

import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TransporterInventory extends AbstractClickerInventory {
    private final int[] border = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

    private final int range;

    public TransporterInventory(@Nonnull AbstractClickerMachine abstractClickerMachine, int range) {
        super(abstractClickerMachine);
        this.range = range;
    }

    @Nonnull
    @Override
    protected int[] getBorder() {
        return this.border;
    }

    @Override
    protected void initSelf() {

    }

    @Override
    protected void openFunction(@Nonnull Player player, @Nonnull Location location, @Nonnull Inventory inventory) {
        InventoryUtil.closeInv(inventory);

        Block block = location.getBlock();
        BlockData blockData = block.getState().getBlockData();
        List<Block> blockList = new ArrayList<>();
        if (blockData instanceof Directional directional) {
            BlockFace blockFace = directional.getFacing();
            Block targetBlock = block;

            for (int i = 0; i < this.range; i++) {
                targetBlock = targetBlock.getRelative(blockFace);
                blockList.add(targetBlock);
                if (targetBlock.getChunk().isLoaded() && targetBlock.getType().isAir()) {
                    JavaPlugin javaPlugin = this.slimefunItem.getAddon().getJavaPlugin();
                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, blockList));

                    Location sourceLocation = player.getLocation().clone();
                    Location targetLocation = LocationUtil.getCenterLocation(targetBlock);
                    targetLocation.setYaw(sourceLocation.getYaw());
                    targetLocation.setPitch(sourceLocation.getPitch());
                    player.teleport(targetLocation);
                    return;
                }
            }
        }
    }

    @Override
    public int getSize() {
        return 9;
    }
}
