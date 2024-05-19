package pl.nadwey.nadarenas.conversation;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.generated.tables.records.ArenaRecord;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.Position;
import pl.nadwey.nadarenas.model.arena.ArenaRecordUtils;

import java.io.IOException;
import java.util.List;

public class CreateArenaConversation extends NadArenasConversation {
    private final static String ARENA_NAME = "arenaName";
    private final static String ARENA_RESTORER_ENABLED = "arenaRestorerEnabled";

    public CreateArenaConversation(Conversable conversable) {
        super(conversable, true, 240);
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
            if (input == null || !input.matches(ArenaRecordUtils.ARENA_NAME_REGEX)) {
                sendLangMessage("command-arena-create-invalid-name");
                return new ArenaNamePrompt();
            }

            if (NadArenas.getInstance().getStorageManager().arena().arenaExists(input)) {
                sendLangMessage("command-arena-create-arena-exists");

                return new ArenaNamePrompt();
            }

            context.setSessionData(ARENA_NAME, input);
            return new ArenaEnableRestorerPrompt();
        }
    }

    private class ArenaEnableRestorerPrompt extends BooleanPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return NadArenas.getInstance().getLangManager().getAsLegacyString("command-arena-create-enable-restorer");
        }

        @Override
        protected Prompt acceptValidatedInput(@NotNull ConversationContext context, boolean input) {
            context.setSessionData(ARENA_RESTORER_ENABLED, input);

            return new ArenaSelectAreaPrompt();
        }
    }

    private class ArenaSelectAreaPrompt extends BooleanPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return NadArenas.getInstance().getLangManager().getAsLegacyString("command-arena-create-select-area");
        }

        @Override
        protected Prompt acceptValidatedInput(@NotNull ConversationContext context, boolean input) {
            if (!input) {
                sendLangMessage("conversation-cancelled");
                return Prompt.END_OF_CONVERSATION;
            }

            Player player = (Player) context.getForWhom();
            Region weRegion = getSelectedRegion(player);

            if (weRegion == null) {
                sendLangMessage("command-arena-create-invalid-selection");
                return Prompt.END_OF_CONVERSATION;
            }

            pl.nadwey.nadarenas.model.Region region =  new pl.nadwey.nadarenas.model.Region(
                    Position.fromBlockVector3(weRegion.getMinimumPoint()),
                    Position.fromBlockVector3(weRegion.getMaximumPoint())
            );

            // get and list the overlapping arenas
            List<ArenaRecord> overlapping = NadArenas.getInstance().getStorageManager().arena().getOverlappingArenas(region);
            if (!overlapping.isEmpty()) {
                sendOverlappingArenasMessage(player, overlapping);
                return new ArenaSelectAreaPrompt();
            }
            
            createAndSaveArena(context, BukkitAdapter.adapt(weRegion.getWorld()), region);

            sendLangMessage("command-arena-create-arena-created");
            return Prompt.END_OF_CONVERSATION;
        }

        private Region getSelectedRegion(Player player) {
            BukkitPlayer wePlayer = BukkitAdapter.adapt(player);
            try {
                Region weRegion = WorldEdit.getInstance().getSessionManager().get(wePlayer).getSelection();
                if (weRegion.getWorld() == null) {
                    return null;
                }
                return weRegion;
            } catch (IncompleteRegionException e) {
                return null;
            }
        }

        private void sendOverlappingArenasMessage(Player player, List<ArenaRecord> overlapping) {
            Component textComponent = NadArenas.getInstance().getLangManager().getAsComponent("command-arena-create-overlapping").appendNewline();
            for (ArenaRecord overlappingArena : overlapping) {
                textComponent = textComponent
                        .append(Component.text("- ").color(NamedTextColor.DARK_GRAY))
                        .append(Component.text(overlappingArena.getName()).color(NamedTextColor.GOLD))
                        .appendNewline();
            }
            player.sendMessage(textComponent);
        }

        private void createAndSaveArena(ConversationContext context, World world, pl.nadwey.nadarenas.model.Region region) {
            ArenaRecord arena = new ArenaRecord();
            arena.setName((String) context.getSessionData(ARENA_NAME));
            arena.setEnableRestorer(Boolean.TRUE.equals(context.getSessionData(ARENA_RESTORER_ENABLED)));
            arena.setWorld(world.getUID().toString());
            ArenaRecordUtils.setRegion(arena, region);

            NadArenas.getInstance().getStorageManager().arena().createArena(arena);

            try {
                NadArenas.getInstance().getArenaRestorer().saveArena(arena);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
