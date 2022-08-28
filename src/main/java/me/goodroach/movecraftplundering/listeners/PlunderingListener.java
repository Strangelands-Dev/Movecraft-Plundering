package me.goodroach.movecraftplundering.listeners;

import me.goodroach.movecraftplundering.MovecraftPlundering;
import me.goodroach.movecraftplundering.managers.CaptureManager;
import me.goodroach.movecraftplundering.utils.PlunderingUtils;
import net.countercraft.movecraft.Movecraft;
import net.countercraft.movecraft.combat.features.tracking.DamageTracking;
import net.countercraft.movecraft.combat.features.tracking.events.CraftSunkByEvent;
import net.countercraft.movecraft.craft.PlayerCraft;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PlunderingListener implements Listener {
    private final me.goodroach.movecraftplundering.utils.PlunderingUtils plunderingUtils;

    public PlunderingListener(PlunderingUtils plunderingUtils) {
        this.plunderingUtils = plunderingUtils;
    }

    @EventHandler
    public void onSunkByPlayer(@NotNull CraftSunkByEvent e){
        System.out.println("Okay someone sunk someone else.");
        //cause is the guy who sunk the ship
        OfflinePlayer cause = e.getLastRecord().getCause();
        //damaged is the guy who got sunk
        OfflinePlayer damaged = e.getLastRecord().getDamaged();

        System.out.println(cause.toString());
        System.out.println(damaged.toString());
        if (!cause.getPlayer().hasPermission("movecraftplundering.bounty")){return;}
        System.out.println("Has permission");
        if (CaptureManager.contains(cause.getPlayer(), damaged.getPlayer())){return;}
        System.out.println("Not being captured");
        if (!plunderingUtils.checkExpiry(damaged)) {return;}
        System.out.println("Has not been damaged in the last 24 hours");

        Economy econ = MovecraftPlundering.getEconomy();
        if (cause != damaged) {
            econ.depositPlayer(cause, plunderingUtils.sinkCalc(e.getCraft()));
            long currentTime = System.currentTimeMillis();
            plunderingUtils.setCooldownMap(damaged,currentTime);
            cause.getPlayer().sendMessage("You've plundered " + plunderingUtils.sinkCalc(e.getCraft()) + " pieces of gold.");

        } else {
            cause.getPlayer().sendMessage("Bruh did you kill yourself?");
        }
    }
}

