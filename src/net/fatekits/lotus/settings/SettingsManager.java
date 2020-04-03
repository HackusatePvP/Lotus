package net.fatekits.lotus.settings;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsManager implements Listener {
    FileConfiguration config = Lotus.getPlugin().getConfig();
    FileConfiguration lang = Lotus.getPlugin().getLangConfig().getConfig();

    public Inventory getSettingsInventory(Player player) {
        Inventory i = Bukkit.createInventory(null, config.getInt("settings.inventory.size"), StringUtil.format(config.getString("settings.inventory.name")));
        i.setItem(config.getInt("settings.scoreboard.slot"), getScoreboard(player));
        i.setItem(config.getInt("settings.chat.slot"), getChat(player));
        return i;
    }

    public ItemStack getScoreboard(Player player) {
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        if (profile.isScoreboard()) {
            ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString("settings.scoreboard.material")));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(StringUtil.format(config.getString("settings.scoreboard.name")));
            itemMeta.setLore(StringUtil.format(config.getStringList("settings.scoreboard.lore-false")));
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString("settings.scoreboard.material")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format(config.getString("settings.scoreboard.name")));
        itemMeta.setLore(StringUtil.format(config.getStringList("settings.scoreboard.lore-true")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getChat(Player player) {
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        if (profile.isChat()) {
            ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString("settings.chat.material")));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(StringUtil.format(config.getString("settings.chat.name")));
            itemMeta.setLore(StringUtil.format(config.getStringList("settings.chat.lore-false")));
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString("settings.chat.material")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format(config.getString("settings.chat.name")));
        itemMeta.setLore(StringUtil.format(config.getStringList("settings.chat.lore-true")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
            if (event.getClickedInventory() != null) {
                if (event.getCurrentItem() != null) {
                    if (event.getCurrentItem().getItemMeta() != null) {
                        if (event.getClickedInventory().getName().equalsIgnoreCase(getSettingsInventory(player).getName())) {
                            event.setCancelled(true);
                            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(getScoreboard(player).getItemMeta().getDisplayName())) {
                                if (profile.isScoreboard()) {
                                    player.sendMessage(StringUtil.format(lang.getString("scoreboard-false")));
                                    profile.setScoreboard(false);
                                } else {
                                    player.sendMessage(StringUtil.format(lang.getString("scoreboard-true")));
                                    profile.setScoreboard(true);
                                }
                            }
                            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(getChat(player).getItemMeta().getDisplayName())) {
                                if (profile.isChat()) {
                                    player.sendMessage(StringUtil.format(lang.getString("chat-false")));
                                    profile.setChat(false);
                                } else {
                                    player.sendMessage(StringUtil.format(lang.getString("chat-true")));
                                    profile.setChat(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
