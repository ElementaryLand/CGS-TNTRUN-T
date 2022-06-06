package fr.redcraft.tnt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Plugin instance;
    public static GameState gameState;
    public static String prefix = "§7[§4CGS-§8TNTRun§7] §f\u00BB";
    public static World world = Bukkit.getWorld("TnTRUN");
    public static List<Material> blocksToDestroy = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        gameState = GameState.WAITING;
        getCommand("tntrun").setExecutor(new CommandBase());
        getCommand("tntrun").setTabCompleter(new CommandCompleter());
        blocksToDestroy.add(Material.RED_SAND); blocksToDestroy.add(Material.SAND); blocksToDestroy.add(Material.GRAVEL);
        blocksToDestroy.add(Material.GREEN_CONCRETE_POWDER); blocksToDestroy.add(Material.WHITE_CONCRETE_POWDER);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getInstance() {
        return instance;
    }

    public static void setGameState(GameState gameState) {
        Main.gameState = gameState;
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static World getWorld() {
        return world;
    }
}
