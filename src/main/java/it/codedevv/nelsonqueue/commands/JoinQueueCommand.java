package it.codedevv.nelsonqueue.commands;

import it.codedevv.nelsonqueue.Queue;
import it.codedevv.nelsonqueue.utilities.Messages;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("joinqueue|joinq")
public class JoinQueueCommand extends BaseCommand {
    @Default
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(Messages.getMessageFromConfig("SERVER_NOT_ENTERED"));
            return;
        }
        if (Queue.getInstance().getQueueManager().isInQueue(player)) {
            player.sendMessage(Messages.color("&cSei gia' in un'altra coda!"));
            return;
        }
        Queue.getInstance().getQueueManager().joinQueue(player, args[0]);
    }
}