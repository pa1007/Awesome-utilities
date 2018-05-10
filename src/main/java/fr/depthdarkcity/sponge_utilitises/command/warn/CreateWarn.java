package fr.depthdarkcity.sponge_utilitises.command.warn;

import fr.depthdarkcity.sponge_utilitises.Permissions;
import fr.depthdarkcity.sponge_utilitises.SpongeUtilities;
import fr.depthdarkcity.sponge_utilitises.command.AbstractCommand;
import fr.depthdarkcity.sponge_utilitises.command.ban.Reason;
import fr.depthdarkcity.sponge_utilitises.creator.CommonException;
import fr.depthdarkcity.sponge_utilitises.creator.Warn;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CreateWarn extends AbstractCommand {

    public CreateWarn(SpongeUtilities spongeUtilities) {
        super(spongeUtilities);

    }

    @Override
    public String[] getNames() {
        return new String[]{"create", "c"};
    }

    @Override
    public CommandSpec createCommand() {
        return CommandSpec.builder()
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.enumValue(Text.of("Reason"), Reason.class)),
                        GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("Other reason"))
                        )
                )
                .permission(Permissions.WARN_CREATE)
                .description(Text.of("Warn a player for doing something bad "))
                .executor(this)
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(CommonException.CONSOLE_SOURCE_EXCEPTION);
        }
        Player warned = args.<Player>getOne(Text.of("player")).get();

        if (Objects.equals(args.<String>getOne(Text.of("Reason")).get(), Reason.OTHER)) {
            if (!args.<String>getOne(Text.of("Other reason")).isPresent()) {
                src.sendMessage(Text.of(
                        TextColors.RED,
                        "You have chose the reason \"other\" you must leave the reason !"
                ));
                return CommandResult.empty();
            }
            else {
                String reason = args.<String>getOne(Text.of("Other reason")).get();
                warned.sendTitle(Title.of(Text.of(TextColors.RED, "You have been warned by ", src.getName())));
                warned.sendTitle(Title.update().subtitle(Text.of(
                        TextColors.GOLD,
                        "For ",
                        reason
                )).build());
                SpongeUtilities.broadcast(Text.of("The player ",
                                                  warned.getName(),
                                                  " Has been warn for ",
                                                  args.<String>getOne(Text.of("Reason")).get(), " ", reason
                ));

                Player admin = (Player) src;
                Date   now   = Date.from(Instant.now());
                Warn warn = new Warn(
                        warned.getUniqueId(),
                        admin.getUniqueId(),
                        reason,
                        now
                );

                List<Warn> warns = this.pluginInstance.warns.computeIfAbsent(
                        warn.getPlayerUUID(),
                        uuid -> new ArrayList<>()
                );

                warns.add(warn);
                this.pluginInstance.getWarn().put(warned.getUniqueId(), warns);
                this.pluginInstance.saveWarns();
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
            SpongeUtilities.broadcast(Text.of(
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


            List<Warn> warns = this.pluginInstance.warns.computeIfAbsent(
                    warn.getPlayerUUID(),
                    uuid -> new ArrayList<>()
            );

            warns.add(warn);
            this.pluginInstance.getWarn().put(warned.getUniqueId(), warns);
            this.pluginInstance.saveWarns();
            return CommandResult.success();
        }
    }


}
