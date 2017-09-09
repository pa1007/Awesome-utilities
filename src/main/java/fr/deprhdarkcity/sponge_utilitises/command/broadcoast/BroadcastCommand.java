package fr.deprhdarkcity.sponge_utilitises.command.broadcoast;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class BroadcastCommand extends AbstractCommand {

    public BroadcastCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"Broadcast"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("message to broadcast"))))
                .permission(Permissions.BROADCAST_COMMAND)
                .description(Text.of("To broadcast a message to the server"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String message = args.<String>getOne(Text.of("message to broadcast")).get();
        Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(TextColors.RED,"[Broadcast] : ",TextColors.RESET, message));
        return CommandResult.success();
    }
}
