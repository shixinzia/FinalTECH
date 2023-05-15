package io.taraxacum.finaltech.core.menu.clicker;

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

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class RemoteAccessorMenu extends AbstractClickerMenu {
    private static final int[] BORDER = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[0];
    private static final int[] OUTPUT_SLOT = new int[0];

    private final int range;

    public RemoteAccessorMenu(@Nonnull AbstractClickerMachine slimefunItem, int range) {
        super(slimefunItem);
        this.range = range;
    }

    @Override
    protected void doFunction(@Nonnull Inventory inventory, @Nonnull Block block, @Nonnull Player player) {
        InventoryUtil.closeInv(inventory);

        BlockData blockData = block.getState().getBlockData();
        if (blockData instanceof Directional directional && FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
            BlockFace blockFace = directional.getFacing();
            Block targetBlock = block;
            for (int i = 0; i < this.range; i++) {
                targetBlock = targetBlock.getRelative(blockFace);
                BlockMenu blockMenu = slimefunLocationDataService.getBlockMenu(targetBlock.getLocation());
                if (blockMenu != null && blockMenu.canOpen(targetBlock, player)) {
                    JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
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
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }
}
