package me.goodroach.movecraftplundering.managers;

import net.countercraft.movecraft.craft.Craft;
import org.bukkit.entity.Player;

import java.util.WeakHashMap;

public class CaptureManager {

    private static WeakHashMap<Player, Player> tarCap = new WeakHashMap<>();

    public static WeakHashMap<Player, Player> getTarCap(){
        return tarCap;
    }
    public static void addTarCap(Player t, Player c){
        System.out.println("You got a player in here.");
        tarCap.put(t, c);
    }
    public static void removeTarCap(Player t){
        System.out.println("You got a player out of here.");
        tarCap.remove(t);
    }
    public static boolean contains(Player t, Player c){
        for (Player tar : tarCap.keySet()) {
            if (tar == c && t == tarCap.get(c)) {
                return true;
            }
        }
        return false;
    }
}
