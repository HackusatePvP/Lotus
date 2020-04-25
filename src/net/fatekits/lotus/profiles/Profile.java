package net.fatekits.lotus.profiles;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class Profile {
    private final UUID uuid;
    private String name,IP,rank,color;
    private boolean scoreboard,tablist,chat,visibility,staff,doublejump,enderbutt;
    private Player player;

    public Profile(UUID uuid, Player player) {
        this.uuid = uuid;
        this.player = player;
    }
}
