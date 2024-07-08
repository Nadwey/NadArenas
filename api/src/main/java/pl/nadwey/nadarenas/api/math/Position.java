package pl.nadwey.nadarenas.api.math;

import com.sk89q.worldedit.math.BlockVector3;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)
public class Position {
    private Integer x;
    private Integer y;
    private Integer z;

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
        this.z = position.z;
    }

    public Position(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(BlockVector3 vector) {
        this(vector.x(), vector.y(), vector.z());
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
}
