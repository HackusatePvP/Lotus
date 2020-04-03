package net.fatekits.lotus.profiles;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class Profile {
    private final UUID uuid;
    private String name,IP,rank,color;
    private boolean scoreboard,tablist,chat,visibility,staff;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }
}
