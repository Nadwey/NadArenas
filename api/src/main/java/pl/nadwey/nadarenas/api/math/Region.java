package pl.nadwey.nadarenas.api.math;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Region {
    private Position minPosition;
    private Position maxPosition;

    public Region(Position minPosition, Position maxPosition) {
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    public boolean overlaps(Region region) {
        return ((getMinPosition().x() <= region.getMinPosition().x() && region.getMinPosition().x() <= getMaxPosition().x()) || (region.getMinPosition().x() <= getMinPosition().x() && getMinPosition().x() <= region.getMaxPosition().x())) &&
                ((getMinPosition().y() <= region.getMinPosition().y() && region.getMinPosition().y() <= getMaxPosition().y()) || (region.getMinPosition().y() <= getMinPosition().y() && getMinPosition().y() <= region.getMaxPosition().y())) &&
                ((getMinPosition().z() <= region.getMinPosition().z() && region.getMinPosition().z() <= getMaxPosition().z()) || (region.getMinPosition().z() <= getMinPosition().z() && getMinPosition().z() <= region.getMaxPosition().z()));

    }
}
