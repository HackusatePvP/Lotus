package net.fatekits.lotus.commands;

import net.fatekits.lotus.Lotus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LotusCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (player.hasPermission("lotus.rank")) {
            player.sendMessage("it worked!");
        } else {
            Lotus.getPlugin().getRankManager().addPermissions(player, "lotus.rank");
        }
        return false;
    }
}
