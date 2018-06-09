package fr.depthdarkcity.sponge_utilities.command;

import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

public interface Command extends CommandExecutor {

    /**
     * get the command name
     *
     * @return String (name of the command)
     */
    String[] getNames();

    /**
     * Get teh command for registration
     *
     * @return CommandSpec
     */
    CommandSpec createCommand();
}