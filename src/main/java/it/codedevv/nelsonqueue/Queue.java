package it.codedevv.nelsonqueue;

import co.aikar.commands.PaperCommandManager;
import it.codedevv.nelsonqueue.commands.JoinQueueCommand;
import it.codedevv.nelsonqueue.commands.LeaveQueueCommand;
import it.codedevv.nelsonqueue.hooks.PlaceholdersHook;
import it.codedevv.nelsonqueue.listeners.PlayerListeners;
import it.codedevv.nelsonqueue.managers.BungeeManager;
import it.codedevv.nelsonqueue.managers.QueueManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public final class Queue extends JavaPlugin {

    public static Logger LOGGER = Bukkit.getLogger();

    @Getter
    public static Queue instance;
    private QueueManager queueManager;
    private BungeeManager bungeeManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        bungeeManager = new BungeeManager();
        queueManager = new QueueManager(instance);
        queueManager.loadQueueServer();
        queueManager.startCheckerTask();
        new PlaceholdersHook().register();

        LOGGER.info("Plugin enabled!");

        loadCommands();
        loadListeners();

        this.getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(instance, "BungeeCord", bungeeManager);
    }

    @Override
    public void onDisable() {

    }

    private void loadCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(instance);
        paperCommandManager.registerCommand(new JoinQueueCommand());
        paperCommandManager.registerCommand(new LeaveQueueCommand());
    }

    private void loadListeners() {
        PluginManager eventManager = getServer().getPluginManager();
        eventManager.registerEvents(new PlayerListeners(), this);
    }
}