package fr.depthdarkcity.sponge_utilitises.command.staffChat;

import fr.depthdarkcity.sponge_utilitises.creator.ChannelRegistry;
import fr.depthdarkcity.sponge_utilitises.creator.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class StaffChatCommand extends AbstractCommand {

    public StaffChatCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"staffchat", "staff"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
                .permission(Permissions.STAFF_CHAT)
                .description(Text.of("To chat with the staff"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(
            CommandSource src, CommandContext args
    ) throws CommandException {
        String message = args.<String>getOne(Text.of("message")).orElseThrow(NullPointerException::new);
        ChannelRegistry.STAFF.send(Text.of(
                TextColors.RED,
                "[Staff-CHAT]",
                TextColors.GOLD,
                "  <",
                src.getName(),
                "> ",
                TextColors.RESET,
                message
        ));
        return CommandResult.success();
    }
}
