package net.fatekits.lotus.ranks;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Rank {
    private List<String> permissions;
    private String name,prefix;
    private int ladder;

    public Rank(String name) {
        this.name = name;
    }
}
