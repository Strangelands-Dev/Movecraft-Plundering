package me.goodroach.movecraftplundering.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import net.countercraft.movecraft.async.AsyncManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.events.CraftSinkEvent;

public class SinkDelay implements Listener {
    Map<Craft, Long> craftSinkTime = new WeakHashMap<>();

    @EventHandler
    public void onCraftSink(CraftSinkEvent e) {
        System.out.println("Craft has been sunk.");
        if (!craftSinkTime.containsKey(e.getCraft())) {
            // First call to sink this craft, store the time
            System.out.println("Craft has been recorded.");
            craftSinkTime.put(e.getCraft(), System.currentTimeMillis());
            return;
        }

        // Craft has already been sunk, check if it has been sunk long enough
        if (System.currentTimeMillis() - craftSinkTime.get(e.getCraft()) > 60000) {
            // Craft has been sunk long enough, remove it from the map
            craftSinkTime.remove(e.getCraft());
            System.out.println("The craft has been removed from the sinking hashmap.");
            return;
        }

        // Craft has not been sunk long enough, cancel the event
        System.out.println("Craft sinking has been cancelled.");
        
    }
}