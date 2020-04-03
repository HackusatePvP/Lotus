package net.fatekits.lotus.colors.impl;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.colors.Color;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GreenColor extends Color {

    public GreenColor() {
        super(StringUtil.format("&7* &aGreen"));
    }

    @Override
    public String getPrefix() {
        return "&a";
    }

    @Override
    public String getPermission() {
        return "fall.colors.green";
    }

    @Override
    public ItemStack getIcon() {
        ItemStack itemStack = new ItemStack(Material.INK_SACK, 1, (byte) 10);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format("&7* &aGreen"));
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
