package net.fatekits.lotus.servers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
@Setter
public class Server {
    private String name,ip;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private int online,port;

    public Server(String name) {
        this.name = name;
    }
}
