package pl.nadwey.nadarenas.bukkit.math;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import pl.nadwey.nadarenas.api.math.Position;

@Getter
public class BukkitPosition {
    private final Position position;

    public BukkitPosition(Position position) {
        this.position = position;
    }

    public Location asLocation(World world) {
        return new Location(world, position.x(), position.y(), position.z());
    }
}
