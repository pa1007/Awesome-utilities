package fr.depthdarkcity.sponge_utilitises.command.warp;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.Warp;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class CreateCommand extends AbstractCommand {

    public CreateCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String warpName = args.<String>getOne(Text.of("The warp's name")).orElseThrow(NullPointerException::new);

        if (!(src instanceof Player)) {
            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[warp] : ",
                    TextColors.RESET,
                    "You must be a player to perform this command"
            ));

            return CommandResult.empty();
        }
        else if (!this.pluginInstance.getWarps().containsKey(warpName)) {
            Player player = (Player) src;

            Warp warp = new Warp(
                    warpName,
                    player.getLocation().getPosition(),
                    player.getWorld().getName(),
                    player.getUniqueId()
            );

            pluginInstance.getWarps().put(warpName, warp);
            pluginInstance.saveWarps();

            src.sendMessage(Text.of(
                    TextColors.RED,
                    "[warp] : ",
                    TextColors.RESET,
                    "the warp ",
                    warpName,
                    " has been set"
            ));

            return CommandResult.success();
        }
        else {
            src.sendMessages(
                    Text.of("The warp with this name already exist, if you want to delete him ,plz do : "),
                    Text.builder("/warp d <WarpName>").color(TextColors.RED).onClick(
                            TextActions.runCommand("/warp delete " + warpName)).build(),
                    Text.of(TextColors.RESET, "or"),
                    Text.builder("/warp tp <Warpname>").color(TextColors.RED).onClick(
                            TextActions.runCommand("/warp tp " + warpName)).build()
            );

            return CommandResult.empty();
        }
    }

    @Override
    public String[] getNames() {
        return new String[]{"create", "c"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("The warp's name"))))
                .permission(Permissions.CREATE_WARP)
                .description(Text.of("Create a warp"))
                .executor(this)
                .build();
    }
}
