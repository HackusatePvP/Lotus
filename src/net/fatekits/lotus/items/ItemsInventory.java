package net.fatekits.lotus.items;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ItemsInventory {
    FileConfiguration config = Lotus.getPlugin().getConfig();

    public Inventory getItemsInventory(Player player) {
        Inventory i = Bukkit.createInventory(null, Lotus.getPlugin().getConfig().getInt("items-inventory.size"), StringUtil.format(Lotus.getPlugin().getConfig().getString("items-inventory.name")));
        i.setItem(config.getInt("items.server-selector.slot"), Items.byName("COMPASS").getItemType(player));
        i.setItem(config.getInt("items.visibility.slot"), Items.byName("Visibility").getItemType(player));
        i.setItem(config.getInt("items.settings.slot"), Items.byName("ITEM_FRAME").getItemType(player));
        i.setItem(config.getInt("items.cosmetics.slot"), Items.byName("CHEST").getItemType(player));
        i.setItem(config.getInt("items.ender-butt.slot"), Items.byName("ENDER_PEARL").getItemType(player));
        return i;
    }

}
