package it.codedevv.nelsonqueue.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CorePlayer {
    private final UUID player;
    private final QueuePriority priority;
    private final String currentQueue;

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(player);
    }
}
