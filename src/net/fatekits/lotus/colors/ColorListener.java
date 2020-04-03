package net.fatekits.lotus.colors;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ColorListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() != null) {
            Player player = (Player) event.getWhoClicked();
            Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
            if (event.getInventory() != null) {
                if (event.getClickedInventory() != null) {
                    if (event.getClickedInventory().getName().equalsIgnoreCase(Lotus.getPlugin().getColorGUI().getColorGI().getName())) {
                        if (event.getCurrentItem() != null) {
                            if (event.getCurrentItem().getItemMeta() != null) {
                                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Remove")) {
                                    profile.setColor("&7");
                                    player.sendMessage(StringUtil.format("&cYou have reset your color"));
                                }
                                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Staff-Colors")) {
                                    if (profile.isStaff()) {
                                        player.openInventory(Lotus.getPlugin().getColorGUI().getStaffColors());
                                    } else {
                                        player.sendMessage(StringUtil.format("&cYou do not have permission to use these colors."));
                                    }
                                }
                                if (event.getClickedInventory().getName().equals(Lotus.getPlugin().getColorGUI().getColorGI().getName())) {
                                    event.setCancelled(true);
                                    if (Color.getByName(event.getCurrentItem().getItemMeta().getDisplayName()) != null) {
                                        Color color = Color.getByName(event.getCurrentItem().getItemMeta().getDisplayName());
                                        color.onClick(player, profile, event.getCurrentItem());
                                    }
                                }
                            }
                        }
                    }
                    if (event.getClickedInventory().getName().equalsIgnoreCase(Lotus.getPlugin().getColorGUI().getStaffColors().getName())) {
                        if (event.getCurrentItem() != null) {
                            if (event.getCurrentItem().getItemMeta() != null) {
                                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Remove")) {
                                    profile.setColor("&7");
                                    player.sendMessage(StringUtil.format("&cYou have reset your color"));
                                }
                                if (event.getClickedInventory().getName().equals(Lotus.getPlugin().getColorGUI().getColorGI().getName())) {
                                    event.setCancelled(true);
                                    if (Color.getByName(event.getCurrentItem().getItemMeta().getDisplayName()) != null) {
                                        Color color = Color.getByName(event.getCurrentItem().getItemMeta().getDisplayName());
                                        color.onClick(player, profile, event.getCurrentItem());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
