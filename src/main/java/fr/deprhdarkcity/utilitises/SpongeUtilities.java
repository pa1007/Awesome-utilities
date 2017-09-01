package fr.deprhdarkcity.utilitises;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.deprhdarkcity.utilitises.comand.Command;
import fr.deprhdarkcity.utilitises.comand.Warps.WarpsCreateCommand;
import fr.deprhdarkcity.utilitises.comand.Warps.WarpsDelCommand;
import fr.deprhdarkcity.utilitises.comand.Warps.WarpsTPCommand;
import fr.deprhdarkcity.utilitises.comand.teleportation.InterdimentionalTeleportationCommand;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Plugin(id = "utilises",
        name = "SpongeUtilities",
        version = "1.0",
        url = "http://depthdarkcity.fr/",
        description = "This plugin is made for Tazmarkill , created for depthdarkcity.fr",
        authors = {"pa1007"})
public class SpongeUtilities {

    static final Gson        GSON      = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    public       Set<String> WarpsName = new HashSet<String>();
    @Inject
    @ConfigDir(sharedRoot = false)
    private       Path      configPath;
    private       Game      game;
    private final Command[] commands;
    private Set<Warp> warp = new HashSet<>();
    @Inject
    private Logger logger;
    private SpongeUtilities pluginInstance = SpongeUtilities.this;

    public SpongeUtilities() {
        this.commands = new Command[]{
                new WarpsCreateCommand(this),
                new WarpsTPCommand(this),
                new WarpsDelCommand(this),
                new InterdimentionalTeleportationCommand(this)
        };
    }

    public Path getWarpsDirectory() {
        return this.configPath.toAbsolutePath().normalize().resolve("Warps");
    }

    public Set<Warp> getWarps() {
        return warp;
    }

    @Listener
    public void reload(GameReloadEvent event) {
        this.saveWarp();
        this.loadWarp();
    }


    @Listener
    public void preInit(GamePreInitializationEvent evt) {

        logger.debug("Registering commands...");

        for (Command command : this.commands) {
            logger.trace("Loading command {}", String.join("/", command.getNames()));

            Sponge.getCommandManager().register(this, command.createCommand(), command.getNames());
        }
        this.loadWarp();
        WarpsName.add(this.warp.toString());
    }

    @Listener
    public void serverStopping(GameStoppingServerEvent event) {
        this.saveWarp();
    }

    //Warp
    //WarpSet
    public Set<Warp> getWarpsSet() {
        return warp;
    }

    public void saveWarp() {
        Path directory = this.getWarpsDirectory();

        try {
            logger.info("Saving warp...");

            for (Warp warp : this.warp) {
                warp.save(directory);
            }

            logger.info("{} warp saved!", this.warp.size());
        }
        catch (IOException e) {
            logger.error("Couldn't save warp:", e);
        }
    }

    public void loadWarp() {
        Path directory = this.getWarpsDirectory();

        logger.info("Loading warp...");

        try {
            if (!Files.isDirectory(directory)) {
                logger.warn("No directory found at '{}', creating...", directory);

                Files.createDirectories(directory);
            }
            this.warp = Warp.loadAll(directory);
            logger.info("{} warp loaded!", this.warp.size());
        }
        catch (IOException e) {
            logger.error("Couldn't load files in {} directory!", directory, e);
        }
    }

    public void delWarp(String fileName) {
        Path directory = this.getWarpsDirectory();

        try {
            logger.info("deleting the warp...");

            for (Warp warp : this.warp) {
                warp.delete(fileName, directory);
            }

            logger.info("the warp " + fileName + " has been deleted");
        }
        catch (IOException e) {
            logger.error("Couldn't delete warp:", e);
        }
    }


}

