package net.fatekits.lotus.colors.impl;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.colors.Color;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PurpleColor extends Color {

    public PurpleColor() {
        super(StringUtil.format("&7* &dPurple"));
    }

    @Override
    public String getPrefix() {
        return "&d";
    }

    @Override
    public String getPermission() {
        return "fall.colors.purple";
    }

    @Override
    public ItemStack getIcon() {
        ItemStack itemStack = new ItemStack(Material.INK_SACK, 1, (byte) 13);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format("&7* &dPurple"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public boolean staff() {
        return false;
    }

    @Override
    public void onClick(Player player, Profile profile, ItemStack itemStack) {
        if (itemStack.getItemMeta().getDisplayName().equals(getIcon().getItemMeta().getDisplayName())) {
            if (player.hasPermission(getPermission())) {
                profile.setColor(getPrefix());
                player.sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("update-color")));
            } else {
                player.sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("color-locked")));
            }
        }
    }
}

