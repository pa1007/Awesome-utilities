package fr.deprhdarkcity.sponge_utilitises;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.deprhdarkcity.sponge_utilitises.command.Command;
import fr.deprhdarkcity.sponge_utilitises.command.ban.BanCommand;
import fr.deprhdarkcity.sponge_utilitises.command.ban.TempBanCommand;
import fr.deprhdarkcity.sponge_utilitises.command.broadcoast.BroadcastCommand;
import fr.deprhdarkcity.sponge_utilitises.command.hat.HatCommand;
import fr.deprhdarkcity.sponge_utilitises.command.speed.SpeedCommand;
import fr.deprhdarkcity.sponge_utilitises.command.stop.StopCommand;
import fr.deprhdarkcity.sponge_utilitises.command.teleportation.InterdimentionalTeleportationCommand;
import fr.deprhdarkcity.sponge_utilitises.command.teleportation.TeleportationToAll;
import fr.deprhdarkcity.sponge_utilitises.command.vote.VoteCommand;
import fr.deprhdarkcity.sponge_utilitises.command.warn.WarnCommand;
import fr.deprhdarkcity.sponge_utilitises.command.warp.WarpCommand;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Plugin(id = "sponge_utilities",
        name = "SpongeUtilities",
        version = "2.5",
        url = "http://depthdarkcity.fr/",
        description = "This plugin is made for Tazmarkill , created for depthdarkcity.fr",
        authors = {"pa1007"})
public class SpongeUtilities {

    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    public final  Map<UUID, List<Warn>> warns;
    @Inject
    @ConfigDir(sharedRoot = false)
    private       Path                  configPath;
    @Inject
    private       Logger                logger;
    private final Command[]             commands;
    private final Map<String, Warp>     warps;
    private final Map<String, Integer>  choices;
    private       Boolean               closed;
    private       Boolean               deletable;
    private final Set<UUID>             voter;

    public SpongeUtilities() {
        this.commands = new Command[]{
                new InterdimentionalTeleportationCommand(this),
                new BanCommand(this),
                new TempBanCommand(this),
                new WarnCommand(this),
                new BroadcastCommand(this),
                new WarpCommand(this),
                new SpeedCommand(this),
                new TeleportationToAll(this),
                new StopCommand(this),
                new HatCommand(this),
                new VoteCommand(this)
        };
        this.warps = new HashMap<>();
        this.warns = new HashMap<>();
        this.choices = new HashMap<>();
        this.deletable = Boolean.FALSE;
        this.closed = Boolean.FALSE;
        this.voter = new HashSet<>();
    }

    public Map<String, Integer> getChoices() {
        return this.choices;
    }

    public Map<String, Warp> getWarps() {
        return this.warps;
    }

    public Map<UUID, List<Warn>> getWarn() {
        return this.warns;
    }

    public Path getWarpsFile() {
        return this.configPath.toAbsolutePath().normalize().resolve("warps.json");
    }

    public Path getWarnFile() {
        return this.configPath.toAbsolutePath().normalize().resolve("warn.json");
    }

    @Listener
    public void reload(GameReloadEvent event) {
        this.saveWarps();
        this.addNewWarn();
        this.loadWarps();
        this.loadWarn();
        this.deleteVote();
    }

    @Listener
    public void preInit(GamePreInitializationEvent evt) {
        this.logger.debug("Registering commands...");

        for (Command command : this.commands) {
            this.logger.trace("Registering command {}", String.join("/", command.getNames()));

            Sponge.getCommandManager().register(this, command.createCommand(), command.getNames());
        }

        this.logger.debug("Registered {} commands.", this.commands.length);

        this.loadWarps();
        this.loadWarn();
        this.deleteVote();

    }

    @Listener
    public void serverStopping(GameStoppingServerEvent event) {
        this.saveWarps();
    }

    public void saveWarps() {
        Path warpsFile = this.getWarpsFile();

        try {
            this.logger.info("Saving warps...");

            Path parentDir = warpsFile.getParent();

            if (!Files.isDirectory(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (Writer writer = Files.newBufferedWriter(warpsFile)) {
                GSON.toJson(this.warps.values(), writer);
            }

            this.logger.info("Successfully saved {} warps!", this.warps.size());
        }
        catch (IOException e) {
            this.logger.error("Unable to save warps:", e);
        }
    }

    public void loadWarps() {
        Path warpsFile = this.getWarpsFile();

        this.logger.info("Loading warps...");

        if (Files.notExists(warpsFile)) {
            this.logger.info("No warp loaded since the file containing the warps does not exists!");
            return;
        }

        try (Reader reader = Files.newBufferedReader(warpsFile)) {
            Warp[] warps = GSON.fromJson(reader, Warp[].class);

            this.warps.putAll(Arrays.stream(warps).collect(Collectors.toMap(Warp::getName, Function.identity())));
        }
        catch (IOException e) {
            this.logger.error("Unable to load warps:", e);
        }
    }

    public void addNewWarn() {
        Path warnFile = this.getWarnFile();

        try {
            this.logger.info("Creating a new warn...");

            Path parentDir = warnFile.getParent();

            if (!Files.isDirectory(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (Writer writer = Files.newBufferedWriter(warnFile)) {
                GSON.toJson(this.warns.values(), writer);
            }

            this.logger.info("Successfully creating a new warn, there is {} warn!", this.warns.size());
        }
        catch (IOException e) {
            this.logger.error("Unable to create warn:", e);
        }
    }

    public void loadWarn() {
        Path warnFile = this.getWarnFile();

        this.logger.info("Loading warn...");

        if (Files.notExists(warnFile)) {
            this.logger.info("No warn loaded since the file containing the warns does not exists!");
            return;
        }

        try (Reader reader = Files.newBufferedReader(warnFile)) {
            Warn[] warnsList = GSON.fromJson(reader, Warn[].class);


            for (Warn warn : warnsList) {
                List<Warn> warns = this.warns.computeIfAbsent(warn.getPlayerUUID(), uuid -> new ArrayList<>());

                warns.add(warn);
            }
        }
        catch (IOException e) {
            this.logger.error("Unable to load warn:", e);
        }
    }
    public void deleteVote(){
        setDeletable(false);
        setClosed(false);
        voter.clear();
        choices.clear();
    }

    public Boolean setDeletable(Boolean deletables) {
        this.deletable = deletables;
        return deletable;
    }

    public Boolean getDeletable() {
        return deletable;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Set<UUID> getVoter() {
        return voter;
    }
}