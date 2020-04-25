package net.fatekits.lotus.listeners;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.items.Items;
import net.fatekits.lotus.profiles.Profile;
import net.fatekits.lotus.servers.ServerAPI;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.spigotmc.event.entity.EntityDismountEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PlayerListener implements Listener {
    private HashSet<Player> riding = new HashSet<>();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!(player.getGameMode() == GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!(player.getGameMode() == GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getFoodLevel() > 15) {
                player.setFoodLevel(15);
            } else {
                player.setFoodLevel(15);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAchive(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            if (!(event.getEntity() instanceof Player)) {
                Entity entity = event.getEntity();
                entity.remove();
            }
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (!(player.getGameMode() == GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!(player.getGameMode() == GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId());
        if (!profile.isStaff()) {
            String message = event.getMessage();
            if (message.toLowerCase().contains("/server")) {
                player.sendMessage(StringUtil.format(Lotus.getPlugin().getLangConfig().getConfig().getString("queue-message")));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        player.getInventory().clear();
        List<String> joinMessage = format(Lotus.getPlugin().getLangConfig().getConfig().getStringList("join-message"), player);
        joinMessage.forEach(msg -> player.sendMessage(StringUtil.format(msg)));
        for (Player online : Bukkit.getOnlinePlayers()) {
            Profile profile = Lotus.getPlugin().getProfileManager().getProfile(online.getUniqueId());
            if (profile != null) {
                if (!profile.isVisibility()) {
                    online.hidePlayer(player);
                }
                if (!Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId()).isVisibility()) {
                    player.hidePlayer(online);
                }
            } else {
                Lotus.getPlugin().getProfileManager().load(player);
            }
        }
    }

    @EventHandler
    public void onProjectileLaunch(final ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            final Player p = (Player)e.getEntity().getShooter();
            if (e.getEntity() instanceof EnderPearl) {
                if (!riding.contains(p)) {
                    final Projectile proj = e.getEntity();
                    if (proj.getType() == EntityType.ENDER_PEARL) {
                        p.spigot().setCollidesWithEntities(false);
                        proj.setPassenger(p);
                        riding.add(p);
                    }
                }
            } else {
                e.getEntity().remove();
            }
        }
    }
    @EventHandler
    public void onEntityDismound(final EntityDismountEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player)e.getEntity();
            if (p != null && p.getVehicle() instanceof EnderPearl) {
                Entity pearl = p.getVehicle();
                if (pearl != null) {
                    p.spigot().setCollidesWithEntities(true);
                    p.eject();
                    pearl.remove();
                    riding.remove(p);
                    if (Items.byName("ENDER_PEARL") != null) {
                        p.getInventory().setItem(Lotus.getPlugin().getConfig().getInt("items.ender-butt.slot"), Items.byName("ENDER_PEARL").getItemType(p));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            if (Lotus.getPlugin().getProfileManager().getProfile(player.getUniqueId()).isDoublejump()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setVelocity(player.getLocation().getDirection().multiply(3.2692D));
                player.playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 5);
            }
        }
    }

    private List<String> format(List<String> strings, Player player) {
        List<String> returnTo = new ArrayList<>();
        for (String s : strings) {
            s = StringUtil.format(s);
            s = s.replace("%PLAYER%", player.getName());
            s = s.replace("%ONLINE%", ServerAPI.getTotalCount() + "");
            returnTo.add(s);
        }
        return returnTo;
    }

}
