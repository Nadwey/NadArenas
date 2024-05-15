package pl.nadwey.nadarenas.conversation;

import org.bukkit.command.CommandSender;
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

        sendLangMessage("conversation-cancel", Map.of(
                "escapeSequence", ESCAPE_SEQUENCE
        ));
    }

    @Override
    public void conversationAbandoned(@NotNull ConversationAbandonedEvent abandonedEvent) {
        if (!abandonedEvent.gracefulExit()) {
            sendLangMessage("conversation-cancelled");
        }
    }

    public void begin() {
        Conversation conversation = conversationFactory.buildConversation(conversable);

        if (playerOnly && !(conversable instanceof Player)) {
            sendLangMessage("conversation-player-only");
        }

        conversation.begin();
    }

    protected void sendLangMessage(String key) {
        if (!(conversable instanceof CommandSender commandSender))
            return;

        commandSender.sendMessage(NadArenas.getInstance().getLangManager().getAsComponent(key));
    }

    protected void sendLangMessage(String key, Map<String, String> args) {
        if (!(conversable instanceof CommandSender commandSender))
            return;

        commandSender.sendMessage(NadArenas.getInstance().getLangManager().getAsComponent(key, args));
    }
}
