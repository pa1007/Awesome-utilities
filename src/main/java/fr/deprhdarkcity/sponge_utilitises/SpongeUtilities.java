package fr.deprhdarkcity.sponge_utilitises;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.deprhdarkcity.sponge_utilitises.command.Command;
import fr.deprhdarkcity.sponge_utilitises.command.ban.*;
import fr.deprhdarkcity.sponge_utilitises.command.teleportation.InterdimentionalTeleportationCommand;
import fr.deprhdarkcity.sponge_utilitises.command.warp.CreateCommand;
import fr.deprhdarkcity.sponge_utilitises.command.warp.DeleteCommand;
import fr.deprhdarkcity.sponge_utilitises.command.warp.TeleportCommand;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Plugin(id = "utilises",
        name = "SpongeUtilities",
        version = "1.0",
        url = "http://depthdarkcity.fr/",
        description = "This plugin is made for Tazmarkill , created for depthdarkcity.fr",
        authors = {"pa1007"})
public class SpongeUtilities {

    static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configPath;

    @Inject
    private Logger logger;

    private final Command[] commands;

    private final Map<String, Warp> warps;


    public SpongeUtilities() {
        this.commands = new Command[]{
                new CreateCommand(this),
                new TeleportCommand(this),
                new DeleteCommand(this),
                new InterdimentionalTeleportationCommand(this),
                new BanForHacksCommand(this),
                new BanForBadComportementCommand(this),
                new BanForXRayCommand(this),
                new TempBanForHacksCommand(this),
                new TempBanForBadComportment(this),
                new TempBanForXRayCommand(this)
        };

        this.warps = new HashMap<>();
    }

    public Map<String, Warp> getWarps() {
        return this.warps;
    }

    public Path getWarpsFile() {
        return this.configPath.toAbsolutePath().normalize().resolve("warps.json");
    }

    @Listener
    public void reload(GameReloadEvent event) {
        this.saveWarps();
        this.loadWarps();
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
}