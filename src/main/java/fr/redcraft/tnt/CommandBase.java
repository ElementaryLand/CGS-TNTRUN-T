package fr.redcraft.tnt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBase implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length > 0){
                switch (args[0].toLowerCase()){
                    case "join":
                        GameManager.addPlayer(((Player) sender).getPlayer());
                        break;
                    case "start":
                        GameManager.startGame();
                        break;
                    case "leave":
                        GameManager.removePlayer(((Player) sender).getPlayer());
                        break;
                    case "regen":
                        PlayerListener.regenMap();
                        break;
                }
            }
        }else{
            Main.getInstance().getLogger().info("Commande seulment pour les joueurs");
        }
        return false;
    }
}
