package me.goodroach.movecraftplundering.commands;

import me.goodroach.movecraftplundering.config.Config;
import me.goodroach.movecraftplundering.managers.CaptureManager;
import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.CraftManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CaptureCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!Config.AllowCapturing) { return false; }

        if (!(sender instanceof Player)) { return false; }
        Player p = (Player) sender;
        if (!sender.hasPermission("movecraftplundering.capture")) {
            sender.sendMessage("You don't have the permission.");
            return false;
        }
        Craft senderCraft = CraftManager.getInstance().getCraftByPlayer((Player) sender);
        if (senderCraft == null) {
            sender.sendMessage("You must be piloting a craft capture somebody.");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage("You must specify a player.");
            return false;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null){
                        sender.sendMessage("This player doesn't exist!");
                        return false;
            }
            final Craft targetCraft = CraftManager.getInstance().getCraftByPlayer(target);
            if ((targetCraft != null)) {
                        // Executes the command successfully
                        CaptureManager.addTarCap((Player) sender, target);
                        sender.sendMessage("You have captured: " + target.getName() + ".");
                        return true;
            }
            // The player isn't piloting the craft.
            sender.sendMessage("This player isn't piloting a craft!");
        }
        return true;
    }

}
