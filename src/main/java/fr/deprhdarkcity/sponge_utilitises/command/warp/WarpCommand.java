package fr.deprhdarkcity.sponge_utilitises.command.warp;

import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.deprhdarkcity.sponge_utilitises.command.Command;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class WarpCommand extends AbstractCommand {

    private final Command teleportCommand, createCommand, listCommand, deleteCommand;

    public WarpCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);

        this.teleportCommand = new TeleportCommand(spongeUtilities);
        this.createCommand = new CreateCommand(spongeUtilities);
        this.listCommand = new ListCommand(spongeUtilities);
        this.deleteCommand = new DeleteCommand(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"warp"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .description(Text.of("Warps command"))
                .child(this.createCommand.createCommand(), this.createCommand.getNames())
                .child(this.teleportCommand.createCommand(), this.teleportCommand.getNames())
                .child(this.deleteCommand.createCommand(), this.deleteCommand.getNames())
                .child(this.listCommand.createCommand(), this.listCommand.getNames())
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        src.sendMessage(Text.of(
                TextColors.RED,
                "[warp] : ",
                TextColors.RESET,
                "Usage: /warp [teleport|delete|create|list]!"
        ));

        return CommandResult.empty();
    }
}
