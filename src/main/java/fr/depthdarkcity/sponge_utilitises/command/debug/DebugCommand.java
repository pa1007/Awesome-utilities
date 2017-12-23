package fr.depthdarkcity.sponge_utilitises.command.debug;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilitises.command.Command;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class DebugCommand extends AbstractCommand {

    private final Command addCommand, leaveCommand;

    public DebugCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
        this.addCommand = new AddCommand(spongeUtilities);
        this.leaveCommand = new LeaveCommand(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"DebugChat"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .child(this.addCommand.createCommand(), this.addCommand.getNames())
                .child(this.leaveCommand.createCommand(), this.leaveCommand.getNames())
                .permission(Permissions.DEBUG)
                .description(Text.of("get the debug chat"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(
            CommandSource src, CommandContext args
    ) throws CommandException {
        if (src.hasPermission(Permissions.DEBUG)) {
            src.sendMessage(Text.of("To see the Debug Chat"));
        }
        return CommandResult.empty();
    }
}
