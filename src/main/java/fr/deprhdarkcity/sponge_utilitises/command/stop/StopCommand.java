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
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import java.util.concurrent.TimeUnit;

public class StopCommand extends AbstractCommand {

    public static final int COUNTDOWN_DURATION = 15;

    private volatile int shutdownCountdown = -1;

    private String shutdownMessage;

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
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("reason")))
                .permission(Permissions.STOP_COMMAND)
                .description(Text.of("Stop the server with 15 s delay"))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        this.shutdownMessage = args.<String>getOne(Text.of("reason")).orElse("");
        this.shutdownCountdown = 15;

        Task.builder()
                .interval(1, TimeUnit.SECONDS)
                .execute(this::countDown)
                .name("Server shutdown")
                .submit(this.pluginInstance);

        return CommandResult.success();
    }

    private void countDown(Task task) {
        if (--this.shutdownCountdown == 0) {
            Sponge.getServer().shutdown(Text.of(this.shutdownMessage));
        }
        else {
            Sponge.getServer().getBroadcastChannel().send(Text.of(
                    TextColors.RED,
                    "[ADMIN] : ",
                    TextColors.RESET,
                    "The server will stop in ",
                    this.shutdownCountdown,
                    " seconds..."
            ));
        }
    }
}