package it.codedevv.nelsonqueue.listeners;

import it.codedevv.nelsonqueue.Queue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Queue.getInstance().getQueueManager().leaveQueue(player);
    }
}
