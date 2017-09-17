package fr.deprhdarkcity.sponge_utilitises.command.warn;

import fr.deprhdarkcity.sponge_utilitises.Permissions;
import fr.deprhdarkcity.sponge_utilitises.SpongeUtilities;
import fr.deprhdarkcity.sponge_utilitises.Warn;
import fr.deprhdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.deprhdarkcity.sponge_utilitises.command.ban.Reason;
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
import org.spongepowered.api.text.title.Title;
import java.time.Instant;
import java.util.*;

public class WarnCommand extends AbstractCommand {

    public WarnCommand(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);
    }

    @Override
    public String[] getNames() {
        return new String[]{"Warn"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.enumValue(
                                Text.of("Reason"), Reason.class)),
                        GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("Other reason"))
                        )
                )
                .permission(Permissions.WARN_COMMAND)
                .description(Text.of("Warn a player for doing something bad "))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player           warned = args.<Player>getOne(Text.of("player")).get();
        if (Objects.equals(args.<String>getOne(Text.of("Reason")).get(), Reason.OTHER)) {
            if (!args.<String>getOne(Text.of("Other reason")).isPresent()){
                src.sendMessage(Text.of(TextColors.RED,"You have chose the reason \"other\" you must leave the reason !"));
                return CommandResult.empty();
            }else {
                String reason = args.<String>getOne(Text.of("Optional reason")).get();
                warned.sendTitle(Title.of(Text.of(TextColors.RED, "You have been warned by ", src.getName())));
                warned.sendTitle(Title.update().subtitle(Text.of(
                        TextColors.GOLD,
                        "For ",
                        reason
                )).build());
                Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(
                        TextColors.RED,
                        "[Broadcast] : ",
                        TextColors.RESET,
                        "The player ",
                        warned.getName(),
                        " Has been warn for ",
                        args.<String>getOne(Text.of("Reason")).get()," ",reason
                ));
                Player admin = (Player) src;
                Date   now   = Date.from(Instant.now());
                Warn warn = new Warn(
                        warned.getUniqueId(),
                        admin.getUniqueId(),
                        reason ,
                        now
                );
                this.pluginInstance.getWarn().put(UUID.randomUUID().toString(), warn);
                this.pluginInstance.addNewWarn();
                return CommandResult.success();
            }
        }
        else {
            warned.sendTitle(Title.of(Text.of(TextColors.RED, "You have been warned by ", src.getName())));
            warned.sendTitle(Title.update().subtitle(Text.of(
                    TextColors.GOLD,
                    "For ",
                    args.<String>getOne(Text.of("Reason")).get()
            )).build());
            Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(
                    TextColors.RED,
                    "[Broadcast] : ",
                    TextColors.RESET,
                    "The player ",
                    warned.getName(),
                    " Has been warn for ",
                    args.<String>getOne(Text.of("Reason")).get()
            ));
            Player admin = (Player) src;
            Date   now   = Date.from(Instant.now());
            Warn warn = new Warn(
                    warned.getUniqueId(),
                    admin.getUniqueId(),
                    args.<Reason>getOne(Text.of("Reason")).get().name(),
                    now
            );

            this.pluginInstance.getWarn().put(UUID.randomUUID().toString(), warn);
            this.pluginInstance.addNewWarn();
            return CommandResult.success();
        }

    }
}
