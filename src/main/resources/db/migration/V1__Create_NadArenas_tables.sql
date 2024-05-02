CREATE TABLE IF NOT EXISTS nadarenas_arenas
(
    id           INTEGER NOT NULL,
    name         TEXT    NOT NULL UNIQUE,
    arena_path   TEXT    NOT NULL,

    world        TEXT    NOT NULL,
    x1           DOUBLE  NOT NULL,
    y1           DOUBLE  NOT NULL,
    z1           DOUBLE  NOT NULL,
    x2           DOUBLE  NOT NULL,
    y2           DOUBLE  NOT NULL,
    z2           DOUBLE  NOT NULL,

    display_name TEXT,
    description  TEXT,
    item         TEXT,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS nadarenas_spawns
(
    id         INTEGER NOT NULL,
    arena_id   INTEGER NOT NULL,
    
    relative_x DOUBLE  NOT NULL,
    relative_y DOUBLE  NOT NULL,
    relative_z DOUBLE  NOT NULL,
    yaw        DOUBLE  NOT NULL,
    pitch      DOUBLE  NOT NULL,

    PRIMARY KEY (id)
);
