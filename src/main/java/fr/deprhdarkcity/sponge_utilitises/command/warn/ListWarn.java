package fr.deprhdarkcity.sponge_utilitises.command.warn;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.Warn;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import java.util.Collection;
import java.util.UUID;

public class ListWarn extends AbstractCommand {

    public ListWarn(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"list", "l"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .permission(Permissions.WARN_LIST)
                .description(Text.of("Warn a player for doing something bad "))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        UUID             playerUUID = args.<Player>getOne(Text.of("player")).get().getUniqueId();
        Server           server     = Sponge.getGame().getServer();
        String           playerName = server.getPlayer(playerUUID).get().getName();
        Collection<Warn> warn       = pluginInstance.getWarn().get(playerUUID);
        if (warn == null) {
            src.sendMessage(Text.of("The player ", playerName, " has no warn yet"));
            return CommandResult.empty();
        }
        else {
            src.sendMessages(
                    Text.of("Player : ", playerName),
                    Text.of("reason of warn : ", warn),
                    Text.of("--------------"),
                    Text.of("The player ", playerName, " has : ", TextColors.RED, warn.size(), " warns ! "),
                    Text.of("--------------")
            );
            return CommandResult.success();
        }
    }
}
