package fr.depthdarkcity.sponge_utilitises.command.warp;

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

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String warpName = args.<String>getOne(Text.of("The warp's name")).orElseThrow(NullPointerException::new);

        if (this.pluginInstance.getWarps().remove(warpName) == null) {
            src.sendMessage(
                    Text.of(TextColors.RED, "[warp] : ", TextColors.RESET, "The warp ", warpName, " was not found!")
            );
        }
        else {
            src.sendMessage(
                    Text.of(TextColors.RED, "[warp] : ", TextColors.RESET, "The warp ", warpName, " has been deleted!")
            );

            this.pluginInstance.saveWarps();
        }

        return CommandResult.empty();
    }

    @Override
    public String[] getNames() {
        return new String[]{"delete", "d"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("Delete a warp"))
                .arguments(GenericArguments.string(Text.of("The warp's name")))
                .permission(Permissions.DELETE_WARP)
                .executor(this)
                .build();
    }
}
