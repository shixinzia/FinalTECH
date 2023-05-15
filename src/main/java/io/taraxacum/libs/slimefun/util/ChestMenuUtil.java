package io.taraxacum.libs.slimefun.util;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * @author Final_ROOT
 */
public class ChestMenuUtil {

    @Nonnull
    public static ChestMenu.AdvancedMenuClickHandler warpByConsumer(@Nonnull Consumer<InventoryClickEvent> consumer) {
        return new ChestMenu.AdvancedMenuClickHandler() {
            @Override
            public boolean onClick(InventoryClickEvent event, Player player, int slot, ItemStack cursor, ClickAction action) {
                try {
                    consumer.accept(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
                return false;
            }
        };
    }

    public static ClickType warpClickAction(@Nonnull ClickAction clickAction) {
        if(clickAction.isRightClicked()) {
            if(clickAction.isShiftClicked()) {
                return ClickType.SHIFT_RIGHT;
            } else {
                return ClickType.RIGHT;
            }
        } else {
            if(clickAction.isShiftClicked()) {
                return ClickType.SHIFT_LEFT;
            } else {
                return ClickType.LEFT;
            }
        }
    }
}
