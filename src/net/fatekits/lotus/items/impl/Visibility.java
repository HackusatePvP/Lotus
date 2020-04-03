package net.fatekits.lotus.items.impl;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.items.Items;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Visibility extends Items {
    private FileConfiguration config = Lotus.getPlugin().getConfig();
    private FileConfiguration lang = Lotus.getPlugin().getLangConfig().getConfig();
    String path = "items.visibility";

    public Visibility() {
        super("Visibility");
    }

    @Override
    public ItemStack getItemType(Player player) {
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        if (!profile.isVisibility()) {
            ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString(path + ".material-true")), 1, Byte.parseByte(Lotus.getPlugin().getConfig().getString(path + ".material-true-data")));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(getItemDisplayName());
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
        ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString(path + ".material-false")), 1, Byte.parseByte(Lotus.getPlugin().getConfig().getString(path + ".material-false-data")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(getItemDisplayName());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getItem() {
        return StringUtil.format(config.getString(path + ".name"));
    }

    @Override
    public String getItemDisplayName() {
        return StringUtil.format(config.getString(path + ".display-name"));
    }

    public void onClick(Player player, ItemStack itemStack) {
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        if (itemStack.getItemMeta().getDisplayName().equals(this.getItemType(player).getItemMeta().getDisplayName())) {
            if (profile.isVisibility()) {
                player.sendMessage(StringUtil.format(lang.getString("visibility-false")));
                profile.setVisibility(false);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(online);
                    player.getInventory().clear();
                    player.getInventory().setContents(Lotus.getPlugin().getItemsInventory().getItemsInventory(player).getContents());
                }
            } else {
                player.sendMessage(StringUtil.format(lang.getString("visibility-true")));
                profile.setVisibility(true);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    player.hidePlayer(online);
                    player.getInventory().clear();
                    player.getInventory().setContents(Lotus.getPlugin().getItemsInventory().getItemsInventory(player).getContents());
                }
            }
        }
    }

}
