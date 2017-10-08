package fr.deprhdarkcity.sponge_utilitises.command.stop;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class StopCommand extends AbstractCommand {

    public StopCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"stop"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("message to stop")))
                .permission(Permissions.STOP_COMMAND)
                .description(Text.of("Stop the server with 15 s delay"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<String> stopMessage = args.getOne(Text.of("message to stop"));
        Timer            t           = new Timer();
        final int[]      i           = {15};
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
               Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.RED, "[ADMIN] : ", TextColors.RESET, "The server will stop in ", i[0], " secondes"));
                i[0]--;
                if (i[0] == 0) {
                    t.cancel();
                    Sponge.getServer().shutdown(Text.of(stopMessage.get()));
                }
            }
        }, 1, 1000);
        return CommandResult.success();
    }

}
