package it.codedevv.nelsonqueue.hooks;

import it.codedevv.nelsonqueue.Queue;
import it.codedevv.nelsonqueue.managers.QueueManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholdersHook extends PlaceholderExpansion {

    private final QueueManager queueManager = Queue.getInstance().getQueueManager();

    @Override
    public @NotNull String getIdentifier() {
        return "nelsonqueue";
    }

    @Override
    public @NotNull String getAuthor() {
        return "CodeDevv";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.3";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        switch (params) {
            case "in_queue":
                return String.valueOf(queueManager.isInQueue(player));
            case "position_queue":
                return String.valueOf(queueManager.getQueuePosition(player));
            case "server_queue":
                return String.valueOf(queueManager.getQueueServerFromPlayer(player).getServerName());
            case "queue_size":
                return String.valueOf(queueManager.getQueueServerFromPlayer(player).getQueuePlayers().size());
            default:
                return "N/A";
        }
    }
}