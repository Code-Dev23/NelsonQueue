package it.codedevv.nelsonqueue.managers;

import it.codedevv.nelsonqueue.Queue;
import it.codedevv.nelsonqueue.queue.CorePlayer;
import it.codedevv.nelsonqueue.queue.CoreServer;
import it.codedevv.nelsonqueue.queue.QueuePriority;
import it.codedevv.nelsonqueue.utilities.Messages;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

@RequiredArgsConstructor
public class QueueManager {
    public static final Set<CoreServer> queueServers = new HashSet<>();

    private final Queue main;

    public CoreServer getQueueServerFromPlayer(Player player) {
        return queueServers.stream().filter(coreServer -> coreServer.getQueuePlayers().stream().anyMatch(corePlayer -> corePlayer.getPlayer() == player.getUniqueId())).findFirst().orElse(null);
    }

    public boolean isInQueue(Player player) {
        return queueServers.stream().anyMatch(coreServer -> coreServer.getQueuePlayers().stream().anyMatch(corePlayer -> corePlayer.getPlayer() == player.getUniqueId()));
    }

    public CoreServer getServer(String serverName) {
        return queueServers.stream().filter(coreServer -> Objects.equals(coreServer.getServerName(), serverName)).findFirst().orElse(null);
    }

    public CorePlayer getPlayer(Player player) {
        CoreServer coreServer = getQueueServerFromPlayer(player);

        if (coreServer == null) return null;

        return coreServer.getQueuePlayers().stream().filter(corePlayer -> corePlayer.getPlayer() == player.getUniqueId()).findFirst().orElse(null);
    }

    public int getQueuePosition(Player player) {
        CoreServer coreServer = getQueueServerFromPlayer(player);

        if (coreServer == null) return -1;

        CorePlayer corePlayer = getPlayer(player);

        if (corePlayer == null) return -1;

        return coreServer.getQueuePlayers().indexOf(corePlayer) + 1;
    }

    public void joinQueue(Player player, String server) {
        CoreServer coreServer = getServer(server);

        if (coreServer == null || isInQueue(player)) return;

        CorePlayer corePlayer = new CorePlayer(player.getUniqueId(), QueuePriority.NONE, server);

        coreServer.getQueuePlayers().add(corePlayer);

        player.sendMessage(Messages.getMessageFromConfig("JOIN_QUEUE").replace("%server%", server));
        player.sendMessage(Messages.getMessageFromConfig("QUEUE_POSITION").replace("%position%", String.valueOf(getQueuePosition(player))).replace("%queue_size%", String.valueOf(getQueueServerFromPlayer(player).getQueuePlayers().size())));
    }

    public void leaveQueue(Player player) {
        CoreServer coreServer = getQueueServerFromPlayer(player);

        if (coreServer == null || !isInQueue(player)) return;

        coreServer.getQueuePlayers().remove(getPlayer(player));
    }


    public void startCheckerTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Queue.getInstance(), () -> {

            for (CoreServer coreServer : queueServers) {
                List<CorePlayer> corePlayers = new ArrayList<>(coreServer.getQueuePlayers());

                if (corePlayers.isEmpty()) continue;
                CorePlayer corePlayer = corePlayers.get(0);
                Player player = corePlayer.getBukkitPlayer();

                this.leaveQueue(player);
                player.sendMessage(Messages.getMessageFromConfig("PLAYER_SENDED").replace("%server%", coreServer.getServerName()));
                Queue.getInstance().getBungeeManager().sendPlayerToServer(player, coreServer.getServerName());

                corePlayers.remove(corePlayer);
                for (CorePlayer messagers : corePlayers) {
                    int position = corePlayers.indexOf(messagers) + 1;
                    messagers.getBukkitPlayer().sendMessage(Messages.getMessageFromConfig("QUEUE_POSITION")
                            .replace("%position%", String.valueOf(position))
                            .replace("%queue_size%", String.valueOf(corePlayers.size())));
                }
            }

        }, 5, 3 * 20);
    }

    public void loadQueueServer() {
        main.getConfig().getStringList("QUEUE_SERVER").forEach(string -> queueServers.add(new CoreServer(string)));
        Queue.LOGGER.info("[QUEUE] Server loaded with successfully!");
    }
}