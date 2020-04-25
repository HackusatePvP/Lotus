package net.fatekits.lotus.profiles;

import net.fatekits.lotus.Lotus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.HashMap;

public class ProfileListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ProfileManager.userExists(new ProfileManager.Callback<HashMap<String, Boolean>>() {
            @Override
            public void onSuccess(HashMap<String, Boolean> done) {
                if (done.get(player.getUniqueId().toString()) != Boolean.TRUE) {
                    Lotus.getPlugin().getProfileManager().createUser(player);
                    Lotus.getPlugin().getProfileManager().addProfile(player.getUniqueId(), new Profile(player.getUniqueId(), player));
                    Lotus.getPlugin().getProfileManager().load(player);
                } else {
                    Lotus.getPlugin().getProfileManager().load(player);
                    Lotus.getPlugin().getProfileManager().addProfile(player.getUniqueId(), Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId()));
                }

            }

            @Override
            public void onFailure(Throwable cause) {
                Bukkit.getLogger().severe(cause.getMessage());
            }
        }, player.getUniqueId().toString());

    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Lotus.getPlugin().getProfileManager().unload(player);

    }
}