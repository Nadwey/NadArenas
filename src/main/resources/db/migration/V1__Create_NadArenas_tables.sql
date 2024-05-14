CREATE TABLE IF NOT EXISTS nadarenas_arenas
(
    id                     INTEGER NOT NULL,
    name                   TEXT    NOT NULL UNIQUE,
    loader_support         BOOLEAN NOT NULL,

    world                  TEXT    NOT NULL,
    min_x                  INTEGER NOT NULL,
    min_y                  INTEGER NOT NULL,
    min_z                  INTEGER NOT NULL,
    max_x                  INTEGER NOT NULL,
    max_y                  INTEGER NOT NULL,
    max_z                  INTEGER NOT NULL,

    loader_blocks_per_tick INTEGER DEFAULT 250,

    display_name           TEXT,
    description            TEXT,
    item                   TEXT,

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
