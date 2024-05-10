package pl.nadwey.nadarenas.conversation;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.World;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.Position;
import pl.nadwey.nadarenas.model.arena.Arena;

import java.io.IOException;

public class CreateArenaConversation extends NadArenasConversation {
    private final static String ARENA_NAME = "arenaName";

    public CreateArenaConversation(Conversable conversable) {
        super(conversable, true);
    }

    @Override
    public Prompt getEntryPrompt() {
        return new ArenaNamePrompt();
    }

    private class ArenaNamePrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return NadArenas.getInstance().getLangManager().getAsLegacyString("command-arena-create-enter-name");
        }

        @Override
        public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            if (input == null || !input.matches(Arena.ARENA_NAME_REGEX)) {
                sendLangMessage("command-arena-create-invalid-name");
                return new ArenaNamePrompt();
            }

            if (NadArenas.getInstance().getStorageManager().arena().arenaExists(input)) {
                sendLangMessage("command-arena-create-arena-exists");

                return new ArenaNamePrompt();
            }

            context.setSessionData(ARENA_NAME, input);
            return new ArenaAreaPrompt();
        }
    }

    private class ArenaAreaPrompt extends BooleanPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return NadArenas.getInstance().getLangManager().getAsLegacyString("command-arena-create-select-area");
        }

        @Override
        protected Prompt acceptValidatedInput(@NotNull ConversationContext context, boolean input) {
            if (input) {
                BukkitPlayer bPlayer = BukkitAdapter.adapt((Player)context.getForWhom());
                Region region;

                try {
                    region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection();
                } catch (IncompleteRegionException e) {
                    sendLangMessage("command-arena-create-invalid-selection");
                    return new ArenaAreaPrompt();
                }

                if (region.getWorld() == null) {
                    sendLangMessage("command-arena-create-invalid-selection-world");
                    return new ArenaAreaPrompt();
                }

                String name = (String) context.getSessionData(ARENA_NAME);
                World world = BukkitAdapter.adapt(region.getWorld());

                Position minPosition = Position.fromBlockVector3(region.getMinimumPoint());
                Position maxPosition = Position.fromBlockVector3(region.getMaximumPoint());

                if (name == null) {
                    sendLangMessage("command-arena-create-name-null");
                    return Prompt.END_OF_CONVERSATION;
                }

                Arena arena = new Arena(name, world, minPosition, maxPosition);

                NadArenas.getInstance().getStorageManager().arena().createArena(arena);

                try {
                    NadArenas.getInstance().getArenaManager().saveArena(arena);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                sendLangMessage("command-arena-create-arena-created");
                return Prompt.END_OF_CONVERSATION;
            }

            sendLangMessage("conversation-cancelled");

            return Prompt.END_OF_CONVERSATION;
        }
    }
}
