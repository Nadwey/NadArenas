package pl.nadwey.nadarenas.model.arena;

import org.jooq.generated.tables.records.ArenaRecord;
import pl.nadwey.nadarenas.model.Position;
import pl.nadwey.nadarenas.model.Region;

public class ArenaRecordUtils {
    public static final String ARENA_NAME_REGEX = "^[a-zA-z0-9-_]+$";

    public static void setRegion(ArenaRecord arenaRecord, Region region) {
        arenaRecord.setMinX(region.getMinPosition().x());
        arenaRecord.setMinY(region.getMinPosition().y());
        arenaRecord.setMinZ(region.getMinPosition().z());

        arenaRecord.setMaxX(region.getMaxPosition().x());
        arenaRecord.setMaxY(region.getMaxPosition().y());
        arenaRecord.setMaxZ(region.getMaxPosition().z());
    }

    public static Region getRegion(ArenaRecord arenaRecord) {
        return new Region(
                new Position(
                        arenaRecord.getMinX(),
                        arenaRecord.getMinY(),
                        arenaRecord.getMinZ()
                ),
                new Position(
                        arenaRecord.getMaxX(),
                        arenaRecord.getMaxY(),
                        arenaRecord.getMaxZ()
                )
        );
    }

    public static String getPreferredName(ArenaRecord arenaRecord) {
        return arenaRecord.getDisplayName() == null ? arenaRecord.getName() : arenaRecord.getDisplayName();
    }
}
