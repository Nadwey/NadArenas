package pl.nadwey.nadarenas.model;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.World;

public class Position {
    private Integer x = 0;
    private Integer y = 0;
    private Integer z = 0;

    public Position(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position() {

    }

    public Integer x() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer y() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer z() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public Position add(int x, int y, int z) {
        return new Position(this.x + x, this.y + y, this.z + z);
    }

    public Position add(Position position) {
        return add(position.x(), position.y(), position.z());
    }

    public Position subtract(int x, int y, int z) {
        return new Position(this.x - x, this.y - y, this.z - z);
    }

    public Position subtract(Position position) {
        return subtract(position.x(), position.y(), position.z());
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    public static Position fromBlockVector3(BlockVector3 blockVector3) {
        return new Position(blockVector3.x(), blockVector3.y(), blockVector3.z());
    }
}
