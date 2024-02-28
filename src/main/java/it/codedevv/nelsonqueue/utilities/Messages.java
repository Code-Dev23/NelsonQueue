package it.codedevv.nelsonqueue.utilities;

import it.codedevv.nelsonqueue.Queue;
import org.bukkit.ChatColor;

public class Messages {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String getMessageFromConfig(String path) {
        return color(Queue.getInstance().getConfig().getString("MESSAGES." + path));
    }
}