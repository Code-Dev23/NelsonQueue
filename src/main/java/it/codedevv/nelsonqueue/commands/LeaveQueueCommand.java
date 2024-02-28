package it.codedevv.nelsonqueue.commands;

import it.codedevv.nelsonqueue.Queue;
import it.codedevv.nelsonqueue.utilities.Messages;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("leavequeue|leaveq")
public class LeaveQueueCommand extends BaseCommand {
    @Default
    public void onCommand(Player player, String[] args) {
        if (!Queue.getInstance().getQueueManager().isInQueue(player)) {
            player.sendMessage(Messages.getMessageFromConfig("PLAYER_NOT_IN_QUEUE"));
            return;
        }
        Queue.getInstance().getQueueManager().leaveQueue(player);
        player.sendMessage(Messages.getMessageFromConfig("LEAVE_FROM_QUEUE"));
    }
}