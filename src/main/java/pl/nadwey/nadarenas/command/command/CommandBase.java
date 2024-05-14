package pl.nadwey.nadarenas.command.command;

import pl.nadwey.nadarenas.NadArenas;

public abstract class CommandBase {
    private final NadArenas plugin;

    public CommandBase(NadArenas plugin) {
        this.plugin = plugin;
    }

    protected NadArenas getPlugin() {
        return plugin;
    }
}
