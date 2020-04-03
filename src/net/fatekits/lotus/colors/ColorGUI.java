package net.fatekits.lotus.colors;

import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorGUI {

    public ItemStack remove() {
        ItemStack itemStack = new ItemStack(Material.REDSTONE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format("&7* &cRemove"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack staff() {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format("&4Staff-Colors"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Inventory getColorGI() {
        Inventory i = Bukkit.createInventory(null, 18, StringUtil.format("&9&lColor &7GUI"));
        i.setItem(0, Color.getByName(StringUtil.format("&7* &9Blue")).getIcon());
        i.setItem(1, Color.getByName(StringUtil.format("&7* &cRed")).getIcon());
        i.setItem(2, Color.getByName(StringUtil.format("&7* &aGreen")).getIcon());
        i.setItem(3, Color.getByName(StringUtil.format("&7* &eYellow")).getIcon());
        i.setItem(9, remove());
        i.setItem(13, staff());
        i.setItem(17, remove());
        return i;
    }

    public Inventory getStaffColors() {
        Inventory i = Bukkit.createInventory(null, 18, StringUtil.format("&9&lStaff &7Colors"));
        i.setItem(0, Color.getByName(StringUtil.format("&7* &4Dark-Red")).getIcon());
        return i;
    }
}
