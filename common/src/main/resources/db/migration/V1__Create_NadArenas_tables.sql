CREATE TABLE IF NOT EXISTS "${table_prefix}arenas"
(
    id                       INTEGER      NOT NULL,
    name                     VARCHAR(127) NOT NULL UNIQUE,
    namespace_id             INTEGER      NOT NULL,
    enable_restorer          BOOLEAN      NOT NULL,

    world                    TEXT         NOT NULL,
    min_x                    INTEGER      NOT NULL,
    min_y                    INTEGER      NOT NULL,
    min_z                    INTEGER      NOT NULL,
    max_x                    INTEGER      NOT NULL,
    max_y                    INTEGER      NOT NULL,
    max_z                    INTEGER      NOT NULL,

    restorer_blocks_per_tick INTEGER      NOT NULL,

    display_name             TEXT,
    description              TEXT,
    item                     TEXT,

    PRIMARY KEY (id),
    FOREIGN KEY (namespace_id) REFERENCES "${table_prefix}namespaces"(id)
);

CREATE TABLE IF NOT EXISTS "${table_prefix}namespaces"
(
    id   INTEGER NOT NULL,
    name TEXT    NOT NULL UNIQUE,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "${table_prefix}spawns"
(
    id         INTEGER NOT NULL,
    arena_id   INTEGER NOT NULL,

    relative_x DOUBLE  NOT NULL,
    relative_y DOUBLE  NOT NULL,
    relative_z DOUBLE  NOT NULL,
    yaw        DOUBLE  NOT NULL,
    pitch      DOUBLE  NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (arena_id) REFERENCES "${table_prefix}arenas"(id)
);
