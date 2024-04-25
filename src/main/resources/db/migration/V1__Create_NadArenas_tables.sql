CREATE TABLE IF NOT EXISTS arenas
(
    id           INTEGER NOT NULL,
    name         TEXT    NOT NULL UNIQUE,
    world        TEXT    NOT NULL,
    display_name TEXT,
    description  TEXT,
    item         TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS spawns
(
    id       INTEGER NOT NULL,
    arena_id INTEGER NOT NULL,
    x        DOUBLE  NOT NULL,
    y        DOUBLE  NOT NULL,
    z        DOUBLE  NOT NULL,
    yaw      DOUBLE  NOT NULL,
    pitch    DOUBLE  NOT NULL,
    world    TEXT    NOT NULL,
    PRIMARY KEY (id)
);
