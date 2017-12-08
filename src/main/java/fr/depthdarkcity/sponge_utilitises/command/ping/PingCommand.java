package fr.depthdarkcity.sponge_utilitises.command.ping;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilitises.creator.CommonException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PingCommand extends AbstractCommand {

    public PingCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"ping"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .permission(Permissions.PING_COMMAND)
                .description(Text.of("get your ping"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(
            CommandSource src, CommandContext args
    ) throws CommandException {

        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player       player           = (Player) src;
        int          playerConnection = player.getConnection().getLatency();
        Text.Builder textBuilder      = Text.builder();


        if (playerConnection < 40) {
            textBuilder.color(TextColors.GREEN);
        }
        else if (playerConnection > 80 && playerConnection < 100) {
            textBuilder.color(TextColors.GOLD);
        }
        else if (playerConnection > 100) {
            textBuilder.color(TextColors.DARK_RED);
        }
        player.sendMessage(textBuilder.append(Text.of("Your ping is : ", playerConnection, " !")).build());
        return CommandResult.success();
    }
}

