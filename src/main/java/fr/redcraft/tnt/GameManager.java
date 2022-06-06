package fr.redcraft.tnt;

import fr.redcraft.tnt.task.StartTask;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {

    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Player> sepectator = new ArrayList<>();
    public static HashMap<Player,Integer> points = new HashMap<>();
    public static List<Block> blocksToDestroy = new ArrayList<>();


    public static void addPlayer(Player player){
        if(!players.contains(player)){
            players.add(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.sendTitle("§6Bienvenue sur","§eLe TNTRun",10,10,10);
            player.teleport(new Location(Main.getWorld(),295.5, 124.5, 124.5, 0, 0));
            Bukkit.getOnlinePlayers().forEach(player1 -> {
                player1.sendMessage(Main.prefix + player.getName() + " à rejoin le TNTRun (" + players.size() + " / 24}");
            });
            points.put(player,1);
        }
    }

    public static void setSepectator(Player player){
        if(!sepectator.contains(player)){
            if(players.contains(player)){
                sepectator.add(player);
                players.remove(player);
                player.sendMessage(Main.prefix + "Vous êtes mort.");
                Bukkit.broadcastMessage(Main.prefix + "§e§l" + player.getName() + "§6 est mort");
                player.setGameMode(GameMode.SPECTATOR);
                if(players.size() == 1){
                    Bukkit.broadcastMessage("§6Félicitation à §e" + players.get(0).getName() + " §6Qui à gagner la partie de TNTRun !");
                    sepectator.forEach(sepectator ->{
                        sepectator.teleport(new Location(Bukkit.getWorld("Lobby"),-82.546,4,-196.700,(float) 87.6,(float) 1));
                        sepectator.setGameMode(GameMode.ADVENTURE);
                        sepectator.getInventory().clear();
                    });
                    players.get(0).teleport(new Location(Bukkit.getWorld("Lobby"),-82.546,4,-196.700,(float) 87.6,(float) 1));
                    players.get(0).setGameMode(GameMode.ADVENTURE);
                    if(Main.getGameState() == GameState.PLAYING){
                        Main.setGameState(GameState.WAITING);
                        players.clear();
                        sepectator.clear();
                        PlayerListener.regenMap();
                    }
                }
            }
        }
    }

    public static void removePlayer(Player player){
        if(players.contains(player)){
            players.remove(player);
        }
    }

    public static void startGame(){
        if(Main.getGameState() == GameState.WAITING){
            StartTask startTask = new StartTask();
            startTask.runTaskTimer(Main.getInstance(),0L,20L);
            players.forEach(player -> {
                player.teleport(new Location(Main.getWorld(),256.5D, 36.0D, 262.5D));
            });
        }
    }
}
