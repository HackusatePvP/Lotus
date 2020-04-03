package net.fatekits.lotus.cosmetics;

import net.fatekits.lotus.cosmetics.impl.ColorCosmetic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Cosmetic {

    private static Map<String, Cosmetic> byName = new HashMap<>();

    private static final Cosmetic COLOR = new ColorCosmetic();

    private String name;

    public Cosmetic(String name) {
        this.name = name;

        byName.put(name, this);
    }

    public static Cosmetic byName(String name) {

        return byName.get(name);
    }

    public abstract ItemStack getItemStack();

    public void onClick(ItemStack itemStack, Player player) {

    }
}
