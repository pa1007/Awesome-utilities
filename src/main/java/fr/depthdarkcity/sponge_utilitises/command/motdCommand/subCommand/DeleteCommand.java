package fr.depthdarkcity.sponge_utilitises.command.motdCommand.subCommand;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class DeleteCommand extends AbstractCommand {
    
     public DeleteCommand(SpongeUtilities spongeUtilities) {
             super(spongeUtilities);
         }

         @Override
         public String[] getNames() {
             return new String[]{"Delete"};
         }

         @Override
         public CommandSpec createCommand() {
             return CommandSpec.builder()
                     .arguments()
                     .permission(Permissions.MOTD_CHANGE)
                     .description(Text.of("to delete the MOTD"))
                     .executor(this)
                     .build();
         }

         @Override
         public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
             throw new CommandException(Text.of("This is not supported in your sponge Version"));
         }
}
