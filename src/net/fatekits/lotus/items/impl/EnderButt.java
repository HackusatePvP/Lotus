package net.fatekits.lotus.items.impl;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.items.Items;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnderButt extends Items {
    FileConfiguration config = Lotus.getPlugin().getConfig();

    public EnderButt() {
        super("ENDER_PEARL");
    }

    @Override
    public ItemStack getItemType(Player player) {
        ItemStack itemStack = new ItemStack(Material.ENDER_PEARL, 16);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format(config.getString("items.ender-butt.display-name")));
        itemMeta.setLore(StringUtil.format(config.getStringList("items.ender-butt.lore")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getItem() {
        return config.getString("items.ender-butt.name");
    }

    @Override
    public String getItemDisplayName() {
        return StringUtil.format(config.getString("items.ender-butt.name"));
    }

}
