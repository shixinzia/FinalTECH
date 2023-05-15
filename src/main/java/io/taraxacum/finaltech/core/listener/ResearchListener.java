package io.taraxacum.finaltech.core.listener;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerPreResearchEvent;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.taraxacum.finaltech.core.interfaces.SpecialResearch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ResearchListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onResearchStart(PlayerPreResearchEvent playerPreResearchEvent) {
        Research research = playerPreResearchEvent.getResearch();
        Player player = playerPreResearchEvent.getPlayer();
        if (research instanceof SpecialResearch specialResearch) {
            if(specialResearch.canResearch(player)) {
                specialResearch.afterResearch(player);
            } else {
                playerPreResearchEvent.setCancelled(true);
            }
        }
    }
}
