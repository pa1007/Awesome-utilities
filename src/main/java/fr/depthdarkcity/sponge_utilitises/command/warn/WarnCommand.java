package fr.depthdarkcity.sponge_utilitises.command.warn;

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
import org.spongepowered.api.text.format.TextColors;

public class WarnCommand extends AbstractCommand {

    private final Command createWarn,listWarn;

    public WarnCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
        this.createWarn = new CreateWarn(spongeUtilities);
        this.listWarn= new ListWarn(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"warn"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("Warn command"))
                .child(this.createWarn.createCommand(), this.createWarn.getNames())
                .child(this.listWarn.createCommand(), this.listWarn.getNames())
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src.hasPermission(Permissions.WARN_CREATE) || src.hasPermission(Permissions.WARN_LIST)){
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[warp] : ",
                    TextColors.RESET,
                    "Usage: /warn [create,list]!"
            ));
        }else {
            throw new CommandException(Text.of("You don't have the permission to execute this command"));
        }
        return CommandResult.empty();
    }
}
