package me.goodroach.movecraftplundering.listeners;

import me.goodroach.movecraftplundering.managers.CaptureManager;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.PlayerCraft;
import net.countercraft.movecraft.events.CraftReleaseEvent;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReleaseListener implements Listener {
    @EventHandler
    public void craftOnPilotRelease(CraftReleaseEvent e) {
        Craft c = e.getCraft();
        if (!(c instanceof PlayerCraft captured)) {
            return;
        }
        for(Player cap : CaptureManager.getTarCap().keySet()){
            if (cap == captured.getPilot()){
                CaptureManager.getTarCap().get(cap).sendMessage(cap.getName() + " has released their craft.");
                CaptureManager.removeTarCap(cap);
            }
        }
    }
}
