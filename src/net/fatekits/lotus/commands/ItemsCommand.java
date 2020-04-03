package net.fatekits.lotus.commands;

import net.fatekits.lotus.Lotus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        player.getInventory().setContents(Lotus.getPlugin().getItemsInventory().getItemsInventory(player).getContents());
        return false;
    }
}
