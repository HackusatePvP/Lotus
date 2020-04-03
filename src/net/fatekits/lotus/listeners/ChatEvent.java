package net.fatekits.lotus.listeners;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.ranks.Rank;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        event.setCancelled(true);
        if (!profile.isChat()) {
            player.sendMessage(StringUtil.format("&cYou have chat turned off."));
            return;
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            Profile onpro = Lotus.getPlugin().getProfileManager().getProfile(online.getUniqueId());
            if (onpro.isChat()) {
                Rank rank = Lotus.getPlugin().getRankManager().getRank(profile.getRank());
                online.sendMessage(StringUtil.format(rank.getPrefix() + " " + profile.getColor() + player.getName() + "&8» &f" + event.getMessage()));
            }
        }
    }
}
