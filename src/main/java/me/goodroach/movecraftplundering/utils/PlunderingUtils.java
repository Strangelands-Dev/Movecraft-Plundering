package me.goodroach.movecraftplundering.utils;

import me.goodroach.movecraftplundering.config.Config;
import net.countercraft.movecraft.craft.Craft;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;

public class PlunderingUtils {
    private final Map<OfflinePlayer, Long> cooldownMap = new HashMap<>();

    public void setCooldownMap(OfflinePlayer p, long t){
        cooldownMap.put(p,t);
        return;
    }

    public boolean checkExpiry(OfflinePlayer p){
        if(!cooldownMap.containsKey(p)){
            return true;
        }
        long t = cooldownMap.get(p);
        long current = System.currentTimeMillis();
        if(current >= t + Config.SinkingCoolDown){
            cooldownMap.remove(p);
            return true;
        }
        return false;
    }

    public double sinkCalc(Craft craft) {
        double c = Config.SizeCoefficient;
        return craft.getOrigBlockCount()*c;
    }

}
