package net.fatekits.lotus.cosmetics;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class CosmeticInventory {

    public Inventory getCosmeticInventory() {
        Inventory i = Bukkit.createInventory(null, Lotus.getPlugin().getConfig().getInt("cosmetic-inventory.size"), StringUtil.format(Lotus.getPlugin().getConfig().getString("cosmetic-inventory.name")));
        i.setItem(0, Cosmetic.byName("PAPER").getItemStack());
        return i;
    }
}
