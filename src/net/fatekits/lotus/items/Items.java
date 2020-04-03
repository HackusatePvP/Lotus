package net.fatekits.lotus.items;

import net.fatekits.lotus.items.impl.Cosmetics;
import net.fatekits.lotus.items.impl.ServerSelector;
import net.fatekits.lotus.items.impl.Settings;
import net.fatekits.lotus.items.impl.Visibility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

public abstract class Items {

    private static Map<String, Items> byName = new HashMap<>();

    private static final Items SERVER_SELECTOR = new ServerSelector();
    private static final Items VISIBILITY = new Visibility();
    private static final Items SETTINGS = new Settings();
    private static final Items COSMETICS = new Cosmetics();

    private String name;

    public Items(String name) {
        this.name = name;

        byName.put(name, this);
    }

    public static Items byName(String name) {

        return byName.get(name);
    }

    public abstract ItemStack getItemType(Player player);

    public abstract String getItem();

    public abstract String getItemDisplayName();

    public abstract void onClick(Player player, ItemStack itemStack);
}
