package pl.nadwey.nadarenas.conversation;

import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.NadArenas;

import java.util.Map;

public abstract class NadArenasConversation implements ConversationAbandonedListener {
    private final ConversationFactory conversationFactory;
    private final Conversable conversable;
    private final boolean playerOnly;
    private static final String ESCAPE_SEQUENCE = "cancel";

    public abstract Prompt getEntryPrompt();

    protected NadArenasConversation(Conversable conversable, boolean playerOnly) {
        this.conversable = conversable;
        this.playerOnly = playerOnly;

        conversationFactory = new ConversationFactory(NadArenas.getInstance())
                .withEscapeSequence(ESCAPE_SEQUENCE)
                .withTimeout(120)
                .addConversationAbandonedListener(this)
                .withFirstPrompt(getEntryPrompt());

        conversable.sendRawMessage(NadArenas.getInstance().getLangManager().getAsLegacyString("conversation-cancel", Map.of(
                "escapeSequence", ESCAPE_SEQUENCE
        )));
    }

    public static String getCancelledMessage() {
        return NadArenas.getInstance().getLangManager().getAsLegacyString("conversation-cancelled");
    }

    @Override
    public void conversationAbandoned(@NotNull ConversationAbandonedEvent abandonedEvent) {
        if (!abandonedEvent.gracefulExit()) {
            abandonedEvent.getContext().getForWhom().sendRawMessage(getCancelledMessage());
        }
    }

    public void begin() {
        Conversation conversation = conversationFactory.buildConversation(conversable);

        if (playerOnly && !(conversable instanceof Player)) {
            conversation.getForWhom().sendRawMessage(NadArenas.getInstance().getLangManager().getAsLegacyString("conversation-player-only"));
        }

        conversation.begin();
    }
}
