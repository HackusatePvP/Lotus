package net.fatekits.lotus.cosmetics.impl;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.cosmetics.Cosmetic;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DoubleJump extends Cosmetic {
    FileConfiguration config = Lotus.getPlugin().getConfig();

    public DoubleJump() {
        super("FEATHER");
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.FEATHER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(StringUtil.format(config.getString("cosmetics.double-jump.display-name")));
        itemMeta.setLore(StringUtil.format(config.getStringList("cosmetics.double-jump.lore")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onClick(ItemStack itemStack, Player player) {
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(getItemStack().getItemMeta().getDisplayName())) {
            if (profile.isDoublejump()) {
                player.sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("double-jump-disable")));
                profile.setDoublejump(false);
                return;
            }
            player.sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("double-jump-enable")));
            profile.setDoublejump(true);
        }
    }
}
