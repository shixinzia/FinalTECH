package io.taraxacum.finaltech.core.command;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.group.RecipeItemGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * A #{@link Command} that will show the value of an item.
 * @author Final_ROOT
 */
public class ShowItemInfo implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player player) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
            if(slimefunItem == null) {
                player.sendRawMessage(FinalTech.getLanguageString("message", "invalid-item"));
                return true;
            }

            Optional<PlayerProfile> playerProfile = PlayerProfile.find(player);
            if (playerProfile.isPresent()) {
                if(!playerProfile.get().hasUnlocked(slimefunItem.getResearch())) {
                    Slimefun.getLocalization().sendMessage(player, "messages.not-researched", true, s1 -> s1.replace("%item%", slimefunItem.getItemName()));
                    return true;
                }

                if (!slimefunItem.canUse(player, false) || slimefunItem.isHidden()) {
                    player.sendRawMessage(FinalTech.getLanguageString("message", "no-permission", "item"));
                    return true;
                }

                RecipeItemGroup recipeItemGroup = RecipeItemGroup.getByItemStack(player, playerProfile.get(), SlimefunGuideMode.SURVIVAL_MODE, FinalTech.getSimpleInventoryHistoryService(), itemStack);
                GuideHistory guideHistory = playerProfile.get().getGuideHistory();
                if (recipeItemGroup != null) {
                    guideHistory.clear();
                    recipeItemGroup.open(player, playerProfile.get(), SlimefunGuideMode.SURVIVAL_MODE);
                } else {
                    SlimefunGuide.openMainMenu(playerProfile.get(), SlimefunGuideMode.SURVIVAL_MODE, guideHistory.getMainMenuPage());
                }
            }
        } else if(commandSender instanceof ConsoleCommandSender) {
            // TODO
        }
        return true;
    }
}
