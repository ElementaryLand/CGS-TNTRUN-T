package fr.redcraft.tnt;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PlayerListener implements Listener {
    private static double PLAYER_BOUNDINGBOX_ADD = 0.3;
    private int SCAN_DEPTH = 1;
    public static LinkedList<BlockState> blocks = new LinkedList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location to = event.getTo();
        Location location = player.getLocation().clone().add(0,-1,0);
        if(Main.getGameState() == GameState.PLAYING && GameManager.players.contains(player)){
            walk(location);
        }
        Cuboid cuboid = new Cuboid(new Location(Main.getWorld(),281,18,311),new Location(Main.getWorld(),241,14,223));
        if(cuboid.contains(player.getLocation())){
            GameManager.setSepectator(player);
        }
    }

    public void walk(Location location){
        int y = location.getBlockY() + 1;
        Block block = null;

        for(int i = 0; i <= SCAN_DEPTH; i++){
            block = getBlockUnderPlayer(y,location);
            y--;
            if(block != null){
                break;
            }
        }

        if(block != null){
            Block fblock = block;
            if(!GameManager.blocksToDestroy.contains(fblock)){
                GameManager.blocksToDestroy.add(fblock);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        GameManager.blocksToDestroy.remove(fblock);
                        removeBlocks(fblock);

                    }
                },4);
            }
        }
    }

    private void removeBlocks(Block block){
        blocks.add(block.getState());
        block.setType(Material.AIR);
        block = block.getRelative(BlockFace.DOWN);
        blocks.add(block.getState());
        block.setType(Material.AIR);
    }

    private Block getBlockUnderPlayer(int y,Location location){
        Position loc = new Position(location.getX(),y,location.getZ());

        Block b0 = loc.getBlock(location.getWorld(),0,0);
        if(Main.blocksToDestroy.contains(b0.getType())){
            return b0;
        }
        Block b11 = loc.getBlock(location.getWorld(), +PLAYER_BOUNDINGBOX_ADD, -PLAYER_BOUNDINGBOX_ADD);
        if (Main.blocksToDestroy.contains(b11.getType())) {
            return b11;
        }

        Block b12 = loc.getBlock(location.getWorld(), -PLAYER_BOUNDINGBOX_ADD, +PLAYER_BOUNDINGBOX_ADD);
        if (Main.blocksToDestroy.contains(b12.getType())) {
            return b12;
        }

        Block b21 = loc.getBlock(location.getWorld(), +PLAYER_BOUNDINGBOX_ADD, +PLAYER_BOUNDINGBOX_ADD);
        if (Main.blocksToDestroy.contains(b21.getType())) {
            return b21;
        }

        Block b22 = loc.getBlock(location.getWorld(), -PLAYER_BOUNDINGBOX_ADD, -PLAYER_BOUNDINGBOX_ADD);
        if (Main.blocksToDestroy.contains(b22.getType())) {
            return b22;
        }
        return null;
    }

    public static void regenMap(){
        Iterator<BlockState> bsit = blocks.iterator();
        new BukkitRunnable() {
            @Override
            public void run() {
                for(int i = 10; i >= 0; i--){
                    if(bsit.hasNext()){
                        try {
                            BlockState bs = bsit.next();
                            bs.update(true);
                            bsit.remove();
                        } catch (ConcurrentModificationException e){
                        }
                    }else{
                        cancel();
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(),0L,1L);
    }
}
