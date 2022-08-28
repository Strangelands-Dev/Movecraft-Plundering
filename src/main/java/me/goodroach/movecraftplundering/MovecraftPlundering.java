package me.goodroach.movecraftplundering;

import java.util.logging.Logger;
import me.goodroach.movecraftplundering.commands.CaptureCommand;
import me.goodroach.movecraftplundering.config.Config;
import me.goodroach.movecraftplundering.listeners.PlunderingListener;
import me.goodroach.movecraftplundering.listeners.ReleaseListener;
import me.goodroach.movecraftplundering.listeners.SinkDelay;
import me.goodroach.movecraftplundering.utils.PlunderingUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class MovecraftPlundering extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    @Override
    public void onEnable() {
        var PlunderingUtils = new PlunderingUtils();
        getServer().getPluginManager().registerEvents(new PlunderingListener(PlunderingUtils),this);

        getServer().getPluginManager().registerEvents(new SinkDelay(), this);
        getServer().getPluginManager().registerEvents(new ReleaseListener(), this);
        getCommand("capture").setExecutor(new CaptureCommand());
        System.out.println("AU'RAI PLUNDERING ON");
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Config.SinkingCoolDown = getConfig().getDouble("SinkingCoolDown",86400000);
        Config.AllowCapturing = getConfig().getBoolean("AllowCapturing",true);
        Config.AllowMoneyFromSinking = getConfig().getBoolean("AllowMoneyFromSinking",true);
        Config.SizeCoefficient = getConfig().getDouble("SizeCoefficient", 1.0);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }
    @Override
    public void onDisable() {
        System.out.println("bye bye");
    }
}
