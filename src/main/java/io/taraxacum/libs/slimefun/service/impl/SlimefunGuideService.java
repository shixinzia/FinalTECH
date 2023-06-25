package io.taraxacum.libs.slimefun.service.impl;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.libs.plugin.service.InventoryHistoryService;
import io.taraxacum.libs.slimefun.util.GuideUtil;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author Final_ROOT
 */
public class SlimefunGuideService implements InventoryHistoryService {
    @Override
    public void openLast(@Nonnull Player player) {
        Optional<PlayerProfile> optionalPlayerProfile = PlayerProfile.find(player);
        if (optionalPlayerProfile.isPresent()) {
            GuideHistory guideHistory = optionalPlayerProfile.get().getGuideHistory();
            guideHistory.openLastEntry(Slimefun.getRegistry().getSlimefunGuide(SlimefunGuideMode.SURVIVAL_MODE));
        }
    }

    @Override
    public void openHome(@Nonnull Player player) {
        Optional<PlayerProfile> optionalPlayerProfile = PlayerProfile.find(player);
        if (optionalPlayerProfile.isPresent()) {
            GuideHistory guideHistory = optionalPlayerProfile.get().getGuideHistory();
            guideHistory.clear();
            SlimefunGuide.openMainMenu(optionalPlayerProfile.get(), SlimefunGuideMode.SURVIVAL_MODE, guideHistory.getMainMenuPage());
        }
    }

    @Override
    public void addToLast(@Nonnull Player player, @Nonnull Object inventoryImpl) {
        Optional<PlayerProfile> optionalPlayerProfile = PlayerProfile.find(player);
        if (optionalPlayerProfile.isPresent()) {
            GuideHistory guideHistory = optionalPlayerProfile.get().getGuideHistory();
            if (inventoryImpl instanceof ItemGroup itemGroup) {
                // I will not use the page. So it will always be 1
                guideHistory.add(itemGroup, 1);
            }
        }
    }

    @Override
    public void removeLast(@Nonnull Player player) {
        Optional<PlayerProfile> optionalPlayerProfile = PlayerProfile.find(player);
        optionalPlayerProfile.ifPresent(playerProfile -> GuideUtil.removeLastEntry(playerProfile.getGuideHistory()));
    }

    @Override
    public void removeThenOpenLast(@Nonnull Player player) {
        Optional<PlayerProfile> optionalPlayerProfile = PlayerProfile.find(player);
        if (optionalPlayerProfile.isPresent()) {
            GuideHistory guideHistory = optionalPlayerProfile.get().getGuideHistory();
            guideHistory.goBack(Slimefun.getRegistry().getSlimefunGuide(SlimefunGuideMode.SURVIVAL_MODE));
        }
    }

    @Override
    public boolean canBeAddToLast(@Nonnull Object inventoryImpl) {
        if (inventoryImpl instanceof ItemGroup) {
            return true;
        }

        return false;
    }
}
