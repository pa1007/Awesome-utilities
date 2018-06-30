package fr.depthdarkcity.sponge_utilities.command.pos;

import com.flowpowered.math.vector.Vector3d;
import fr.depthdarkcity.sponge_utilities.Permissions;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import fr.depthdarkcity.sponge_utilities.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilities.creator.CommonException;
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
import java.util.Optional;

public class SecondPositionCommand extends AbstractCommand {

    public SecondPositionCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"pos2"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(GenericArguments.optional(GenericArguments.vector3d(Text.of("Pos"))))
                .permission(Permissions.POS)
                .description(Text.of("To create a position"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player             player      = (Player) src;
        Optional<Vector3d> optionalPos = args.getOne(Text.of("Pos"));
        Vector3d           pos;
        if (optionalPos.isPresent()) {
            pos = optionalPos.get();
            pluginInstance.getSecondPos().put(player.getUniqueId(), pos);
        }
        else if (pluginInstance.getSecondPos().containsKey(player.getUniqueId())) {
            Text main = Text.of(TextColors.GREEN, "You can choose between :");
            Text first = Text.builder().color(TextColors.GOLD).append(Text.of(
                    " - You want to OverWrite the first position !"))
                    .onClick(TextActions.runCommand("/pos1 "
                                                    + player.getLocation().getPosition().toString()
                                                            .replace("(", "").replace(" ", "").replace(")", "")))
                    .onHover(TextActions.showText(Text.of("OverWrite the pos1"))).build();
            Text second =
                    Text.builder().color(TextColors.BLUE).append(Text.of(
                            " - You want to OverWrite the second position !")).onClick(
                            TextActions.runCommand(
                                    "/pos2 " + player.getLocation().getPosition().toString()
                                            .replace("(", "").replace(" ", "").replace(")", "")))
                            .onHover(TextActions.showText(Text.of("OverWrite the pos2"))).build();
            player.sendMessages(main, first, second);
            return CommandResult.empty();
        }
        else {
            pos = player.getLocation().getPosition();
        }
        pluginInstance.getSecondPos().put(player.getUniqueId(), pos);
        player.sendMessage(Text.of(TextColors.GREEN,
                                   "The position has been set at the coordinate : " + pluginInstance.getSecondPos().get(
                                           player.getUniqueId()).toString()));
        return CommandResult.success();

    }
}
