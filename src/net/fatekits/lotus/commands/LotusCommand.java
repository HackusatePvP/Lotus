package net.fatekits.lotus.commands;
import net.fatekits.lotus.Lotus;
import net.fatekits.lotus.utils.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LotusCommand implements CommandExecutor {
    FileConfiguration config = Lotus.getPlugin().getLangConfig().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("lotus.admin")) {
            player.sendMessage(StringUtil.format(config.getString("no-permissions")));
            return true;
        }
        if (args.length == 0) {

        } else {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Lotus.getPlugin().saveConfig();
                    Lotus.getPlugin().reloadConfig();
                    Lotus.getPlugin().getLangConfig().saveConfig();
                    Lotus.getPlugin().getLangConfig().reloadConfig();
                    Lotus.getPlugin().getTabConfig().saveConfig();
                    Lotus.getPlugin().getTabConfig().reloadConfig();
                    player.sendMessage(StringUtil.format(config.getString("config-reload")));
                }
            }
        }
        return false;
    }
}
