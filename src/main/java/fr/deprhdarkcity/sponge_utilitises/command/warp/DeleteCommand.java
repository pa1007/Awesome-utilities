package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
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
    public String[] getNames() {
        return new String[]{"delwarp"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("The warp's name"))))
                .permission(Permissions.DELETE_WARP)
                .description(Text.of("tp to a warp"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

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
}