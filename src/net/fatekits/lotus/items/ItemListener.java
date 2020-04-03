package net.fatekits.lotus.items;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getItemInHand();
        if (item.getItemMeta() != null) {
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Items.byName("Visibility").getItemType(player).getItemMeta().getDisplayName())) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    Items.byName("Visibility").onClick(player, item);
                }
                return;
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (Items.byName(item.getType().name()) != null) {
                Items.byName(item.getType().name()).onClick(player, item);
            }
        }
    }
}
