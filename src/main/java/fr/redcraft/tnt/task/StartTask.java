package fr.redcraft.tnt.task;


import fr.redcraft.tnt.GameState;
import fr.redcraft.tnt.Main;
import org.bukkit.Bukkit;

import org.bukkit.scheduler.BukkitRunnable;

public class StartTask extends BukkitRunnable {

    public static int timer = 11;

    @Override
    public void run() {
        switch (timer){

            case 10:
                sendTitle("§e10");
                Bukkit.broadcastMessage("§6La partie commence dans §e" +timer + " §6secondes");
                break;
            case 5:
                sendTitle("§c5");
                Bukkit.broadcastMessage("§6La partie commence dans §e" +timer + " §6secondes");
                break;
            case 4:
                sendTitle("§c4");
                Bukkit.broadcastMessage("§6La partie commence dans §e" +timer + " §6secondes");
                break;
            case 3:
                sendTitle("§43");
                Bukkit.broadcastMessage("§6La partie commence dans §e" +timer + " §6secondes");
                break;
            case 2:
                sendTitle("§42");
                Bukkit.broadcastMessage("§6La partie commence dans §e" +timer + " §6secondes");
                break;
            case 1:
                sendTitle("§41");
                Bukkit.broadcastMessage("§6La partie commence dans §e" +timer + " §6secondes");
                break;
            case 0:
                sendTitle("§bBonne chance !");
                Bukkit.broadcastMessage("§6Début de la partie bonne chance !");
                Main.gameState = GameState.PLAYING;
                cancel();
                timer = 11;
                break;
        }
        timer--;
    }

    public static void sendTitle(String title){
        Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(title,"",10,10,10));
    }
}
