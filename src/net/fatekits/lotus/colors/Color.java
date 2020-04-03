package net.fatekits.lotus.colors;

import net.fatekits.lotus.colors.impl.*;
import net.fatekits.lotus.colors.impl.staff.DarkRedColor;
import net.fatekits.lotus.profiles.Profile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Color {

    private static Map<String, Color> byName = new HashMap<>();

    private static final Color BLUE_COLOR = new BlueColor();
    private static final Color GREEN_COLOR = new GreenColor();
    private static final Color RED_COLOR = new RedColor();
    private static final Color YELLOW_COLOR = new YellowColor();
    private static final Color PURPLE_COLOR = new PurpleColor();

    //staff colors
    private static final Color DARK_RED = new DarkRedColor();

    private String name;



    public Color(String name) {
        this.name = name;

        byName.put(name, this);
    }

    public static Color getByName(String name) {
        return byName.get(name);
    }

    public abstract String getPrefix();

    public abstract String getPermission();

    public abstract ItemStack getIcon();

    public abstract boolean staff();

    public void onClick(Player player, Profile profile, ItemStack itemStack) {

    }
}
