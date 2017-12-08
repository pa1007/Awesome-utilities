package fr.depthdarkcity.sponge_utilitises.creator;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import org.spongepowered.api.text.channel.MessageChannel;

public final class ChannelRegistry {

    /**
     * Message Channel for silent Connection/Disconnection message.
     */
    public static final MessageChannel JOIN_SILENT =
            MessageChannel.combined(
                    MessageChannel.permission(Permissions.JOIN_WITH_INVISIBILITY),
                    MessageChannel.permission(Permissions.SHOW_INVISIBILITY_JOIN)
            );

    /**
     * Message Channel for sending a message only to the staff.
     */
    public static final MessageChannel STAFF = MessageChannel.combined(
            MessageChannel.permission(Permissions.STAFF_CHAT));

    /**
     * Message Channel for an Ingame Chat Between The online Staff .
     */
    public static final MessageChannel STAFF_CHAT = MessageChannel.combined(
            ChannelRegistry.STAFF, MessageChannel.TO_ALL
    );

    private ChannelRegistry() {}
}