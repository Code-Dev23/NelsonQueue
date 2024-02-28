package it.codedevv.nelsonqueue.managers;

import it.codedevv.nelsonqueue.Queue;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeManager implements PluginMessageListener {
    public void sendPlayerToServer(Player player, String server) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(out)) {

            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);

            player.sendPluginMessage(Queue.getInstance(), "BungeeCord", out.toByteArray());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        // assolutamente... nu caz
    }
}
