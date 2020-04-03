package net.fatekits.lotus.cosmetics;

import net.fatekits.lotus.Lotus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CosmeticListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() != null) {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory() != null) {
                if (event.getCurrentItem() != null) {
                    if (event.getClickedInventory().getName().contains(Lotus.getPlugin().getCosmeticInventory().getCosmeticInventory().getName())) {
                        event.setCancelled(true);
                        Cosmetic cosmetic = Cosmetic.byName(event.getCurrentItem().getType().name());
                        if (cosmetic != null) {
                            cosmetic.onClick(event.getCurrentItem(), player);
                        }
                    }
                }
            }
        }
    }
}
