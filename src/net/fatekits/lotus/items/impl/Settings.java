package net.fatekits.lotus.items.impl;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.items.Items;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Settings extends Items {
    private FileConfiguration config = Lotus.getPlugin().getConfig();
    private String path = "items.settings";

    public Settings() {
        super("ITEM_FRAME");
    }

    @Override
    public ItemStack getItemType(Player player) {
        ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString(path + ".material")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format(config.getString(path + ".display-name")));
        itemMeta.setLore(StringUtil.format(config.getStringList(path + ".lore")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getItem() {
        return config.getString(path + ".name");
    }

    @Override
    public String getItemDisplayName() {
        return StringUtil.format(config.getString(path + ".display-name"));
    }

    @Override
    public void onClick(Player player, ItemStack itemStack) {
        if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(getItemDisplayName())) {
            player.openInventory(Lotus.getPlugin().getSettingsManager().getSettingsInventory(player));
        }
    }
}
