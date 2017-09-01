package fr.deprhdarkcity.utilitises.comand.Warps;

import fr.deprhdarkcity.utilitises.Permissions;
import fr.deprhdarkcity.utilitises.SpongeUtilities;
import fr.deprhdarkcity.utilitises.comand.AbstractCommand;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class WarpsDelCommand extends AbstractCommand {

    public WarpsDelCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"delwarp"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("Name of the warp"))))
                .permission(Permissions.DELETE_WARP)
                .description(Text.of("tp to a warp"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        String fileName = args.<String>getOne(Text.of("Name of the warp")).get();

        pluginInstance.delWarp(fileName);
        pluginInstance.loadWarp();
        src.sendMessage(Text.of(
                TextColors.RED,
                "[Warps] : ",
                TextColors.RESET,
                "The warp ",
                fileName,
                " has been deleted"
        ));
        return CommandResult.empty();
    }
}
