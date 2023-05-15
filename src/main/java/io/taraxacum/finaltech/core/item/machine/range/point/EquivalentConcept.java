package io.taraxacum.finaltech.core.item.machine.range.point;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.*;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import io.taraxacum.libs.slimefun.service.SlimefunLocationDataService;
import io.taraxacum.libs.slimefun.util.SfItemUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EquivalentConcept extends AbstractPointMachine implements RecipeItem, SimpleValidItem {
    public static final String KEY_LIFE = "l";
    public static final String KEY_RANGE = "r";
    private final double attenuationRate = ConfigUtil.getOrDefaultItemSetting(0.95, this, "attenuation-rate");
    private final double life = ConfigUtil.getOrDefaultItemSetting(4.0, this, "life");
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");

    private final ItemWrapper templateValidItem;

    public EquivalentConcept(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        ItemStack validItem = new ItemStack(this.getItem());
        SfItemUtil.setSpecialItemKey(validItem);
        this.templateValidItem = new ItemWrapper(validItem);

        this.addItemHandler(new ItemUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onRightClick(PlayerRightClickEvent e) {
                e.cancel();
            }
        });
        this.addItemHandler(new WeaponUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onHit(@Nonnull EntityDamageByEntityEvent e, @Nonnull Player player, @Nonnull ItemStack item) {
                e.setCancelled(true);
            }
        });
        this.addItemHandler(new EntityInteractHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onInteract(PlayerInteractEntityEvent e, ItemStack item, boolean offHand) {
                e.setCancelled(true);
            }
        });
        this.addItemHandler(new ToolUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onToolUse(BlockBreakEvent e, ItemStack tool, int fortune, List<ItemStack> drops) {
                e.setCancelled(true);
            }
        });
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler();
    }

    @Nonnull
    @Override
    public Collection<ItemStack> getDrops() {
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(this.getValidItem());
        return drops;
    }

    @Nullable
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        // this is the only
        return null;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        if (!BlockTickerUtil.subSleep(FinalTech.getLocationDataService(), locationData)) {
            return;
        }

        String lifeStr = FinalTech.getLocationDataService().getLocationData(locationData, KEY_LIFE);
        double life = lifeStr != null ? Double.parseDouble(lifeStr) : 0;
        if (life < 1) {
            Location location = block.getLocation();
            FinalTech.getLocationDataService().setLocationData(locationData, KEY_LIFE, null);
            FinalTech.getLocationDataService().setLocationData(locationData, KEY_RANGE, null);
            BlockTickerUtil.subSleep(FinalTech.getLocationDataService(), locationData);
            FinalTech.getLocationDataService().clearLocationData(location);
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                if (!location.getBlock().getType().isAir()
                        && FinalTech.getLocationDataService().getLocationData(location) == null
                        && FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
                    slimefunLocationDataService.getOrCreateEmptyLocationData(location, FinalTechItems.JUSTIFIABILITY.getId());
                }
            }, Slimefun.getTickerTask().getTickRate() + 1);
            return;
        }

        String rangeStr = FinalTech.getLocationDataService().getLocationData(locationData, KEY_RANGE);
        final int range = rangeStr != null ? Integer.parseInt(rangeStr) : this.range;

        while (life > 1) {
            final double finalLife = life--;
            this.pointFunction(block, range, location -> {
                FinalTech.getLocationRunnableFactory().waitThenRun(() -> {
                    Block targetBlock = location.getBlock();
                    if (FinalTech.getLocationDataService().getLocationData(location) == null
                            && (targetBlock.getType().isAir())
                            && FinalTech.getLocationDataService() instanceof SlimefunLocationDataService slimefunLocationDataService) {
                        LocationData tempLocationData = slimefunLocationDataService.getOrCreateEmptyLocationData(location, this.getId());
                        FinalTech.getLocationDataService().setLocationData(locationData, KEY_LIFE, String.valueOf(finalLife * attenuationRate));
                        FinalTech.getLocationDataService().setLocationData(locationData, KEY_RANGE, String.valueOf(range + 1));
                        BlockTickerUtil.setSleep(FinalTech.getLocationDataService(), tempLocationData, EquivalentConcept.this.life - finalLife);
                        JavaPlugin javaPlugin = EquivalentConcept.this.getAddon().getJavaPlugin();
                        javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> targetBlock.setType(EquivalentConcept.this.getItem().getType()));
                    }
                }, location);
                return 0;
            });
        }

        FinalTech.getLocationDataService().setLocationData(locationData, KEY_LIFE, String.valueOf(0));
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public Location getTargetLocation(@Nonnull Location location, int range) {
        int y = location.getBlockY() - range + FinalTech.getRandom().nextInt(range + range);
        y = Math.min(location.getWorld().getMaxHeight(), y);
        y = Math.max(location.getWorld().getMinHeight(), y);
        return new Location(location.getWorld(), location.getX() - range + FinalTech.getRandom().nextInt(range + range), y, location.getZ() - range + new Random().nextInt(range + range));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }

    @Nonnull
    @Override
    public ItemStack getValidItem() {
        return ItemStackUtil.cloneItem(this.templateValidItem.getItemStack());
    }

    @Override
    public boolean verifyItem(@Nonnull ItemStack itemStack) {
        return ItemStackUtil.isItemSimilar(itemStack, this.templateValidItem);
    }
}
