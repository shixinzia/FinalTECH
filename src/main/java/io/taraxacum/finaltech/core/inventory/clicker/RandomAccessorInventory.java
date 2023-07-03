package io.taraxacum.finaltech.core.inventory.clicker;

import io.taraxacum.common.util.JavaUtil;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class RandomAccessorInventory extends AbstractClickerInventory {
    private final int[] border = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};

    private final BlockFace[] availableBlockFaces = new BlockFace[] {BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH};

    public RandomAccessorInventory(@Nonnull AbstractClickerMachine abstractClickerMachine) {
        super(abstractClickerMachine);
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
        Block targetBlock;
        if(FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
            for(int i : JavaUtil.generateRandomInts(this.availableBlockFaces.length)) {
                BlockFace blockFace = this.availableBlockFaces[i];
                targetBlock = block.getRelative(blockFace);
                BlockMenu targetBlockMenu = slimefunLocationDataService.getBlockMenu(targetBlock.getLocation());
                if (targetBlockMenu != null && targetBlockMenu.canOpen(targetBlock, player)) {
                    JavaPlugin javaPlugin = this.slimefunItem.getAddon().getJavaPlugin();
                    Block finalTargetBlock = targetBlock;
                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, finalTargetBlock));
                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.WAX_OFF, 0, 0.25, LocationUtil.getCenterLocation(block), LocationUtil.getCenterLocation(finalTargetBlock)));
                    targetBlockMenu.open(player);
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
