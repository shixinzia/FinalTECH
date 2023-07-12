package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.PermissionUtil;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author Final_ROOT
 */
public class SteppingStonePlacer extends UsableSlimefunItem {
    public SteppingStonePlacer(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        Optional<Block> clickedBlock = playerRightClickEvent.getClickedBlock();
        if(clickedBlock.isPresent()) {
            Block block = clickedBlock.get().getRelative(playerRightClickEvent.getClickedFace());
            if(block.getType().isAir()
                    && FinalTech.getLocationDataService().getLocationData(block.getLocation()) == null
                    && PermissionUtil.checkPermission(playerRightClickEvent.getPlayer(), block.getLocation(), Interaction.PLACE_BLOCK)
                    && FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
                block.setType(FinalTechItems.STEPPING_STONE.getItem().getType());
                slimefunLocationDataService.getOrCreateEmptyLocationData(block.getLocation(), FinalTechItems.STEPPING_STONE.getId());
            }
        }
    }
}
