package pl.nadwey.nadarenas.conversation;

import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.NadArenas;

public abstract class NadArenasConversation implements ConversationAbandonedListener {
    private final ConversationFactory conversationFactory;
    private final Conversable conversable;
    private final boolean playerOnly;

    public abstract Prompt getEntryPrompt();

    protected NadArenasConversation(Conversable conversable, boolean playerOnly) {
        this.conversable = conversable;
        this.playerOnly = playerOnly;

        conversationFactory = new ConversationFactory(NadArenas.getInstance())
                .withEscapeSequence("cancel")
                .withTimeout(120)
                .addConversationAbandonedListener(this)
                .withFirstPrompt(getEntryPrompt());

        conversable.sendRawMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Note: Enter " + ChatColor.GRAY + "'cancel'" + ChatColor.DARK_GRAY + " to quit the conversation.");
    }

    public static String getCancelledMessage() {
        return ChatColor.GRAY + "" + ChatColor.ITALIC + "[cancelled]";
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
            conversation.getForWhom().sendRawMessage(ChatColor.RED + "Only players can start this conversation");
        }

        conversation.begin();
    }
}
