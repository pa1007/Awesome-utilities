package fr.depthdarkcity.sponge_utilitises;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.depthdarkcity.sponge_utilitises.command.Command;
import fr.depthdarkcity.sponge_utilitises.command.ban.BanCommand;
import fr.depthdarkcity.sponge_utilitises.command.ban.TempBanCommand;
import fr.depthdarkcity.sponge_utilitises.command.broadcoast.BroadcastCommand;
import fr.depthdarkcity.sponge_utilitises.command.fly.FlyCommand;
import fr.depthdarkcity.sponge_utilitises.command.god.GodCommand;
import fr.depthdarkcity.sponge_utilitises.command.god.UnGodEveryoneCommand;
import fr.depthdarkcity.sponge_utilitises.command.hat.HatCommand;
import fr.depthdarkcity.sponge_utilitises.command.ping.PingCommand;
import fr.depthdarkcity.sponge_utilitises.command.speed.SpeedCommand;
import fr.depthdarkcity.sponge_utilitises.command.staffChat.StaffChatCommand;
import fr.depthdarkcity.sponge_utilitises.command.stop.StopCommand;
import fr.depthdarkcity.sponge_utilitises.command.teleportation.InterdimentionalTeleportationCommand;
import fr.depthdarkcity.sponge_utilitises.command.teleportation.TeleportationToAll;
import fr.depthdarkcity.sponge_utilitises.command.vanish.VanishCommand;
import fr.depthdarkcity.sponge_utilitises.command.vote.VoteCommand;
import fr.depthdarkcity.sponge_utilitises.command.warn.WarnCommand;
import fr.depthdarkcity.sponge_utilitises.command.warp.WarpCommand;
import fr.depthdarkcity.sponge_utilitises.creator.CreativeItem;
import fr.depthdarkcity.sponge_utilitises.creator.Warn;
import fr.depthdarkcity.sponge_utilitises.creator.Warp;
import fr.depthdarkcity.sponge_utilitises.listener.Listener;
import fr.depthdarkcity.sponge_utilitises.listener.connection_listener.ConnectionListener;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
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

    private static SpongeUtilities pluginInstance;

    public final Map<UUID, List<Warn>> warns;

    private final Map<String, CreativeItem> creativeItem;

    @Inject
    @ConfigDir(sharedRoot = false)
    private       Path      configPath;
    @Inject
    private       Logger    logger;
    private final Command[] commands;

    private final Listener[] events;

    private final Map<String, Warp> warps;

    private final Map<String, Integer> choices;

    private final Set<UUID> godded;

    private final Set<UUID> debugList;

    private Boolean closed;

    private Boolean deletable;

    private final Set<UUID> voter;


    public SpongeUtilities() {
        this.events = new Listener[]{
                new ConnectionListener(this)
        };

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
                new VoteCommand(this),
                new GodCommand(this),
                new UnGodEveryoneCommand(this),
                new VanishCommand(this),
                new StaffChatCommand(this),
                new FlyCommand(this),
                new PingCommand(this)/*,
                new CreativePlusInventoryCommand(this)/*,
                new StateBlockCommand(this)*/
        };

        this.godded = new HashSet<>();
        this.warps = new HashMap<>();
        this.warns = new HashMap<>();
        this.choices = new HashMap<>();
        this.deletable = Boolean.FALSE;
        this.closed = Boolean.FALSE;
        this.voter = new HashSet<>();
        this.creativeItem = new HashMap<>();
        this.debugList = new HashSet<>();
        pluginInstance = this;
    }

    public Set<UUID> getGodded() {
        return godded;
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

    public Map<String, CreativeItem> getCreativeItem() {
        return creativeItem;
    }

    public Path getWarpsFile() {
        return this.configPath.toAbsolutePath().normalize().resolve("warps.json");
    }

    public Path getWarnsFile() {
        return this.configPath.toAbsolutePath().normalize().resolve("warn.json");
    }

    public Path getCreativeItemFile() {
        return this.configPath.toAbsolutePath().normalize().resolve("item.json");
    }


    @org.spongepowered.api.event.Listener
    public void reload(GameReloadEvent event) {
        this.saveWarps();
        this.saveWarns();
        this.loadWarps();
        this.loadWarns();
        this.deleteVote();
       // this.saveCreativeItem();
       // this.loadCreativeItem();
    }

    @org.spongepowered.api.event.Listener
    public void preInit(GamePreInitializationEvent evt) {
        this.loadWarps();
        this.loadWarns();
        this.deleteVote();
      //  this.loadCreativeItem();
        this.logger.debug("Registering commands...");

        for (Command command : this.commands) {
            this.logger.trace("Registering command {}", String.join("/", command.getNames()));

            Sponge.getCommandManager().register(this, command.createCommand(), command.getNames());
        }
        for (Listener event : this.events) {
            this.logger.trace("Registering event {}", String.join("/", event.getEventName()));

            Sponge.getEventManager().registerListeners(this, event);
        }

        this.logger.debug("Registered {} commands.", this.commands.length);
        this.logger.debug("Registered {} events.", this.events.length);


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

    public void saveWarns() {
        Path warnFile = this.getWarnsFile();

        try {
            this.logger.info("Creating a new warn...");

            Path parentDir = warnFile.getParent();

            if (!Files.isDirectory(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (Writer writer = Files.newBufferedWriter(warnFile)) {
                GSON.toJson(this.warns.values().stream().flatMap(List::stream).toArray(), writer);
            }

            this.logger.info("Successfully creating a new warn!");
        }
        catch (IOException e) {
            this.logger.error("Unable to create warn:", e);
        }
    }

    public void loadWarns() {
        Path warnsFile = this.getWarnsFile();

        this.logger.info("Loading warns...");

        if (Files.notExists(warnsFile)) {
            this.logger.info("No warn loaded since the file containing the warns does not exists!");
            return;
        }

        try (Reader reader = Files.newBufferedReader(warnsFile)) {
            Warn[] warnsList = GSON.fromJson(reader, Warn[].class);

            for (Warn warn : warnsList) {
                List<Warn> warns = this.warns.computeIfAbsent(warn.getPlayerUUID(), uuid -> new ArrayList<>());

                warns.add(warn);
            }

            this.logger.info("{} warns loaded successfully!", warnsList.length);
        }
        catch (IOException e) {
            this.logger.error("Unable to load warns:", e);
        }
    }

    /*public void saveCreativeItem() {
        Path itemFile = this.getCreativeItemFile();

        try {
            this.logger.info("Creating a new item...");

            Path parentDir = itemFile.getParent();

            if (!Files.isDirectory(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (Writer writer = Files.newBufferedWriter(itemFile)) {
                GSON.toJson(this.creativeItem.values(), writer);
            }

            this.logger.info("Successfully creating a new Item!");
        }
        catch (IOException e) {
            this.logger.error("Unable to create Item:", e);
        }
    }

    public void loadCreativeItem() {
        Path creativeItem = this.getCreativeItemFile();

        this.logger.info("Loading Items...");

        if (Files.notExists(creativeItem)) {
            this.logger.info("No items loaded since the file containing the items does not exists!");
            return;
        }

        try (Reader reader = Files.newBufferedReader(creativeItem)) {
            CreativeItem[] items = GSON.fromJson(reader, CreativeItem[].class);

            this.creativeItem.putAll(Arrays.stream(items).collect(Collectors.toMap(
                    CreativeItem::getName,
                    Function.identity()
            )));

            this.logger.info("{} items loaded successfully!", items.length);
        }
        catch (IOException e) {
            this.logger.error("Unable to load items:", e);
        }
    }*/

    @org.spongepowered.api.event.Listener
    public void onDammage(DamageEntityEvent e) {
        if (e.getTargetEntity() instanceof Player && getGodded().contains(e.getTargetEntity().getUniqueId())) {
            e.setCancelled(true);
            e.getTargetEntity().offer(Keys.HEALTH, ((Player) e.getTargetEntity()).maxHealth().getMaxValue());
        }
    }

    public void deleteVote() {
        setDeletable(false);
        setClosed(false);
        voter.clear();
        choices.clear();
    }

    public void broadcast(Text text) {
        Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(
                TextColors.RED, "[Broadcast] : ", TextColors.RESET,
                text
        ));
    }

    public boolean setDeletable(boolean deletables) {
        this.deletable = deletables;
        return deletable;
    }

    public boolean getDeletable() {
        return deletable;
    }

    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Set<UUID> getVoter() {
        return voter;
    }

    public Set<UUID> getDebugList() {
        return debugList;
    }

    public void debugInChatMessage(Player player, Text text) {
        player.sendMessage(Text.of(TextColors.RED, "[Debug]", TextColors.RESET, text));
    }

    public static SpongeUtilities getPluginInstance() {
        return pluginInstance;
    }
}