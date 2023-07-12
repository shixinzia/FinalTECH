package io.taraxacum.finaltech.core.inventory.clicker;

import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
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

public class RemoteAccessorInventory extends AbstractClickerInventory {
    private final int[] border = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

    private final int range;

    public RemoteAccessorInventory(@Nonnull AbstractClickerMachine abstractClickerMachine, int range) {
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
        if (blockData instanceof Directional directional
                && FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
            BlockFace blockFace = directional.getFacing();
            Block targetBlock = block;
            for (int i = 0; i < this.range; i++) {
                targetBlock = targetBlock.getRelative(blockFace);
                if (!targetBlock.getChunk().isLoaded()) {
                    return;
                }
                BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(targetBlock.getLocation());
                if (blockMenu != null && blockMenu.canOpen(targetBlock, player)) {
                    JavaPlugin javaPlugin = this.slimefunItem.getAddon().getJavaPlugin();
                    Block finalTargetBlock = targetBlock;
                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, finalTargetBlock));
                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.WAX_OFF, 0, 0.25, LocationUtil.getCenterLocation(block), LocationUtil.getCenterLocation(finalTargetBlock)));
                    blockMenu.open(player);
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
