package pl.nadwey.nadarenas.model;

import com.sk89q.worldedit.math.BlockVector3;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nonnull;

@Setter
@Getter
@Accessors(fluent = true)
public class Position {
    private Integer x = 0;
    private Integer y = 0;
    private Integer z = 0;

    public Position(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(BlockVector3 vector) {
        this(vector.x(), vector.y(), vector.z());
    }

    public Position() {

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

    public Location asLocation(World world) {
        return new Location(world, x, y, z);
    }
}
