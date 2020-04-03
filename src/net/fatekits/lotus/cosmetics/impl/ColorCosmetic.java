package net.fatekits.lotus.cosmetics.impl;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.cosmetics.Cosmetic;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorCosmetic extends Cosmetic {

    public ColorCosmetic() {
        super("PAPER");
    }

    @Override
    public ItemStack getItemStack() {
        String path = "cosmetics.name-color";
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format(Lotus.getPlugin().getConfig().getString(path + ".display-name")));
        itemMeta.setLore(StringUtil.format(Lotus.getPlugin().getConfig().getStringList(path + ".lore")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(getItemStack().getItemMeta().getDisplayName())) {
            player.openInventory(Lotus.getPlugin().getColorGUI().getColorGI());
        }
    }
}
