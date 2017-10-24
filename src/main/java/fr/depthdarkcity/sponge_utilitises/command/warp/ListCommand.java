package fr.depthdarkcity.sponge_utilitises.command.warp;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ListCommand extends AbstractCommand {

    public ListCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (pluginInstance.getWarps().keySet().isEmpty()) {
            src.sendMessage(Text.of("There is no warp created"));
        }
        else {
            src.sendMessage(Text.of("The warps are : ", TextColors.RED, pluginInstance.getWarps().keySet().toString()));
        }

        return CommandResult.success();
    }

    @Override
    public String[] getNames() {
        return new String[]{"list", "l"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("Lists the warps"))
                .permission(Permissions.TELEPORT_WARP)
                .executor(this)
                .build();
    }
}
