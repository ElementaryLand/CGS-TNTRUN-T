package fr.redcraft.tnt;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.NumberConversions;

public class Position {
    private double x;
    private int y;
    private double z;

    public Position(double x, int y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Block getBlock(World world, double addx, double addz) {
        return world.getBlockAt(NumberConversions.floor(x + addx), y, NumberConversions.floor(z + addz));
    }
}
