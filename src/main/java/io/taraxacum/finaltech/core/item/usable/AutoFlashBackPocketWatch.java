package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public class AutoFlashBackPocketWatch extends UsableSlimefunItem implements RecipeItem {
    private final Map<Player, Location> playerLocationMap = new HashMap<>();
    // 200 tick = 10 s
    private final int time = ConfigUtil.getOrDefaultItemSetting(200, this, "time");

    public AutoFlashBackPocketWatch(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    protected void function(@Nonnull PlayerRightClickEvent playerRightClickEvent) {
        Player player = playerRightClickEvent.getPlayer();
        Location location = player.getLocation();

        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();

        BukkitTask bukkitTask = javaPlugin.getServer().getScheduler().runTaskTimerAsynchronously(javaPlugin, () -> {
            World world = location.getWorld();
            double x = location.getX();
            double y = location.getY() + player.getEyeHeight();
            double z = location.getZ();

            double random = 0.2;
            int times = 5;

            for(int i = 0; i < times; i++) {
                world.spawnParticle(Particle.SQUID_INK,
                        x + FinalTech.getRandom().nextDouble(random * 2) - random,
                        y + FinalTech.getRandom().nextDouble(random * 2) - random,
                        z + FinalTech.getRandom().nextDouble(random * 2) - random,
                        1, 0, 0, 0, 0);
            }
        }, 1, 1);

        javaPlugin.getServer().getScheduler().runTaskLater(javaPlugin, () -> {
            if(!bukkitTask.isCancelled()) {
                bukkitTask.cancel();
            }
            player.teleport(location);
        }, this.time);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this, String.valueOf(this.time / 20));
    }
}
