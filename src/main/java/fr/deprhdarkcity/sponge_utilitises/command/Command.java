package fr.deprhdarkcity.sponge_utilitises.command;

import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

public interface Command extends CommandExecutor {

    String[] getNames();

    CommandSpec createCommand();
}