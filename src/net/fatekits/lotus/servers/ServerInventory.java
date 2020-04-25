package net.fatekits.lotus.servers;

import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.queue.QueueAPI;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ServerInventory implements Listener {

    public Inventory getServerGUI() {
        Inventory i = Bukkit.createInventory(null, Lotus.getPlugin().getConfig().getInt("server-gui.size"), StringUtil.format(Lotus.getPlugin().getConfig().getString("server-gui.name")));
        int count = 0;
        for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("servers").getKeys(false)) {
            Server server = new Server(s);
            Lotus.getPlugin().getServerManager().getServers().put(s, server);
            count++;
            String path = "servers." + server.getName();
            ItemStack itemStack = new ItemStack(Material.getMaterial(Lotus.getPlugin().getConfig().getString(path + ".material")));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(StringUtil.format(Lotus.getPlugin().getConfig().getString(path + ".name")));
            itemMeta.setLore(format(Lotus.getPlugin().getConfig().getStringList(path + ".lore"), server));
            itemStack.setItemMeta(itemMeta);
            server.setItemStack(itemStack);
            server.setItemMeta(itemMeta);
            i.setItem(Lotus.getPlugin().getConfig().getInt(path + ".slot"), itemStack);
        }
        return i;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() != null) {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory() != null) {
                if (event.getClickedInventory().getName().equals(getServerGUI().getName())) {
                    event.setCancelled(true);
                    if (event.getCurrentItem() != null) {
                        ItemStack item = event.getCurrentItem();
                        for (String s : Lotus.getPlugin().getConfig().getConfigurationSection("servers").getKeys(false)) {
                            Bukkit.getLogger().info("[s] " + s);
                            Server server = Lotus.getPlugin().getServerManager().getServer(s);
                            if (item.getItemMeta() != null) {
                                if (item.getItemMeta().getDisplayName().equals(server.getItemMeta().getDisplayName())) {
                                    player.performCommand(Lotus.getPlugin().getConfig().getString("servers." + s + ".command"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private List<String> format(List<String> strings, Server server) {
        List<String> forReturn = new ArrayList<>();
        for (String s : strings) {
            s = StringUtil.format(s);
            s = s.replace("%ONLINE%", server.getOnline() + "");
            if (Lotus.getPlugin().getQueueManager().getQueue(server.getName()) != null) {
                s = s.replace("%QUEUESIZE%", Lotus.getPlugin().getQueueManager().getQueue(server.getName()).getQueuePlayers().size() + "");
            } else {
                Lotus.getPlugin().getLogger().severe("[Queue] Queues were not loaded properly please make sure the config is correct.");
            }
            forReturn.add(s);
        }
        return forReturn;
    }
}
