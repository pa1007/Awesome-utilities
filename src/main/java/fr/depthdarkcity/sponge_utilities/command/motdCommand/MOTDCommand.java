package fr.depthdarkcity.sponge_utilities.command.motdCommand;

import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilities.command.Command;
import fr.depthdarkcity.sponge_utilities.command.motdCommand.subCommand.*;
import fr.depthdarkcity.sponge_utilities.creator.CommonException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class MOTDCommand extends AbstractCommand {

    private final Command helpCommand,changeCommand,deleteCommand,seeCommand,adminBroadcast;

    public MOTDCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
        this.helpCommand = new HelpSubCommand(spongeUtilities);
        this.changeCommand = new ChangeCommand(spongeUtilities);
        this.deleteCommand = new DeleteCommand(spongeUtilities);
        this.seeCommand = new SeeCommand(spongeUtilities);
        this.adminBroadcast = new AdminBroadcastCommand(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"motd"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .child(this.helpCommand.createCommand(),this.helpCommand.getNames())
                .child(this.changeCommand.createCommand(),this.changeCommand.getNames())
                .child(this.deleteCommand.createCommand(),this.deleteCommand.getNames())
                .child(this.seeCommand.createCommand(),this.seeCommand.getNames())
                .permission(Permissions.MOTD_CHANGE)
                .description(Text.of("to change the motd"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src.hasPermission(Permissions.MOTD_CHANGE)) {
            throw new CommandException(Text.of("Usage : "
                                               + "/motd [change|see|adminbroadcast|delete|help]"));
        }
        else {
            throw new CommandException(Text.of(CommonException.SOURCE_PERMISSION_EXCEPTION));
        }
    }
}
