package net.fatekits.lotus.utils;

import net.minecraft.util.com.google.gson.stream.JsonWriter;
import org.bukkit.ChatColor;

final class MessagePart {
    private ChatColor color;
    private ChatColor[] styles;
    private String clickActionName;
    private String clickActionData;
    private String hoverActionName;
    private String hoverActionData;
    private final String text;

    MessagePart(String text) {
        this.text = text;
    }

    JsonWriter writeJson(JsonWriter json) {
        try {
            json.beginObject().name("text").value(this.text);
            if (this.color != null) {
                json.name("color").value(this.color.name().toLowerCase());
            }

            if (this.styles != null) {
                ChatColor[] arrayOfChatColor = this.styles;
                int j = this.styles.length;

                for(int i = 0; i < j; ++i) {
                    ChatColor style = arrayOfChatColor[i];
                    json.name(style.name().toLowerCase()).value(true);
                }
            }

            if (this.clickActionName != null && this.clickActionData != null) {
                json.name("clickEvent").beginObject().name("action").value(this.clickActionName).name("value").value(this.clickActionData).endObject();
            }

            if (this.hoverActionName != null && this.hoverActionData != null) {
                json.name("hoverEvent").beginObject().name("action").value(this.hoverActionName).name("value").value(this.hoverActionData).endObject();
            }

            return json.endObject();
        } catch (Exception var6) {
            var6.printStackTrace();
            return json;
        }
    }
}

