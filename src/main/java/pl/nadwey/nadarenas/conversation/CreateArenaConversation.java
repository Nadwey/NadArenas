package pl.nadwey.nadarenas.conversation;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.Position;
import pl.nadwey.nadarenas.model.arena.Arena;
import pl.nadwey.nadarenas.utility.ArenaSaver;

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

    private static class ArenaNamePrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return ChatColor.GRAY + "Please enter the arena name.";
        }

        @Override
        public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            if (input == null || !input.matches(Arena.ARENA_NAME_REGEX)) {
                context.getForWhom().sendRawMessage(ChatColor.RED + "Arena name is invalid.");
                return new ArenaNamePrompt();
            }

            if (NadArenas.getInstance().getArenaManager().arenaExists(input)) {
                context.getForWhom().sendRawMessage(ChatColor.RED + "Arena already exists.");
                return new ArenaNamePrompt();
            }

            context.setSessionData(ARENA_NAME, input);
            return new ArenaAreaPrompt();
        }
    }

    private static class ArenaAreaPrompt extends BooleanPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return ChatColor.GRAY + "Please select the area for the arena with " + ChatColor.DARK_PURPLE + "//wand" + ChatColor.GRAY + " and then type 'yes' to confirm.";
        }

        @Override
        protected Prompt acceptValidatedInput(@NotNull ConversationContext context, boolean input) {
            if (input) {
                BukkitPlayer bPlayer = BukkitAdapter.adapt((Player)context.getForWhom());
                Region region;

                try {
                    region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection();
                } catch (IncompleteRegionException e) {
                    context.getForWhom().sendRawMessage(ChatColor.RED + "Invalid selection");
                    return new ArenaAreaPrompt();
                }

                if (region.getWorld() == null) {
                    context.getForWhom().sendRawMessage(ChatColor.RED + "The world of selection is invalid");
                    return new ArenaAreaPrompt();
                }

                String name = (String) context.getSessionData(ARENA_NAME);
                World world = BukkitAdapter.adapt(region.getWorld());

                Position minPosition = Position.fromBlockVector3(region.getMinimumPoint());
                Position maxPosition = Position.fromBlockVector3(region.getMaximumPoint());

                if (name == null) {
                    context.getForWhom().sendRawMessage(ChatColor.RED + "Name is null for some reason, cancelling");
                    return Prompt.END_OF_CONVERSATION;
                }

                Arena arena = new Arena(name, world, minPosition, maxPosition);

                NadArenas.getInstance().getArenaManager().createArena(arena);

                try {
                    ArenaSaver.saveArena(arena);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                context.getForWhom().sendRawMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "[Arena created]");
                return Prompt.END_OF_CONVERSATION;
            }

            context.getForWhom().sendRawMessage(NadArenasConversation.getCancelledMessage());
            return Prompt.END_OF_CONVERSATION;
        }
    }
}
